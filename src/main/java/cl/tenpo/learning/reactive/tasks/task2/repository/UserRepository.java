package cl.tenpo.learning.reactive.tasks.task2.repository;

import cl.tenpo.learning.reactive.tasks.task2.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;



public interface UserRepository extends R2dbcRepository<User, String> {
    Mono<User> findByEmail(String email);
}
