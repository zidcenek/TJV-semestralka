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
 * @author zidcenek
 */
public class AnimalBox {
    private List<Animal> animals = new ArrayList();

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}