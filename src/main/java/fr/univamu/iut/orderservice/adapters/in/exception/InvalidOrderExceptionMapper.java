package fr.univamu.iut.orderservice.adapters.in.exception;

import fr.univamu.iut.orderservice.dto.ErrorResponse;
import fr.univamu.iut.orderservice.exception.InvalidOrderException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * ExceptionMapper for InvalidOrderException.
 *
 * Role: Catches domain-level validation exceptions and turns them into HTTP 400 Bad Request responses.
 * Architecture: Interface Adapters layer. Keeps the controller clean by centralizing error formatting.
 */
@Provider
public class InvalidOrderExceptionMapper implements ExceptionMapper<InvalidOrderException> {
    @Override
    public Response toResponse(InvalidOrderException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
