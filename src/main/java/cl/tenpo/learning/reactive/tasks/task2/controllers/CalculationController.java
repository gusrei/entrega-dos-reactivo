package cl.tenpo.learning.reactive.tasks.task2.controllers;

import cl.tenpo.learning.reactive.tasks.task2.controllers.dtos.CalculationRequestDto;
import cl.tenpo.learning.reactive.tasks.task2.exceptions.UnauthorizedException;
import cl.tenpo.learning.reactive.tasks.task2.repository.UserRepository;
import cl.tenpo.learning.reactive.tasks.task2.service.CalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/learning/reactive")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    private final UserRepository userRepository;


    @PostMapping("/get-evaluation")
    public Mono<ResponseEntity<Double>> getEvaluation(@RequestBody CalculationRequestDto request,
                                                      @RequestHeader("email") String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UnauthorizedException("[UNAUTHORIZED-ERROR] User not authorized")))
                .flatMap(user -> calculationService.sumWithPercentage(email, request.getNumber1(), request.getNumber2()))
                .map(ResponseEntity::ok);
    }

}
