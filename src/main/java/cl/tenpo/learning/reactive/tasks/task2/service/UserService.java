package cl.tenpo.learning.reactive.tasks.task2.service;

import cl.tenpo.learning.reactive.tasks.task2.controllers.dtos.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<String> createUser(UserDto userDto);

    Mono<Boolean> deleteUser(String id);
}
