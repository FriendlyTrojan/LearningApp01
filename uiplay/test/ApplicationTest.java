import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import org.junit.*;

import play.db.jpa.JPA;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void renderTemplate() {
        /*
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
        */
    }

    @Test
    public void personBuilder() {
        Person.Importer personImporter = new PersonManualImporter(
                "John",
                "Wayne",
                LocalDate.of(1907, 5, 26),
                "email@domain.com",
                1,
                "He liked westerns"
        );

        Person p = new Person(personImporter);

        Person.Exporter personExporter = new PersonTestExporter();
        p.export(personExporter);

        String exportedPerson = personExporter.toString();

        assertThat(exportedPerson)
                .isEqualTo("FirstName : John; LastName : Wayne; Born : 1907/05/26; Email : email@domain.com; Fav. db : 1; Notes : He liked westerns;");
    }

    @Test
    public void personSaveToHibernate() {
        running(fakeApplication(), new Runnable() {
                    public void run() {
                        Person.Importer personImporter = new PersonManualImporter(
                                "Bruce",
                                "Lee",
                                LocalDate.of(1940, 11, 27),
                                "bruce@domain.com",
                                1,
                                "He created martial art"
                        );

                        Person personToSave = new Person(personImporter);

                        JPA.withTransaction(new F.Callback0() {
                            @Override
                            public void invoke() throws Throwable {
                                Person.save(personToSave);
                            }
                        });

                        Person.Exporter personTestExporter = new PersonTestExporter();

                        JPA.withTransaction(new F.Callback0() {
                            @Override
                            public void invoke() throws Throwable {
                                Person p = JPA.em().find(Person.class, 1L);
                                p.export(personTestExporter);
                            }
                        });

                        String exportedPerson = personTestExporter.toString();

                        assertThat(exportedPerson)
                                .isEqualTo("FirstName : Bruce; LastName : Lee; Born : 1940/11/27; Email : bruce@domain.com; Fav. db : 1; Notes : He created martial art;");
                    }
                }
        );
        }

        private class PersonManualImporter extends PersonAbstractImporter{
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

    private class PersonTestExporter extends PersonAbstractExporter {
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
