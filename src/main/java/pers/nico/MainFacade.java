package pers.nico;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import pers.nico.models.entities.Item;
import pers.nico.models.entities.Sell;
import pers.nico.models.entities.Shop;

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
    ShopService shopService;

    @Inject
    ItemService itemService;

    @Inject
    SellService sellService;

    public String addItem(Item item) {
        itemService.addItem(item);
        return "Item was added!";
    }

    public Item getItemByName(String name) {
        return itemService.getItemRepository().findByName(name);
    }

    public List<Item> getAllItems() {
        return itemService.getItemRepository().listAll();
    }

    public String addShop(Shop shop) {
        shopService.addShop(shop);
        return "Shop was added!";
    }

    public Shop getShop(Long id) {
        return shopService.getShopRepository().findById(id);
    }

    /*public Shop getShopByName(String name) {
        return shopService.getShopRepository().findByName(name);
    }*/

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
        Map<String, Integer> map = sells.stream().collect(Collectors.groupingBy(Sell::getShopName, Collectors.summingInt(Sell::getTotalCost)));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> shop: map.entrySet()) {
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

}
