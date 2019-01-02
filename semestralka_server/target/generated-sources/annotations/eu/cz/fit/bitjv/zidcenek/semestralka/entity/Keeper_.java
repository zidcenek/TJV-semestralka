package eu.cz.fit.bitjv.zidcenek.semestralka.entity;

import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Feeding;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Specie;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-02T22:35:36")
@StaticMetamodel(Keeper.class)
public class Keeper_ { 

    public static volatile SingularAttribute<Keeper, String> gender;
    public static volatile ListAttribute<Keeper, Specie> species;
    public static volatile SetAttribute<Keeper, Feeding> feedings;
    public static volatile SingularAttribute<Keeper, String> name;
    public static volatile SingularAttribute<Keeper, Long> id;

}