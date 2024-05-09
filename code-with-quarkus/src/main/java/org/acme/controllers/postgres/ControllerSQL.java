package org.acme.controllers.postgres;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.controllers.metric.ControllerMetric;
import org.acme.model.MyClass;
import org.acme.model.MyClassRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Path("/app")
public class ControllerSQL {
    @Inject
    MyClassRepository myClassRepository;
    @Inject
    ControllerMetric controllerMetric;
    private Connection connection;

    {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://postgres:5432/postgres", "admin", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Transactional
    @Path("/deleteSQL")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> deleteObjectByNameInDatabaseSQL(@QueryParam("name") String name) {
        controllerMetric.changeMetric();
        try {
            Statement statement = connection.createStatement();
            boolean responseStatement = statement.execute("DELETE FROM myclasses WHERE name='" + name + "'");
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
            boolean responseStatement = statement.execute("UPDATE myclasses SET surname='" +
                    surname + "' WHERE name='" + name + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myClassRepository.findAll().list();
    }
}
