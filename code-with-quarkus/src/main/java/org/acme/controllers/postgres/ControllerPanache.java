package org.acme.controllers.postgres;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.controllers.metric.ControllerMetric;
import org.acme.model.MyClass;
import org.acme.model.MyClassRepository;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/app")
public class ControllerPanache {
    @Inject
    MyClassRepository myClassRepository;
    @Inject
    ControllerMetric controllerMetric;
    @GET
    @Path("/select")
    public List<MyClass> getAllFromDatabase() {
        controllerMetric.changeMetric();
        Log.log(Logger.Level.INFO, "Select query");
        return myClassRepository.findAll().list();
    }

    @POST
    @Transactional
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> addObjectInDatabase(MyClass element) {
        myClassRepository.persist(element);
        return myClassRepository.findAll().list();
    }

    @DELETE
    @Transactional
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> deleteObjectByNameInDatabase(@QueryParam("name") String name) {
        myClassRepository.delete("name", name);
        return myClassRepository.findAll().list();
    }

    @PUT
    @Transactional
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> updateSurnameByNameInDatabase(@QueryParam("name") String name,
                                                       @QueryParam("surname") String surname) {
        myClassRepository.update("surname = '" + surname + "' WHERE name = '" + name + "'");
        return myClassRepository.findAll().list();
    }
}
