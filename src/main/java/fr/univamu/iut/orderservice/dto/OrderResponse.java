package fr.univamu.iut.orderservice.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) for sending Order creation responses.
 *
 * Role: Maps the Domain Order entity to a JSON-friendly output structure.
 * Architecture: Interface Adapters layer.
 */
public class OrderResponse {
    private int id;
    private int subscriberId;
    private String orderDate;
    private String deliveryAddress;
    private List<LineResponse> lines;
    private double totalPrice;

    public OrderResponse() {}

    public OrderResponse(int id, int subscriberId, String orderDate, String deliveryAddress, List<LineResponse> lines, double totalPrice) {
        this.id = id;
        this.subscriberId = subscriberId;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.lines = lines;
        this.totalPrice = totalPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSubscriberId() { return subscriberId; }
    public void setSubscriberId(int subscriberId) { this.subscriberId = subscriberId; }
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public List<LineResponse> getLines() { return lines; }
    public void setLines(List<LineResponse> lines) { this.lines = lines; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public static class LineResponse {
        private int menuId;
        private String menuName;
        private int quantity;
        private double unitPrice;
        private double linePrice;

        public LineResponse() {}
        public LineResponse(int menuId, String menuName, int quantity, double unitPrice, double linePrice) {
            this.menuId = menuId;
            this.menuName = menuName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.linePrice = linePrice;
        }

        public int getMenuId() { return menuId; }
        public void setMenuId(int menuId) { this.menuId = menuId; }
        public String getMenuName() { return menuName; }
        public void setMenuName(String menuName) { this.menuName = menuName; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getUnitPrice() { return unitPrice; }
        public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
        public double getLinePrice() { return linePrice; }
        public void setLinePrice(double linePrice) { this.linePrice = linePrice; }
    }
}
