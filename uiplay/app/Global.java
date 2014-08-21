/**
 * Created by karolt on 2014-08-21.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

public class Global extends GlobalSettings {

    @Override
    @Transactional
    public void onStart(Application app) {
        Logger.info("Application started...");
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:daoDi.xml");
        FavouriteDbService favouriteDbService = (FavouriteDbService) appContext.getBean("favouriteDbService");
        JPA.withTransaction(() -> {
                    if (favouriteDbService.findAll().size() == 0) {
                        Logger.info("Initializing FavouriteDb table with data");

                        favouriteDbService.save((new FavouriteDbEntity("PostgreSQL")));
                        favouriteDbService.save((new FavouriteDbEntity("MySql")));
                        favouriteDbService.save((new FavouriteDbEntity("Oracle")));
                        favouriteDbService.save((new FavouriteDbEntity("MsSql")));
                    }
                }
        );
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
}
