package cl.tenpo.learning.reactive.tasks.task2.service.impl;

import cl.tenpo.learning.reactive.tasks.task2.controllers.dtos.UserDto;
import cl.tenpo.learning.reactive.tasks.task2.model.User;
import cl.tenpo.learning.reactive.tasks.task2.repository.UserRepository;
import cl.tenpo.learning.reactive.tasks.task2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public Mono<String> createUser(UserDto userDto) {
        User userEntity = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail()).build();
        return userRepository.save(userEntity)
                .map(User::getId)
                .onErrorResume(e -> {
                    log.error("Error saving user: {}", e.getMessage());
                    return Mono.error(new IllegalArgumentException("Email already exists"));
                });


    }

    @Override
    public Mono<Boolean> deleteUser(String id) {
        return userRepository.deleteById(id).thenReturn(true);
    }
}
