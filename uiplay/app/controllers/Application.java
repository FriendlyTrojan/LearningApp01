package controllers;

import models.Person;
import models.PersonAbstractExporter;
import models.PersonAbstractImporter;
import play.*;
import play.data.format.Formatters;
import play.data.validation.Constraints;

import play.mvc.*;

import views.html.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.*;
import play.db.jpa.*;
import play.data.*;
import models.*;

public class Application extends Controller {

    static Form<Person> personForm = Form.form(Person.class);

    public static Result index() {
        return redirect(routes.Application.people());
    }

    @Transactional
    public static Result people() {
        return ok(
                views.html.index.render(Person.all(), personForm)
        );
    }

    @Transactional
    public static Result newPerson() {
        Formatters.register(LocalDate.class, new Formatters.SimpleFormatter<LocalDate>() {

            // private Pattern datePattern = Pattern.compile("(\\\\d+)(?-[\\\\s:\\\\._\\\\-]+([0-5]\\\\d))?");

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

        Form<Person> filledForm = personForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.index.render(Person.all(), filledForm)
            );
        } else {
            Person.save(filledForm.get());
            return redirect(routes.Application.people());
        }
    }

    @Transactional
    public static Result deletePerson(Long id) {
        Person.delete(id);
        return redirect(routes.Application.people());
    }

    private static class PersonManualImporter extends PersonAbstractImporter {
        public PersonManualImporter(String firstName, String lastName, LocalDate birthDate, String email, int favouriteDB, String notes) {
            super(firstName,
                    lastName,
                    birthDate,
                    email,
                    favouriteDB,
                    notes,
                    null
            );
        }

        public void open(){ }
        public void close(){ }
    }

    private static class PersonTestExporter extends PersonAbstractExporter {
        @Override
        public String toString() {
            return String.format(
                    "FirstName : %s; LastName : %s; Born : %s; Email : %s; Fav. db : %s; Notes : %s;",
                    this.firstName,
                    this.lastName,
                    this.birthDate,
                    this.email,
                    this.favouriteDB,
                    this.notes
            );
        }
    }
}
