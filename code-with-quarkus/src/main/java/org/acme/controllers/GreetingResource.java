package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.acme.controllers.ControllerInMemory;
import org.acme.controllers.ControllerPanache;
import org.acme.controllers.ControllerSQL;

@Path("/hello")
public class GreetingResource {
    @Inject
    ControllerInMemory controllerInMemory;
    @Inject
    ControllerSQL controllerSQL;
    @Inject
    ControllerPanache controllerPanache;
}
