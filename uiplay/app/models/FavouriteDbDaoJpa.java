package models;

/**
 * Created by karolt on 2014-08-20.
 */
public class FavouriteDbDaoJpa extends GenericDaoJpa<FavouriteDbEntity> implements FavouriteDbDao {
    public FavouriteDbDaoJpa() {
        super(FavouriteDbEntity.class);
    }
}
