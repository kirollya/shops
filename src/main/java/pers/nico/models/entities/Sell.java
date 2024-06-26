package pers.nico.models.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "sell")
public class Sell {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "shop")
    private Shop shop;

    private Integer count;

    private Double cost;

    public Sell() {

    }

    public Sell(Long id, Item item, Shop shop, Integer count, Double cost) {
        this.id = id;
        this.item = item;
        this.shop = shop;
        this.count = count;
        this.cost = cost;
    }

    public Sell(Shop shop, Double totalCost) {
        this.shop = shop;
        this.cost = totalCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Shop getShop() {
        return shop;
    }

    public String getShopName() {
        return shop.getName();
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getTotalCost() {
        return this.cost * this.count;
    }

}
