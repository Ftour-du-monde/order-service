package fr.univamu.iut.orderservice.exception;

/**
 * Business exception thrown when an order fails business validation.
 *
 * Role: Indicates that input data for an order creation is invalid.
 * Architecture: Exception layer, usable by Domain or Application (UseCase) layers.
 */
public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message) {
        super(message);
    }
}
