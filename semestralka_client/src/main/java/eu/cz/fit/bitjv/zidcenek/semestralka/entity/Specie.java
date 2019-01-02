/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cz.fit.bitjv.zidcenek.semestralka.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zidcenek
 */
@Entity
@XmlRootElement
public class Specie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String secondName;

    public Specie(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Specie() {
    }
    
    
    
    //@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToMany()
    @JoinTable(
            name = "specie_keeper",
            joinColumns = @JoinColumn (name = "specie_id"),
            inverseJoinColumns = @JoinColumn (name = "keeper_id")
    )
    private List<Keeper> keepers;

    public String keepersToString(){
        String tmp = "";
        for ( Keeper item : keepers ){
            if ( tmp . equals("") )
                tmp = item . toString();
            else
                tmp = tmp + ", " + item . toString();
        }
        return tmp;
    }
    
    public void addKeeper(Keeper keep) {
        keepers.add(keep);
        keep.getSpecies().add(this);
    }
    
    @OneToMany(mappedBy = "specie", cascade = CascadeType.DETACH)
    private Set<Animal> animals;
    
    /*@OneToMany(mappedBy="specie", cascade = CascadeType.ALL)
    private List<Animal> animals;*/
    
    @OneToMany(mappedBy="specieFed")
    private Set<Feeding> feedings;

    public List<Keeper> getKeepers() {
        return keepers;
    }

    public void setKeepers(List<Keeper> keepers) {
        this.keepers = keepers;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public Set<Feeding> getFeedings() {
        return feedings;
    }

    public void setFeedings(Set<Feeding> feedings) {
        this.feedings = feedings;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specie)) {
            return false;
        }
        Specie other = (Specie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName;
    }
    
}
