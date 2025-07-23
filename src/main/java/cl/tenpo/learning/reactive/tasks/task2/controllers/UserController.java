package cl.tenpo.learning.reactive.tasks.task2.controllers;

import cl.tenpo.learning.reactive.tasks.task2.controllers.dtos.UserDto;
import cl.tenpo.learning.reactive.tasks.task2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;

    @PostMapping("/create")
        public Mono<ResponseEntity<String>> createUser(@RequestBody UserDto userDto) {
            return userService.createUser(userDto)
                    .map(userId -> ResponseEntity.ok("User created with ID: " + userId))
                    .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body("Error creating user: " + e.getMessage())));
        }

        @DeleteMapping("/{id}")
        public Mono<ResponseEntity<String>> deleteUser(@PathVariable String id) {
            return userService.deleteUser(id)
                    .map(deleted -> ResponseEntity.ok("User deleted with ID: " + id))
                    .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage())));
        }
}

