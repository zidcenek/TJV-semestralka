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
public class FeedingBox {
    private List<Feeding> feedings = new ArrayList();

    public List<Feeding> getFeedings() {
        return feedings;
    }

    public void setFeedings(List<Feeding> feedings) {
        this.feedings = feedings;
    }
}