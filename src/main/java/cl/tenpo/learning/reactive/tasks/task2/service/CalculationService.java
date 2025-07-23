package cl.tenpo.learning.reactive.tasks.task2.service;

import reactor.core.publisher.Mono;

public interface CalculationService {

    Mono<Double> sumWithPercentage(String email, double number1, double number2);

}
