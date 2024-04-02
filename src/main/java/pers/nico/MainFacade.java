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

import java.util.List;

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

    public Shop getShopByName(String name) {
        return shopService.getShopRepository().findByName(name);
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
