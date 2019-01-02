package eu.cz.fit.bitjv.zidcenek.semestralka.entity;

import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Animal;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Feeding;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Keeper;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-02T22:35:36")
@StaticMetamodel(Specie.class)
public class Specie_ { 

    public static volatile SingularAttribute<Specie, String> firstName;
    public static volatile ListAttribute<Specie, Keeper> keepers;
    public static volatile SetAttribute<Specie, Feeding> feedings;
    public static volatile SingularAttribute<Specie, Long> id;
    public static volatile SetAttribute<Specie, Animal> animals;
    public static volatile SingularAttribute<Specie, String> secondName;

}