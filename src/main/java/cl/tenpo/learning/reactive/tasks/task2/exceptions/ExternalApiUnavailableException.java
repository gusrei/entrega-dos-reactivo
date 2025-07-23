package cl.tenpo.learning.reactive.tasks.task2.exceptions;

public class ExternalApiUnavailableException extends RuntimeException {
    public ExternalApiUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
