package models;

import java.util.List;

/**
 * Created by karolt on 2014-08-20.
 */
public interface FavouriteDbDao {
    FavouriteDbEntity findOne(Long id);
    List<FavouriteDbEntity> findAll();
    void save(FavouriteDbEntity entity);
    void update(FavouriteDbEntity entity);
    void delete(FavouriteDbEntity entity);
    void deleteById(Long entityId);
}
