package models;

import java.util.List;

/**
 * Created by karolt on 2014-08-18.
 */
public interface PersonDao {
    PersonEntity findOne(Long id);
    List<PersonEntity> findAll();
    void save(PersonEntity entity);
    void update(PersonEntity entity);
    void delete(PersonEntity entity);
    void deleteById(Long entityId);
}
