/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cz.fit.bitjv.zidcenek.semestralka.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zidcenek
 */
@Entity
@XmlRootElement
public class Feeding implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate feedingTime;
    private String typeOfFood;
    private Integer amount;
    
    //@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="specie_id", nullable=true)
    private Specie specieFed;
    
    //@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="keeper_id", nullable=true)
    private Keeper foodProvider;

    public Specie getSpecieFed() {
        return specieFed;
    }

    public void setSpecieFed(Specie specieFed) {
        this.specieFed = specieFed;
    }

    public Keeper getFoodProvider() {
        return foodProvider;
    }

    public void setFoodProvider(Keeper foodProvider) {
        this.foodProvider = foodProvider;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFeedingTime() {
        return feedingTime;
    }

    public void setFeedingTime(LocalDate feedingTime) {
        this.feedingTime = feedingTime;
    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
        if (!(object instanceof Feeding)) {
            return false;
        }
        Feeding other = (Feeding) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.cz.fit.bitjv.zidcenek.semestralka.entity.Feeding[ id=" + id + " ]";
    }
    
}
