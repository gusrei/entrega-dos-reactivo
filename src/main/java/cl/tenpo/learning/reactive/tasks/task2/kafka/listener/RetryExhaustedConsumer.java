package cl.tenpo.learning.reactive.tasks.task2.kafka.listener;

import cl.tenpo.learning.reactive.tasks.task2.kafka.Dto.RetryExhaustedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;


@Component
@Slf4j
@RequiredArgsConstructor
public class RetryExhaustedConsumer {

    private final ReceiverOptions<String, String> retryExhaustedReceiverOptions;
    private final ObjectMapper mapper;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        KafkaReceiver.create(retryExhaustedReceiverOptions)
                .receive()
                .flatMap(record -> Mono.fromRunnable(() -> {
                    try {
                        RetryExhaustedEvent event = mapper.readValue(record.value(), RetryExhaustedEvent.class);
                        log.warn("[CR_RETRY_EXHAUSTED] Received error event from topic. Error:  {}", event.getError());
                    } catch (Exception e) {
                        log.error("[CR_RETRY_EXHAUSTED] Failed to parse retry exhausted event", e);
                    }
                }).then(Mono.fromRunnable(record.receiverOffset()::acknowledge)))
                .doOnError(e -> log.error("Error in Kafka reactive listener", e))
                .subscribe();
    }
}
