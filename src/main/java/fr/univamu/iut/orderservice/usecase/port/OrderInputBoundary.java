package fr.univamu.iut.orderservice.usecase.port;

import fr.univamu.iut.orderservice.domain.Order;
import fr.univamu.iut.orderservice.usecase.CreateOrderCommand;

/**
 * Input Boundary: Interface exposing the 'Create Order' operation.
 *
 * Role: The controller calls this interface instead of the concrete UseCase directly.
 * Architecture: Application layer. Implements the Input Boundary concept of Clean Arch.
 */
public interface OrderInputBoundary {
    Order execute(CreateOrderCommand command);
}
