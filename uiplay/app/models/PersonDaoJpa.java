package models;

import org.springframework.stereotype.Service;

/**
 * Created by karolt on 2014-08-18.
 */
public class PersonDaoJpa extends GenericDaoJpa<PersonEntity> implements PersonDao {
    public PersonDaoJpa() {
        super(PersonEntity.class);
    }
}
