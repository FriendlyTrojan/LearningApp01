package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by karolt on 2014-08-20.
 */
@Entity
@Table(name = "FavouriteDb")
public class FavouriteDbEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "favouritedb_generator", sequenceName = "favouritedb_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favouritedb_generator")
    @Column(name = "FAVOURITEDB_ID")
    private Long favouriteDbId;

    private String dbName;

    public FavouriteDbEntity(){}

    public FavouriteDbEntity(String dbName){this.dbName = dbName;}

    public Long getFavouriteDbId() {
        return favouriteDbId;
    }

    public void setFavouriteDbId(Long id) {
        this.favouriteDbId = id;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}


