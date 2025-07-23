package cl.tenpo.learning.reactive.tasks.task2.advice;

import cl.tenpo.learning.reactive.tasks.task2.exceptions.CachedValueUnavailableException;
import cl.tenpo.learning.reactive.tasks.task2.exceptions.ExternalApiUnavailableException;
import cl.tenpo.learning.reactive.tasks.task2.exceptions.InvalidPercentageFormatException;
import cl.tenpo.learning.reactive.tasks.task2.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPercentageFormatException.class)
    public ResponseEntity<String> handleInvalidFormat(InvalidPercentageFormatException e) {
        return ResponseEntity.badRequest().body("[INVALID_FORMAT] " + e.getMessage());
    }

    @ExceptionHandler(ExternalApiUnavailableException.class)
    public ResponseEntity<String> handleExternalDown(ExternalApiUnavailableException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("[EXTERNAL_API_DOWN] " + e.getMessage());
    }

    @ExceptionHandler(CachedValueUnavailableException.class)
    public ResponseEntity<String> handleCacheDown(CachedValueUnavailableException e) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("[CACHE_AND_API_UNAVAILABLE] " + e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception e) {
        log.error("[GENERIC_ERROR]", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }
}
