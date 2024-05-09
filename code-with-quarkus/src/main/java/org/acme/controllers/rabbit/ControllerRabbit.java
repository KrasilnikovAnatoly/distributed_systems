package org.acme.controllers.rabbit;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class ControllerRabbit {
    @Channel("rabbitmq")
    Emitter<JsonObject> emitter;

    public void send(JsonObject object) {
        emitter.send(object);
    }
}
