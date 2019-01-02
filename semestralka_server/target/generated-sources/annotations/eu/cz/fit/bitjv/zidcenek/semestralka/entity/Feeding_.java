package eu.cz.fit.bitjv.zidcenek.semestralka.entity;

import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Keeper;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Specie;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-02T22:35:36")
@StaticMetamodel(Feeding.class)
public class Feeding_ { 

    public static volatile SingularAttribute<Feeding, Integer> amount;
    public static volatile SingularAttribute<Feeding, Specie> specieFed;
    public static volatile SingularAttribute<Feeding, LocalDate> feedingTime;
    public static volatile SingularAttribute<Feeding, String> typeOfFood;
    public static volatile SingularAttribute<Feeding, Long> id;
    public static volatile SingularAttribute<Feeding, Keeper> foodProvider;

}