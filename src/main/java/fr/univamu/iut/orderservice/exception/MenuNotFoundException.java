package fr.univamu.iut.orderservice.exception;

/**
 * Business exception thrown when a menu is not found via the external API.
 *
 * Role: Indicates a missing menu resource.
 * Architecture: Exception layer.
 */
public class MenuNotFoundException extends RuntimeException {
    private final int menuId;

    public MenuNotFoundException(int menuId) {
        super("Menu not found with ID: " + menuId);
        this.menuId = menuId;
    }

    public MenuNotFoundException(int menuId, Throwable cause) {
        super("Menu not found with ID: " + menuId, cause);
        this.menuId = menuId;
    }

    public int getMenuId() { return menuId; }
}
