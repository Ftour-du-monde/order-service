package fr.univamu.iut.orderservice.usecase.port;

import fr.univamu.iut.orderservice.domain.Subscriber;

/**
 * Output Port: interface defining the retrieval of Subscribers.
 *
 * Role: Allows UseCase to request subscriber data without depending on HTTP libraries.
 * Architecture: Application layer. Concrete implementations dwell in the Adapters layer.
 */
public interface SubscriberGateway {
    Subscriber findSubscriberById(int subscriberId);
}
