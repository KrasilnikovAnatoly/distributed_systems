package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.MyClass;
import org.acme.model.MyClassRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
@Path("/hello")
public class ControllerSQL {
    @Inject
    private MyClassRepository myClassRepository;
    private Connection connection;

    {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/Database", "postgres", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @DELETE
    @Transactional
    @Path("/deleteSQL")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> deleteObjectByNameInDatabaseSQL(@QueryParam("name") String name) {
        try {
            Statement statement = connection.createStatement();
            boolean responseStatement = statement.execute("DELETE FROM MyClasses WHERE name='"+name+"'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myClassRepository.findAll().list();
    }
    @PUT
    @Transactional
    @Path("/updateSQL")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> updateSurnameByNameInDatabaseSQL(@QueryParam("name") String name,
                                                          @QueryParam("surname") String surname) {
        try {
            Statement statement = connection.createStatement();
            boolean responseStatement = statement.execute("UPDATE MyClasses SET surname='"+
                    surname+"' WHERE name='"+name+"'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myClassRepository.findAll().list();
    }
}
