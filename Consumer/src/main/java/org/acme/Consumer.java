package org.acme;

import io.quarkus.logging.Log;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class Consumer {
    @Incoming("rabbitmq")
    public void process(JsonObject message){
        Log.info(message.toString());
    }
}
