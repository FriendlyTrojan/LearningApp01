package controllers;

import models.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import play.*;
import play.data.format.Formatters;
import play.data.validation.Constraints;

import play.mvc.*;

import scala.Console;
import scala.Console$;
import views.html.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.persistence.*;
import play.db.jpa.*;
import play.data.*;
import models.*;

public class Application extends Controller {

    static Form<PersonEntity> personForm = Form.form(PersonEntity.class);

    public static Result index() {
        return redirect(routes.Application.people());
    }

    private static PersonService personService;

    private static PersonService getPersonService() {
        if(personService == null) {
            ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:daoDi.xml");
            personService = (PersonService) appContext.getBean("personService");
        }

        return personService;
    }

    @Transactional
    public static Result people() {
        /*
        // Data entries for setup
        JPA.em().persist((new FavouriteDb("PostgreSQL")));
        JPA.em().persist((new FavouriteDb("MySql")));
        JPA.em().persist((new FavouriteDb("Oracle")));
        JPA.em().persist((new FavouriteDb("MsSql")));
        */

        return ok(
                views.html.index.render(getPersonService().findAll(), personForm)
        );
    }

    @Transactional
    public static Result newPerson() {
        Formatters.register(LocalDate.class, new Formatters.SimpleFormatter<LocalDate>() {

            @Override
            public LocalDate parse(String input, Locale l) throws ParseException {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(input, dtf);
            }

            @Override
            public String print(LocalDate localDate, Locale l) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return localDate.format(dtf);
            }
        });

        Form<PersonEntity> filledForm = personForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.index.render(getPersonService().findAll(), filledForm)
            );
        } else {
            getPersonService().save(filledForm.get());
            return redirect(routes.Application.people());
        }
    }

    @Transactional
    public static Result deletePerson(Long id) {
        getPersonService().deleteById(id);
        return redirect(routes.Application.people());
    }
}
