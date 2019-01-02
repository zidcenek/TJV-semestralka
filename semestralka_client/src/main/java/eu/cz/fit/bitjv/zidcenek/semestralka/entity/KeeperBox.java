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
public class KeeperBox {
    private List<Keeper> keepers = new ArrayList();

    public List<Keeper> getKeepers() {
        return keepers;
    }

    public void setKeepers(List<Keeper> keepers) {
        this.keepers = keepers;
    }
}
