package models;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by karolt on 2014-08-20.
 */
public class FavouriteDbService {
    private FavouriteDbDao favouriteDbDao;

    public FavouriteDbService(FavouriteDbDao favouriteDbDao){
        this.favouriteDbDao = favouriteDbDao;
    }

    public List<FavouriteDbEntity> findAll() {
        return favouriteDbDao.findAll();
    }

    public void save(FavouriteDbEntity favouriteDbEntity) {
        favouriteDbDao.save(favouriteDbEntity);
    }

    public void deleteById(Long id) {
        favouriteDbDao.deleteById(id);
    }
}
