package fr.univamu.iut.orderservice.domain;

/**
 * Domain entity representing an individual line in an order.
 *
 * Role: Validates basic invariants (quantity > 0) and computes the sub-price.
 * Architecture: Domain layer. It relies entirely on Composition inside Order.
 */
public class OrderLine {
    private final int menuId;
    private final String menuName;
    private final int quantity;
    private final double unitPrice;
    private final double linePrice;

    public OrderLine(int menuId, String menuName, int quantity, double unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be strictly positive.");
        if (unitPrice < 0) throw new IllegalArgumentException("Unit price cannot be negative.");
        this.menuId = menuId;
        this.menuName = menuName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.linePrice = unitPrice * quantity;
    }

    public int getMenuId() { return menuId; }
    public String getMenuName() { return menuName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getLinePrice() { return linePrice; }
}
