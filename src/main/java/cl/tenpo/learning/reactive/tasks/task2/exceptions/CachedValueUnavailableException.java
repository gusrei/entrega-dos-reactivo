package cl.tenpo.learning.reactive.tasks.task2.exceptions;

public class CachedValueUnavailableException extends RuntimeException {
    public CachedValueUnavailableException(String message) {
        super(message);
    }
}