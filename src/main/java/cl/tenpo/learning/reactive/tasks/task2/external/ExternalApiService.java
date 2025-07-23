package cl.tenpo.learning.reactive.tasks.task2.external;

import cl.tenpo.learning.reactive.tasks.task2.exceptions.ExternalApiUnavailableException;
import cl.tenpo.learning.reactive.tasks.task2.exceptions.InvalidPercentageFormatException;
import cl.tenpo.learning.reactive.tasks.task2.service.KafkaService;
import cl.tenpo.learning.reactive.tasks.task2.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalApiService {

    private final WebClient webClient;

    private final KafkaService kafkaService;

    @Value("${external.api.base-url}")
    private String urlBasePath;

    @Value("${external.api.percentage}")
    private String percentagePath;

    private static final String  RETRY_EXHAUSTED_TOPIC = "CR_RETRY_EXHAUSTED";

    public Mono<Double> getPercentage() {
        return webClient.get()
                .uri(urlBasePath.concat(percentagePath))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Received raw percentage string: {}", response))
                .map(JsonUtils::parsePercentage)
                .map(Double::parseDouble)
                .onErrorMap(NumberFormatException.class, ex ->
                        new InvalidPercentageFormatException("Invalid percentage format", ex)
                )
                .onErrorMap(JsonParseException.class, ex ->
                        new InvalidPercentageFormatException("Invalid JSON format", ex)
                )
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .doBeforeRetry(retrySignal ->
                                log.warn("Retrying external API call, attempt: {}", retrySignal.totalRetries() + 1))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            String errorMessage = "External API failed after 3 retries: " + retrySignal.failure().getMessage();
                            sendRetryExhaustedEvent(errorMessage);
                            log.error("[EXTERNAL_API] Retry exhausted", retrySignal.failure());
                            return new ExternalApiUnavailableException(errorMessage, retrySignal.failure());
                        })
                );
    }



    private void sendRetryExhaustedEvent(String errorMessage) {
        String event = String.format("{\"error\": \"%s\"}", errorMessage);

        kafkaService.sendMessage(RETRY_EXHAUSTED_TOPIC, event);

    }
}