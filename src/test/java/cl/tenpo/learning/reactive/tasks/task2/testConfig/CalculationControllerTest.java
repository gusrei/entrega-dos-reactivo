package cl.tenpo.learning.reactive.tasks.task2.testConfig;

import cl.tenpo.learning.reactive.tasks.task2.controllers.dtos.CalculationRequestDto;
import cl.tenpo.learning.reactive.tasks.task2.model.User;
import cl.tenpo.learning.reactive.tasks.task2.repository.UserRepository;
import cl.tenpo.learning.reactive.tasks.task2.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public final class CalculationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CalculationService calculationService;


    @Test
    public void testGetEvaluation_HappyPath() {
        String email = "test@example.com";
        CalculationRequestDto requestDto = new CalculationRequestDto(10.0, 20.0);
        User mockUser = new User();
        mockUser.setId("123");
        mockUser.setName("Test User");
        mockUser.setEmail(email);
        double expectedResult = 33.0;

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Mono.just(mockUser));
        Mockito.when(calculationService.sumWithPercentage(email, 10, 20)).thenReturn(Mono.just(expectedResult));

        webTestClient.post()
                .uri("/learning/reactive/get-evaluation")
                .header("email", email)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class)
                .isEqualTo(expectedResult);
    }

    @Test
    public void testGetEvaluation_UserNotFound() {
        String email = "nonexistent@example.com";
        CalculationRequestDto requestDto = new CalculationRequestDto(10.0, 20.0);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/learning/reactive/get-evaluation")
                .header("email", email)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .value(errorMessage -> errorMessage.contains("[UNAUTHORIZED-ERROR] User not authorized"));
    }

    @Test
    public void testGetEvaluation_RedisError() {
        String email = "test@example.com";
        CalculationRequestDto requestDto = new CalculationRequestDto(10.0, 20.0);

        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Mono.error(new RuntimeException("Redis connection error")));

        webTestClient.post()
                .uri("/learning/reactive/get-evaluation")
                .header("email", email)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class)
                .value(errorMessage -> errorMessage.contains("Redis connection error"));
    }

}
