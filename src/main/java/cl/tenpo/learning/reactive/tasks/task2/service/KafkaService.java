package cl.tenpo.learning.reactive.tasks.task2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaSender<String, String> kafkaSender;

    public void sendMessage(String topic, String event) {
        SenderRecord<String, String, String> message = SenderRecord.create(
                new ProducerRecord<>(topic, event), event);

        kafkaSender.send(Mono.just(message))
                .doOnNext(result -> log.info("Event sent to topic {}: {}", topic, result.correlationMetadata()))
                .doOnError(ex -> log.error("Failed to send event to topic {}", topic, ex))
                .subscribe();
    }
}