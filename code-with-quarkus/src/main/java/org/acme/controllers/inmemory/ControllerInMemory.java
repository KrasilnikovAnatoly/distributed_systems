package org.acme.controllers.inmemory;

import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.controllers.metric.ControllerMetric;
import org.acme.controllers.rabbit.ControllerRabbit;
import org.acme.model.MyClass;
import java.util.ArrayList;
import java.util.List;

@Path("/app")
public class ControllerInMemory {
    @Inject
    ControllerRabbit controllerRabbit;
    @Inject
    ControllerMetric controllerMetric;
    public ControllerInMemory() {
        list.add(new MyClass("abc", "123"));
        list.add(new MyClass("def", "456"));
    }

    private List<MyClass> list = new ArrayList<>();

    @GET
    public List<MyClass> getList() {
        controllerMetric.changeMetric();
        JsonObject object = new JsonObject();
        object.put(list.get(0).getName(), list.get(0).getSurname());
        controllerRabbit.send(object);
        return list;
    }

    @GET
    @Path("{id}")
    public MyClass getClassById(@PathParam("id") int id) {
        return id < list.size() ? list.get(id) : null;
    }

    @GET
    @Path("/queryParam")
    public String getSurnameByName(@QueryParam("name") String name) {
        MyClass elem = list.stream().filter(o -> name.equals(o.getName())).findFirst().orElse(null);
        return elem == null ? null : elem.getSurname();
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
        this.list = list;
        return this.list.get(0);
    }

    @PUT
    @Path("/change")
    public void changeSurnameByName(@QueryParam("name") String name, @QueryParam("surname") String surname) {
        MyClass elem = list.stream().filter(o -> name.equals(o.getName())).findFirst().orElse(null);
        if (elem != null)
            list.get(list.indexOf(elem)).setSurname(surname);
        else list.add(new MyClass(name, surname));
    }

    @DELETE
    @Path("/del")
    public List<MyClass> delObjectByName(@QueryParam("name") String name) {
        MyClass elem = list.stream().filter(o -> name.equals(o.getName())).findFirst().orElse(null);
        if (elem != null)
            list.remove(list.indexOf(elem));
        return list;
    }
}
