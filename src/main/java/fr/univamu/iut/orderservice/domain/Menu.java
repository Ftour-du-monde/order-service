package fr.univamu.iut.orderservice.domain;

/**
 * Domain entity (Value Object) representing a snapshot of a menu.
 *
 * Role: Encapsulates the menu details fetched externally at the exact moment of order.
 * Architecture: Domain layer. Immutable to preserve the snapshot state.
 */
public class Menu {
    private final int id;
    private final String name;
    private final double totalPrice;

    public Menu(int id, String name, double totalPrice) {
        this.id = id;
        this.name = name;
        this.totalPrice = totalPrice;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getTotalPrice() { return totalPrice; }
}
