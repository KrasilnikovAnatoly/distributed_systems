package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.vertx.core.json.JsonObject;

import java.sql.*;
import java.util.*;

@Path("/hello")
public class GreetingResource {
    private List<MyClass> list =new ArrayList<>();
    public GreetingResource(){
        list.add(new MyClass("abc","123"));
        list.add(new MyClass("def","456"));
    }
    @Inject
    private MyClassRepository myClassRepository;
    private Connection connection;

    {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/Database", "postgres", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
    @PUT
    @Transactional
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> updateSurnameByNameInDatabase(@QueryParam("name") String name,
                                                       @QueryParam("surname") String surname) {
        myClassRepository.update("surname = '"+surname+"' WHERE name = '"+name+"'");
        return myClassRepository.findAll().list();
    }
    @Channel("rabbitmq")
    Emitter<JsonObject> emitter;
    @GET
    public List<MyClass> getList() {
        JsonObject object = new JsonObject();
        object.put(list.get(0).getName(), list.get(0).getSurname());
        emitter.send(object);
        return list;
    }

    @GET
    @Path("{id}")
    public MyClass getClassById(@PathParam("id") int id){
        return id< list.size() ? list.get(id) : null;
    }
    @GET
    @Path("/queryParam")
    public String getSurnameByName(@QueryParam("name") String name){
        MyClass elem= list.stream().filter(o -> name.equals(o.getName())).findFirst().orElse(null);
        return elem==null ? null: elem.getSurname();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public MyClass addObjectInListReturnObject(MyClass elem) {
        list.add(elem);
        return elem;
    }
    @POST
    @Path("/addObject")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyClass> addObjectInListReturnList(MyClass elem) {
        list.add(elem);
        return list;
    }
    @POST
    @Path("/addList")
    public MyClass addList(List<MyClass> list) {
        this.list =list;
        return this.list.get(0);
    }
    @PUT
    @Path("/change")
    public void changeSurnameByName(@QueryParam("name") String name,@QueryParam("surname") String surname) {
        MyClass elem= list.stream().filter(o -> name.equals(o.getName())).findFirst().orElse(null);
        if(elem!=null)
            list.get(list.indexOf(elem)).setSurname(surname);
        else list.add(new MyClass(name,surname));
    }
    @DELETE
    @Path("/del")
    public List<MyClass> delObjectByName(@QueryParam("name") String name){
        MyClass elem= list.stream().filter(o -> name.equals(o.getName())).findFirst().orElse(null);
        if(elem!=null)
            list.remove(list.indexOf(elem));
        return list;
    }
}
