package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by karolt on 2014-08-12.
 */
@Entity
public class FavouriteDb implements Serializable {
    @Id
    @SequenceGenerator(name = "favouritedb_generator", sequenceName = "favouritedb_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favouritedb_generator")
    @Column(name = "FAVOURITEDB_ID")
    private Long favouriteDbId;

    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="FAVOURITEDB_ID")
    private Set<Person> people;

    public static List<FavouriteDb> all() {
        return JPA.em().createQuery(
                "SELECT f FROM FavouriteDb f").getResultList();

    }

    public Long getFavouriteDbId() {
        return favouriteDbId;
    }

    public void setFavouriteDbId(Long id) {
        this.favouriteDbId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }
}

