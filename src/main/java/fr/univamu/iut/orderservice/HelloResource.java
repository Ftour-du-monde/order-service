package fr.univamu.iut.orderservice;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/order-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "CIAO, World!";
    }
}