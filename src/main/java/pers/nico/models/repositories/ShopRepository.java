package pers.nico.models.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import pers.nico.models.entities.Shop;

@ApplicationScoped
public class ShopRepository implements PanacheRepository<Shop> {

    public Shop findByName(String name){
        return find("name", name).firstResult();
    }

}
