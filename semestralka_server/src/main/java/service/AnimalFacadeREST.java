/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Animal;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.AnimalBox;
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
@Path("animal")
public class AnimalFacadeREST extends AbstractFacade<Animal> {

    @PersistenceContext(unitName = "eu.cz.fit.bitjv.zidcenek.semestralka_semestralka_server_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public AnimalFacadeREST() {
        super(Animal.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Animal entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Animal entity) {
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
    public Animal find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public AnimalBox findAllAnimals() {
        AnimalBox animals = new AnimalBox();
        animals . setAnimals(super.findAll());
        return animals;
    }

    /*@GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }*/

    @GET
    @Path("search/{searched}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public AnimalBox findSearchedAnimals(@PathParam("searched") String searched) {
        List<Animal> allAnimals = super.findAll();
        List<Animal> searchedAnimals = new ArrayList();

        for (Animal animal : allAnimals) {
            if ((animal.getName() != null && animal.getName().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (animal.getGender() != null && animal.getGender().toLowerCase().contains(searched.trim().toLowerCase()))
                    || (animal.getBirthDate() != null && animal.getBirthDate().toString().toLowerCase().contains(searched.trim().toLowerCase()))
                    ) {
                searchedAnimals.add(animal);
            }
        }

        AnimalBox animals = new AnimalBox();
        animals.setAnimals(searchedAnimals);
        return animals;
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public AnimalBox findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        AnimalBox animals = new AnimalBox();
        animals.setAnimals(super.findRange(new int[]{from, to}));
        return animals;
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
