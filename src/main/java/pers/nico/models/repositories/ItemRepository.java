package pers.nico.models.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import pers.nico.models.entities.Item;

@ApplicationScoped
public class ItemRepository implements PanacheRepository<Item> {

    public Item findByName(String name){
        return find("name", name).firstResult();
    }

}
