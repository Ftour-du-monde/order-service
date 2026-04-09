package fr.univamu.iut.orderservice.usecase;

import java.util.List;

/**
 * Command object used as data input for the UseCase.
 *
 * Role: Contains user inputs decoupled from any external REST details.
 * Architecture: Application layer. Ensures UseCase does not map directly to HTTP Requests.
 */
public class CreateOrderCommand {
    private final int subscriberId;
    private final String deliveryAddress;
    private final List<Integer> menuIds;
    private final List<Integer> quantities;

    public CreateOrderCommand(int subscriberId, String deliveryAddress, List<Integer> menuIds, List<Integer> quantities) {
        this.subscriberId = subscriberId;
        this.deliveryAddress = deliveryAddress;
        this.menuIds = menuIds;
        this.quantities = quantities;
    }

    public int getSubscriberId() { return subscriberId; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public List<Integer> getMenuIds() { return menuIds; }
    public List<Integer> getQuantities() { return quantities; }
}
