package pers.nico.models.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import pers.nico.models.entities.Item;
import pers.nico.models.entities.Sell;
import pers.nico.models.entities.Shop;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SellRepository implements PanacheRepository<Sell> {

    public Sell findByShopAndItemsIds(Long shopId, Long itemId) {
        return find("item.id = " + itemId + " AND shop.id = " + shopId).firstResult();
    }

}
