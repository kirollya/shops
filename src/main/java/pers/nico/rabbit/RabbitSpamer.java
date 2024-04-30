package pers.nico.rabbit;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import pers.nico.models.entities.Item;

@ApplicationScoped
public class RabbitSpamer {

    @Channel("quarkus-sender")
    Emitter<Item> emitter;

    public void send(Item item) {
        //JsonObject msg = new JsonObject();
        //msg.put("greeting", "Hello rabbit");
        //emitter.send(msg);
        emitter.send(item);
    }

}
