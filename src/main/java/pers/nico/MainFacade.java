package pers.nico;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import pers.nico.metrics.ObjectCounterMetric;
import pers.nico.models.entities.Item;
import pers.nico.models.entities.Sell;
import pers.nico.models.entities.Shop;

import pers.nico.rabbit.RabbitSpamer;
import pers.nico.services.ItemService;
import pers.nico.services.SellService;
import pers.nico.services.ShopService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class MainFacade {

    @Inject
    EntityManager entityManager;

    @Inject
    RabbitSpamer rabbitSpamer;

    @Inject
    ObjectCounterMetric objectCounterMetric;

    @Inject
    ShopService shopService;

    @Inject
    ItemService itemService;

    @Inject
    SellService sellService;

    public String addItem(Item item) {
        itemService.addItem(item);
        objectCounterMetric.putObj();
        return "Item was added!";
    }

    @Blocking
    @Incoming("quarkus-consumer")
    public void addItem(JsonObject jsonItem) {
        Item item = jsonItem.mapTo(Item.class);
        itemService.addItem(item);
    }

    public String spamItem(Integer count) {
        Item item = new Item();
        item.setName("Spam item");
        item.setDescription("Item for spam");
        for (int i = 0; i < count; i++) {
            rabbitSpamer.send(item);
        }
        objectCounterMetric.putObj(count);
        return "Fine";
    }

    public Item getItemByName(String name) {
        return itemService.getItemRepository().findByName(name);
    }

    public List<Item> getAllItems() {
        return itemService.getItemRepository().listAll();
    }

    public String addShop(Shop shop) {
        shopService.addShop(shop);
        objectCounterMetric.putObj();
        return "Shop was added!";
    }

    public Shop getShop(Long id) {
        return shopService.getShopRepository().findById(id);
    }

    public Shop getShopByName(String name) {
        Query query = entityManager.createQuery("FROM Shop WHERE name = :name");
        query.setParameter("name", name);
        return (Shop) query.getSingleResult();
    }

    public List<Shop> getAllShops() {
        return shopService.getShopRepository().listAll();
    }

    @Transactional
    public Boolean editShopName(Long id, String name) {
        Query query = entityManager.createQuery("UPDATE Shop set name = :shopName WHERE id = :shopId");
        query.setParameter("shopName", name);
        query.setParameter("shopId", id);
        return query.executeUpdate() == 0;
    }

    public List<Map<String, Object>> getTotalCost(String shopName) {
        List<Sell> sells = sellService.getSellRepository().listAll();
        Map<String, Double> map = sells.stream().collect(Collectors.groupingBy(Sell::getShopName, Collectors.summingDouble(Sell::getTotalCost)));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Double> shop: map.entrySet()) {
            Map<String, Object> newElem = new HashMap<>();
            newElem.put("name", shop.getKey());
            newElem.put("totalCost", shop.getValue());
            if (shopName == null || shop.getKey().toLowerCase().startsWith(shopName.toLowerCase()))
                result.add(newElem);
        }
        return result;
    }

    public String addSell(Sell sell) {
        sellService.addSell(sell);
        objectCounterMetric.putObj();
        return "Sell was added!";
    }

    public Sell findSellByShopAndItem(Long shopId, Long itemId) {
        return sellService.getSellRepository().findByShopAndItemsIds(shopId, itemId);
    }

    @Transactional
    public Boolean updateSellById(Sell sell) {
        Sell updSell = sellService.getSellRepository().findById(sell.getId());
        updSell.setItem(sell.getItem());
        updSell.setShop(sell.getShop());
        updSell.setCost(sell.getCost());
        updSell.setCount(sell.getCount());
        sellService.getSellRepository().persist(updSell);
        return true;
    }

    @Transactional
    public Boolean deleteSellById(Long id) {
        return sellService.getSellRepository().deleteById(id);
    }

    @Transactional
    public Boolean shopUpdCost(Integer updVal, Long shopId) {
        if (updVal.equals(0))
            return true;
        Shop shop = shopService.getShopRepository().findById(shopId);
        double val = 1 + (double)updVal / 100;
        Query query = entityManager.createQuery("UPDATE Sell set cost = cost * :val WHERE shop = :shop");
        query.setParameter("val", val);
        query.setParameter("shop", shop);
        try {
            int result = query.executeUpdate();
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    public List<Sell> getAllSell() {
        return sellService.getSellRepository().listAll();
    }

}
