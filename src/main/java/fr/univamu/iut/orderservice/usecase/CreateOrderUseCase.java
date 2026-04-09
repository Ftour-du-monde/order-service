package fr.univamu.iut.orderservice.usecase;

import fr.univamu.iut.orderservice.domain.Order;
import fr.univamu.iut.orderservice.domain.OrderLine;
import fr.univamu.iut.orderservice.domain.Menu;
import fr.univamu.iut.orderservice.domain.Subscriber;
import fr.univamu.iut.orderservice.exception.InvalidOrderException;
import fr.univamu.iut.orderservice.usecase.port.MenuClientPort;
import fr.univamu.iut.orderservice.usecase.port.SubscriberGateway;
import fr.univamu.iut.orderservice.usecase.port.OrderRepositoryGateway;
import fr.univamu.iut.orderservice.usecase.port.OrderInputBoundary;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor: Concrete implementation tracking the path to create an Order.
 *
 * Role: Orchestrates validation, gateways, and initiates domain processing.
 * Architecture: Application layer. Contains no technical frameworks.
 */
public class CreateOrderUseCase implements OrderInputBoundary {
    private final MenuClientPort menuClient;
    private final SubscriberGateway subscriberGateway;
    private final OrderRepositoryGateway orderRepository;

    public CreateOrderUseCase(MenuClientPort menuClient, SubscriberGateway subscriberGateway, OrderRepositoryGateway orderRepository) {
        this.menuClient = menuClient;
        this.subscriberGateway = subscriberGateway;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(CreateOrderCommand command) {
        validateCommand(command);

        Subscriber subscriber = subscriberGateway.findSubscriberById(command.getSubscriberId());

        List<Integer> menuIds = command.getMenuIds();
        List<Integer> quantities = command.getQuantities();

        List<OrderLine> lines = new ArrayList<>();
        for (int i = 0; i < menuIds.size(); i++) {
            int menuId = menuIds.get(i);
            int quantity = quantities.get(i);

            Menu menu = menuClient.getMenuById(menuId);
            OrderLine line = new OrderLine(menu.getId(), menu.getName(), quantity, menu.getTotalPrice());
            lines.add(line);
        }

        int newId = orderRepository.generateNewId();
        Order order = new Order(newId, command.getSubscriberId(), command.getDeliveryAddress(), lines);

        return orderRepository.save(order);
    }

    private void validateCommand(CreateOrderCommand command) {
        if (command.getMenuIds() == null || command.getMenuIds().isEmpty()) {
            throw new InvalidOrderException("The menu list cannot be empty.");
        }
        if (command.getQuantities() == null || command.getQuantities().isEmpty()) {
            throw new InvalidOrderException("The quantity list cannot be empty.");
        }
        if (command.getMenuIds().size() != command.getQuantities().size()) {
            throw new InvalidOrderException("The number of menus does not match the number of quantities.");
        }
        if (command.getDeliveryAddress() == null || command.getDeliveryAddress().isBlank()) {
            throw new InvalidOrderException("Delivery address is required.");
        }
    }
}
