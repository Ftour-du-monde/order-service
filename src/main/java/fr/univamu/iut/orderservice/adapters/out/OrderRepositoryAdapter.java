package fr.univamu.iut.orderservice.adapters.out;

import fr.univamu.iut.orderservice.domain.Order;
import fr.univamu.iut.orderservice.usecase.port.OrderRepositoryGateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Output Adapter: In-memory persistence engine for Orders.
 *
 * Role: Persists generated orders. Acts as a mock repository.
 * Architecture: Interface Adapters layer. Translates the UseCase need to a physical persistence mechanism (memory here).
 */
public class OrderRepositoryAdapter implements OrderRepositoryGateway {
    private static final AtomicInteger counter = new AtomicInteger(1);
    private final Map<Integer, Order> orders = new ConcurrentHashMap<>();

    @Override
    public int generateNewId() {
        return counter.getAndIncrement();
    }

    @Override
    public Order save(Order order) {
        orders.put(order.getId(), order);
        return order;
    }
}
