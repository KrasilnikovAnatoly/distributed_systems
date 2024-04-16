package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.MyClass;
import org.acme.model.MyClassRepository;

import java.util.List;
@Path("/hello")
public class ControllerPanache {
    @Inject
    private MyClassRepository myClassRepository;
    @GET
    @Path("/select")
    public List<MyClass> getAllFromDatabase(){
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
        myClassRepository.delete("name",name);
        return myClassRepository.findAll().list();
    }
    @PUT
    @Transactional
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> updateSurnameByNameInDatabase(@QueryParam("name") String name,
                                                       @QueryParam("surname") String surname) {
        myClassRepository.update("surname = '"+surname+"' WHERE name = '"+name+"'");
        return myClassRepository.findAll().list();
    }
}
