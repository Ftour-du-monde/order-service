package fr.univamu.iut.orderservice.adapters.in;

import fr.univamu.iut.orderservice.domain.Order;
import fr.univamu.iut.orderservice.domain.OrderLine;
import fr.univamu.iut.orderservice.dto.OrderRequest;
import fr.univamu.iut.orderservice.dto.OrderResponse;
import fr.univamu.iut.orderservice.usecase.CreateOrderCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper responsible for translating between DTOs and internal Domain/Command objects.
 *
 * Role: Translates HTTP Request DTOs into Application Commands, and Domain responses into HTTP Response DTOs.
 * Architecture: Interface Adapters layer. Shields Domain and UseCase from external web contracts.
 */
public class OrderMapper {
    public static CreateOrderCommand toCommand(OrderRequest request) {
        return new CreateOrderCommand(
                request.getSubscriberId(),
                request.getDeliveryAddress(),
                request.getMenuIds(),
                request.getQuantities()
        );
    }

    public static OrderResponse toResponse(Order order) {
        List<OrderResponse.LineResponse> linesResponse = new ArrayList<>();
        for (OrderLine line : order.getLines()) {
            linesResponse.add(new OrderResponse.LineResponse(
                    line.getMenuId(),
                    line.getMenuName(),
                    line.getQuantity(),
                    line.getUnitPrice(),
                    line.getLinePrice()
            ));
        }

        return new OrderResponse(
                order.getId(),
                order.getSubscriberId(),
                order.getOrderDate().toString(),
                order.getDeliveryAddress(),
                linesResponse,
                order.getTotalPrice()
        );
    }
}
