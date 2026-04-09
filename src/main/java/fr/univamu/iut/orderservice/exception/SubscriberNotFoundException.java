package fr.univamu.iut.orderservice.exception;

/**
 * Business exception thrown when a subscriber cannot be found.
 *
 * Role: Indicates missing subscriber in the external system.
 * Architecture: Exception layer.
 */
public class SubscriberNotFoundException extends RuntimeException {
    private final int subscriberId;

    public SubscriberNotFoundException(int subscriberId) {
        super("Subscriber not found with ID: " + subscriberId);
        this.subscriberId = subscriberId;
    }

    public int getSubscriberId() { return subscriberId; }
}
