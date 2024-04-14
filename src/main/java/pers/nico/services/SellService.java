package pers.nico.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import pers.nico.models.entities.Sell;
import pers.nico.models.repositories.SellRepository;

@ApplicationScoped
public class SellService {

    @Inject
    SellRepository sellRepository;

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addSell(Sell sell) {
        sellRepository.persist(sell);
    }

    public SellRepository getSellRepository() {
        return sellRepository;
    }

}
