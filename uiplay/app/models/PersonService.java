package models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by karolt on 2014-08-18.
 */
public class PersonService {
    private PersonDao personDao;

    public PersonService(PersonDao personDao){
        this.personDao = personDao;
    }


    public List<PersonEntity> findAll() {
        return personDao.findAll();
    }

    public void save(PersonEntity personEntity) {
        personDao.save(personEntity);
    }

    public void deleteById(Long id) {
        personDao.deleteById(id);
    }
}
