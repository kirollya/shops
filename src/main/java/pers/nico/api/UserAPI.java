package pers.nico.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pers.nico.MainFacade;
import pers.nico.models.entities.Item;
import pers.nico.models.entities.Sell;
import pers.nico.models.entities.Shop;

@Path("")
public class UserAPI {

    @Inject
    MainFacade mainFacade;

    @POST
    @Path("/addItem")
    public Response addItem(Item item) {
        return Response.ok(mainFacade.addItem(item)).build();
    }

    @GET
    @Path("/items/getByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("name") String name) {
        Item item = mainFacade.getItemByName(name);
        if (item != null)
            return Response.ok(item).build();
        else
            return Response.serverError().build();
    }

    @GET
    @Path("/items/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        return Response.ok(mainFacade.getAllItems()).build();
    }

    @POST
    @Path("/addShop")
    public Response addShop(Shop shop) {
        return Response.ok(mainFacade.addShop(shop)).build();
    }

    @GET
    @Path("/shops/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShop(@PathParam("id") Long id) {
        return Response.ok(mainFacade.getShop(id)).build();
    }

    @GET
    @Path("/shops/getByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShop(@PathParam("name") String name) {
        Shop shop = mainFacade.getShopByName(name);
        if (shop != null)
            return Response.ok(shop).build();
        else
            return Response.serverError().build();
    }

    @GET
    @Path("/shops/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllShops() {
        return Response.ok(mainFacade.getAllShops()).build();
    }

    @POST
    @Path("/editShopName")
    public Response editShopName(@QueryParam("id") Long id, @QueryParam("name") String name) {
        if (mainFacade.editShopName(id, name))
            return Response.ok().build();
        else
            return Response.notModified().build();
    }

    @POST
    @Path("/addSell")
    public Response addSell(Sell sell) {
        return Response.ok(mainFacade.addSell(sell)).build();
    }

    @GET
    @Path("/sell/findByShopAndItem")
    public Response findSellByShopAndItem(@QueryParam("shopId") Long shopId, @QueryParam("itemId") Long itemId) {
        return Response.ok(mainFacade.findSellByShopAndItem(shopId, itemId)).build();
    }

    @POST
    @Path("/sell/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSell(Sell sell) {
        return Response.ok(mainFacade.updateSellById(sell)).build();
    }

    @POST
    @Path("/sell/deleteById/{id}")
    public Response deleteSellById(@PathParam("id") Long id) {
        if (mainFacade.deleteSellById(id))
            return Response.ok().build();
        else
            return  Response.notModified().build();
    }

}
