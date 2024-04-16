package org.acme;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


@Path("/hello")
public class GreetingResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
}
