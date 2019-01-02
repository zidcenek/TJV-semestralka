package eu.cz.fit.bitjv.zidcenek.semestralka.entity;

import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Specie;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-02T22:35:36")
@StaticMetamodel(Animal.class)
public class Animal_ { 

    public static volatile SingularAttribute<Animal, Specie> specie;
    public static volatile SingularAttribute<Animal, String> gender;
    public static volatile SingularAttribute<Animal, String> name;
    public static volatile SingularAttribute<Animal, Long> id;
    public static volatile SingularAttribute<Animal, LocalDate> birthDate;

}