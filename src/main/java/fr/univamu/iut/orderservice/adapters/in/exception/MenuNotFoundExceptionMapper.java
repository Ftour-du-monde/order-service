package fr.univamu.iut.orderservice.adapters.in.exception;

import fr.univamu.iut.orderservice.dto.ErrorResponse;
import fr.univamu.iut.orderservice.exception.MenuNotFoundException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * ExceptionMapper for MenuNotFoundException.
 *
 * Role: Maps 'Menu Not Found' business exceptions to HTTP 404 responses.
 * Architecture: Interface Adapters layer.
 */
@Provider
public class MenuNotFoundExceptionMapper implements ExceptionMapper<MenuNotFoundException> {
    @Override
    public Response toResponse(MenuNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
