/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import eu.cz.fit.bitjv.zidcenek.semestralka.entity.AnimalBox;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Feeding;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.FeedingBox;
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
@Path("feeding")
public class FeedingFacadeREST extends AbstractFacade<Feeding> {

    @PersistenceContext(unitName = "eu.cz.fit.bitjv.zidcenek.semestralka_semestralka_server_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public FeedingFacadeREST() {
        super(Feeding.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Feeding entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Feeding entity) {
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
    public Feeding find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FeedingBox findAllFeedings() {
        FeedingBox feedings = new FeedingBox();
        feedings . setFeedings(super.findAll());
        return feedings;
    }
    
    @GET
    @Path("search/{searched}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FeedingBox findSearchedFeedings(@PathParam("searched") String searched) {
        List<Feeding> allFeedings = super.findAll();
        List<Feeding> searchedFeedings = new ArrayList();

        for (Feeding feeding : allFeedings) {
            if ((feeding.getFoodProvider() != null && feeding.getFoodProvider().getName().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (feeding.getFeedingTime() != null && feeding.getFeedingTime().toString().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (feeding.getTypeOfFood() != null && feeding.getTypeOfFood().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (feeding.getAmount() != null && feeding.getAmount().toString().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (feeding.getSpecieFed() != null && feeding.getSpecieFed().toString().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (feeding.getFoodProvider() != null && feeding.getFoodProvider().toString().toLowerCase().contains(searched.trim().toLowerCase()))
                    ) {
                searchedFeedings.add(feeding);
            }
        }

        FeedingBox feedings = new FeedingBox();
        feedings.setFeedings(searchedFeedings);
        return feedings;
    }

    /*@GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Feeding> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }*/
    
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FeedingBox findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        FeedingBox feedings = new FeedingBox();
        feedings.setFeedings(super.findRange(new int[]{from, to}));
        return feedings;
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
