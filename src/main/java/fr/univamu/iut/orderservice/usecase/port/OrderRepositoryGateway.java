package fr.univamu.iut.orderservice.usecase.port;

import fr.univamu.iut.orderservice.domain.Order;

/**
 * Output Port: interface for persisting Order aggregates.
 *
 * Role: Hides the DB reality (SQL, Memory, Mongo) from the Application layer.
 * Architecture: Application layer.
 */
public interface OrderRepositoryGateway {
    int generateNewId();
    Order save(Order order);
}
