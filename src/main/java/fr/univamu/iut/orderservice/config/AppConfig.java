package fr.univamu.iut.orderservice.config;

import fr.univamu.iut.orderservice.adapters.out.SubscriberHttpGateway;
import fr.univamu.iut.orderservice.adapters.out.OrderRepositoryAdapter;
import fr.univamu.iut.orderservice.adapters.out.MenuClientAdapter;
import fr.univamu.iut.orderservice.usecase.CreateOrderUseCase;
import fr.univamu.iut.orderservice.usecase.port.SubscriberGateway;
import fr.univamu.iut.orderservice.usecase.port.OrderInputBoundary;
import fr.univamu.iut.orderservice.usecase.port.OrderRepositoryGateway;
import fr.univamu.iut.orderservice.usecase.port.MenuClientPort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

/**
 * Configuration class resolving CDI dependencies (Composition Root).
 *
 * Role: Glues all layers together by instantiating the concrete classes and satisfying interfaces.
 * Architecture: Outer infrastructure/framework layer.
 */
@ApplicationScoped
public class AppConfig {
    private static final String MENU_API_BASE_URL = "http://localhost:3004";
    private static final String SUBSCRIBER_API_BASE_URL = "http://localhost:3003";

    @Produces
    @ApplicationScoped
    public MenuClientPort menuClientPort() {
        return new MenuClientAdapter(MENU_API_BASE_URL);
    }

    @Produces
    @ApplicationScoped
    public SubscriberGateway subscriberGateway() {
        return new SubscriberHttpGateway(SUBSCRIBER_API_BASE_URL);
    }

    @Produces
    @ApplicationScoped
    public OrderRepositoryGateway orderRepositoryGateway() {
        return new OrderRepositoryAdapter();
    }

    @Produces
    @ApplicationScoped
    public OrderInputBoundary orderInputBoundary(
            MenuClientPort menuClient,
            SubscriberGateway subscriberGateway,
            OrderRepositoryGateway orderRepository) {
        return new CreateOrderUseCase(menuClient, subscriberGateway, orderRepository);
    }
}
