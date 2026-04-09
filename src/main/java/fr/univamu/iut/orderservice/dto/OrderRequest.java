package fr.univamu.iut.orderservice.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) for receiving Order creation requests.
 *
 * Role: Maps incoming JSON requests into a Java object for the API boundary.
 * Architecture: Interface Adapters layer. Decouples HTTP data structures from Application logic.
 */
public class OrderRequest {
    private int subscriberId;
    private String deliveryAddress;
    private List<Integer> menuIds;
    private List<Integer> quantities;

    public OrderRequest() {}
    public OrderRequest(int subscriberId, String deliveryAddress, List<Integer> menuIds, List<Integer> quantities) {
        this.subscriberId = subscriberId;
        this.deliveryAddress = deliveryAddress;
        this.menuIds = menuIds;
        this.quantities = quantities;
    }

    public int getSubscriberId() { return subscriberId; }
    public void setSubscriberId(int subscriberId) { this.subscriberId = subscriberId; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public List<Integer> getMenuIds() { return menuIds; }
    public void setMenuIds(List<Integer> menuIds) { this.menuIds = menuIds; }
    public List<Integer> getQuantities() { return quantities; }
    public void setQuantities(List<Integer> quantities) { this.quantities = quantities; }
}
