/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cz.fit.bitjv.zidcenek.semestralka.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Čeněk Žid
 */
public class SpecieBox {
    private List<Specie> species = new ArrayList();

    public List<Specie> getSpecies() {
        return species;
    }

    public void setSpecies(List<Specie> species) {
        this.species = species;
    }
}
