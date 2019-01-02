/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Keeper;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.KeeperBox;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Čeněk Žid
 */
@Stateless
@Path("keeper")
public class KeeperFacadeREST extends AbstractFacade<Keeper> {

    @PersistenceContext(unitName = "eu.cz.fit.bitjv.zidcenek.semestralka_semestralka_server_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public KeeperFacadeREST() {
        super(Keeper.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Keeper entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Keeper entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Keeper find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public KeeperBox findAllKeepers() {
        KeeperBox keepers = new KeeperBox();
        keepers . setKeepers(super.findAll());
        return keepers;
    }
    
    
    
    @GET
    @Path("search/{searched}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public KeeperBox findSearchedKeepers(@PathParam("searched") String searched) {
        List<Keeper> allKeepers = super.findAll();
        List<Keeper> searchedKeepers = new ArrayList();

        for (Keeper keeper : allKeepers) {
            if ((keeper.getName() != null && keeper.getName().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (keeper.getGender() != null && keeper.getGender().toLowerCase().contains(searched.trim().toLowerCase()))
                    ) {
                searchedKeepers.add(keeper);
            }
        }

        KeeperBox keepers = new KeeperBox();
        keepers.setKeepers(searchedKeepers);
        return keepers;
    }

    /*@GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Keeper> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }*/
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public KeeperBox findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        KeeperBox keepers = new KeeperBox();
        keepers.setKeepers(super.findRange(new int[]{from, to}));
        return keepers;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
