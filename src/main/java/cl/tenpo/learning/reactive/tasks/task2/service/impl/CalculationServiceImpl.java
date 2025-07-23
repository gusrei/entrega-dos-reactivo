package cl.tenpo.learning.reactive.tasks.task2.service.impl;

import cl.tenpo.learning.reactive.tasks.task2.exceptions.CachedValueUnavailableException;
import cl.tenpo.learning.reactive.tasks.task2.external.ExternalApiService;
import cl.tenpo.learning.reactive.tasks.task2.service.CalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    private final ExternalApiService externalApiService;
    private final ReactiveRedisTemplate<String, Double> redisTemplate;

    @Override
    public Mono<Double> sumWithPercentage(String cacheKey, double num1, double num2) {
        return redisTemplate.opsForValue().get(cacheKey)
                .doOnNext(value -> log.info("[CACHE-HIT] for key {}: value: {}", cacheKey, value))
                .switchIfEmpty(
                        externalApiService.getPercentage()
                                .flatMap(percentage -> redisTemplate.opsForValue()
                                        .set(cacheKey, percentage, Duration.ofMinutes(30))
                                        .thenReturn(percentage)
                                )
                                .onErrorResume(error -> redisTemplate.opsForValue().get(cacheKey)
                                        .switchIfEmpty(Mono.error(new CachedValueUnavailableException(
                                                "No cached value available and external service failed: " + error.getMessage())))
                                )
                )
                .map(percentage -> {
                    double sum = num1 + num2;
                    return sum + (sum * (percentage));
                });
    }


}
