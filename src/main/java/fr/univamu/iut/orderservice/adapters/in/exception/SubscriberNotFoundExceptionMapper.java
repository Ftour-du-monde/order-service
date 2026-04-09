package fr.univamu.iut.orderservice.adapters.in.exception;

import fr.univamu.iut.orderservice.dto.ErrorResponse;
import fr.univamu.iut.orderservice.exception.SubscriberNotFoundException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * ExceptionMapper for SubscriberNotFoundException.
 *
 * Role: Maps 'Subscriber Not Found' business exceptions to HTTP 404 responses.
 * Architecture: Interface Adapters layer.
 */
@Provider
public class SubscriberNotFoundExceptionMapper implements ExceptionMapper<SubscriberNotFoundException> {
    @Override
    public Response toResponse(SubscriberNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
