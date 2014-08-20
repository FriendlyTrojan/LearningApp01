package models;

import play.db.jpa.JPA;
import scala.Console;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created by karolt on 2014-08-18.
 */
public class GenericDaoJpa<T extends Serializable> {
    private Class<T> tClass;

    protected GenericDaoJpa(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T findOne(Long id){
        return JPA.em().find( tClass, id );
    }

    public List<T> findAll(){
        return JPA.em()
                .createQuery("from " + tClass.getName())
                .getResultList();
    }

    public void save( T entity ){
        JPA.em().persist(entity);
    }

    public void update( T entity ){
        JPA.em().merge(entity);
    }

    public void delete( T entity ){
        JPA.em().remove( entity );
    }

    public void deleteById( Long entityId ){
        T entity = findOne( entityId );
        delete( entity );
    }
}
