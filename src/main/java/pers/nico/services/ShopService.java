package pers.nico.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pers.nico.models.entities.Shop;
import pers.nico.models.repositories.ShopRepository;

@ApplicationScoped
public class ShopService {

    @Inject
    ShopRepository shopRepository;

    @Transactional
    public void addShop(Shop shop) {
        shopRepository.persist(shop);
    }

    public ShopRepository getShopRepository() {
        return shopRepository;
    }
}
