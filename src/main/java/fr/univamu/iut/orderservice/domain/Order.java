package fr.univamu.iut.orderservice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Domain entity representing an Order (Aggregate Root).
 *
 * Role: Core business object owning its subset of elements (OrderLines) and orchestrating calculation logic.
 * Architecture: Domain layer. Pure logic, strict Composition relationship with OrderLine.
 */
public class Order {
    private final int id;
    private final int subscriberId;
    private final LocalDateTime orderDate;
    private final String deliveryAddress;
    private final List<OrderLine> lines;
    private final double totalPrice;

    public Order(int id, int subscriberId, String deliveryAddress, List<OrderLine> lines) {
        this.id = id;
        this.subscriberId = subscriberId;
        this.orderDate = LocalDateTime.now();
        this.deliveryAddress = deliveryAddress;
        this.lines = new ArrayList<>(lines); // Defensive copy reinforcing Composition
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        double sum = 0;
        for (OrderLine line : lines) {
            sum += line.getLinePrice();
        }
        return sum;
    }

    public int getId() { return id; }
    public int getSubscriberId() { return subscriberId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public List<OrderLine> getLines() { return Collections.unmodifiableList(lines); }
    public double getTotalPrice() { return totalPrice; }
}
