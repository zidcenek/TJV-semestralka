/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Specie;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.SpecieBox;
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
@Path("specie")
public class SpecieFacadeREST extends AbstractFacade<Specie> {

    @PersistenceContext(unitName = "eu.cz.fit.bitjv.zidcenek.semestralka_semestralka_server_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public SpecieFacadeREST() {
        super(Specie.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Specie entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Specie entity) {
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
    public Specie find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SpecieBox findAllSpecies() {
        SpecieBox specie = new SpecieBox();
        specie . setSpecies(super.findAll());
        return specie;
    }
    
    @GET
    @Path("search/{searched}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SpecieBox findSearchedSpecies(@PathParam("searched") String searched) {
        List<Specie> allSpecies = super.findAll();
        List<Specie> searchedSpecies = new ArrayList();

        for (Specie specie : allSpecies) {
            if ((specie.getFirstName() != null && specie.getFirstName().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (specie.getSecondName() != null && specie.getSecondName().toLowerCase().contains(searched.trim().toLowerCase()))
                    ) {
                searchedSpecies.add(specie);
            }
        }

        SpecieBox species = new SpecieBox();
        species.setSpecies(searchedSpecies);
        return species;
    }

    /*@GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Specie> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }*/
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SpecieBox findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        SpecieBox species = new SpecieBox();
        species.setSpecies(super.findRange(new int[]{from, to}));
        return species;
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
