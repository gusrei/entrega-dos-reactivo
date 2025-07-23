package cl.tenpo.learning.reactive.tasks.task2.mongo.repository;

import cl.tenpo.learning.reactive.tasks.task2.model.ApiCallEntry;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ApiCallRepository extends ReactiveMongoRepository<ApiCallEntry, String> {
}
