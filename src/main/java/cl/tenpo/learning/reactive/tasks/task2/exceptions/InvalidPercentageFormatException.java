package cl.tenpo.learning.reactive.tasks.task2.exceptions;

public class InvalidPercentageFormatException extends RuntimeException {
    public InvalidPercentageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
