package pers.nico.api;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pers.nico.MainFacade;
import pers.nico.models.entities.Item;
import pers.nico.models.entities.Sell;
import pers.nico.models.entities.Shop;
import pers.nico.reports.ReportBuilder;

@Path("/platform")
//@RolesAllowed("user")
@PermitAll
public class UserAPI {

    @Inject
    MainFacade mainFacade;

    @POST
    @Path("/addItem")
    public Response addItem(Item item) {
        return Response.ok(mainFacade.addItem(item)).build();
    }

    @POST
    @Path("/addMoreItems")
    public Response addMoreItems(Item item) {
        return Response.ok(mainFacade.spamItem(item)).build();
    }

    @GET
    @Path("/items/getByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("name") String name) {
        Item item = mainFacade.getItemByName(name);
        if (item != null)
            return Response.ok(item).build();
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
        return Response.notModified().build();
    }

    @GET
    @Path("/getTotalCosts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalCosts(@QueryParam("shopName") String shopName) {
        return Response.ok(mainFacade.getTotalCost(shopName)).build();
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
        return  Response.notModified().build();
    }

    @POST
    @Path("/shop/updCost")
    public Response shopUpdCost(@QueryParam("updVal") Integer updVal, @QueryParam("shopId") Long shopId) {
        if (mainFacade.shopUpdCost(updVal, shopId))
            return Response.ok().build();
        else
            return Response.status(500).build();
    }

    @GET
    @Path("/sell/getAll")
    public Response getAllSell() {
        return Response.ok(mainFacade.getAllSell()).build();
    }

    @POST
    @Path("/report/create/{id}")
    public Response createReport(@PathParam("id") Long id) {
        return Response.ok(new ReportBuilder(mainFacade.getAllSell(), mainFacade.getAllShops(), mainFacade.getAllItems()).build(id)).build();
    }

}
