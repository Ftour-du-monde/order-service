package fr.univamu.iut.orderservice.adapters.in;

import fr.univamu.iut.orderservice.domain.Order;
import fr.univamu.iut.orderservice.dto.OrderRequest;
import fr.univamu.iut.orderservice.dto.OrderResponse;
import fr.univamu.iut.orderservice.usecase.CreateOrderCommand;
import fr.univamu.iut.orderservice.usecase.port.OrderInputBoundary;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;

/**
 * Input Adapter: REST controller for handling Order operations via HTTP.
 *
 * Role: Receives HTTP requests, maps them to commands via a Mapper, delegates to the UseCase, and returns mapped HTTP responses.
 * Architecture: Interface Adapters layer. Highly decoupled; communicates to the Application layer exclusively via OrderInputBoundary.
 */
@Path("/orders")
@RequestScoped
public class OrderController {

    @Inject
    private OrderInputBoundary useCase;

    /**
     * POST /api/orders
     * Creates an order from a given set of menu IDs and quantities.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderRequest request) {
        CreateOrderCommand command = OrderMapper.toCommand(request);
        Order order = useCase.execute(command);
        OrderResponse response = OrderMapper.toResponse(order);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    /**
     * GET /api/orders/test
     * A helper endpoint to create a pre-filled mock order.
     */
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testCreateOrder() {
        OrderRequest testRequest = new OrderRequest(
                3,
                "7 avenue du Prado, 13008 Marseille",
                Arrays.asList(1, 3),
                Arrays.asList(2, 1)
        );

        CreateOrderCommand command = OrderMapper.toCommand(testRequest);
        Order order = useCase.execute(command);
        OrderResponse response = OrderMapper.toResponse(order);

        return Response.ok(response).build();
    }
}
