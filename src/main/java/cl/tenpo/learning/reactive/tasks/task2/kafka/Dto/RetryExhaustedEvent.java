package cl.tenpo.learning.reactive.tasks.task2.kafka.Dto;

import lombok.Data;

@Data
public class RetryExhaustedEvent {
    private String error;
}