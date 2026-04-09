package fr.univamu.iut.orderservice.usecase.port;

import fr.univamu.iut.orderservice.domain.Menu;

/**
 * Output Port: interface indicating how the system requires menus to be fetched.
 *
 * Role: Enforces Dependency Inversion Principle.
 * Architecture: Application layer.
 */
public interface MenuClientPort {
    Menu getMenuById(int menuId);
}
