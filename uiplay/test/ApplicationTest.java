import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import models.Person;
import models.PersonAbstractExporter;
import models.PersonAbstractImporter;
import org.junit.*;

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
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }

    @Test
    public void personBuilder() {
        Person.Importer personImporter = new PersonManualImporter(
                "John",
                "Wayne",
                LocalDate.of(1907, 5, 26), // 1907-05-26
                "email@domain.com",
                1,
                "He liked westerns"
        );

        Person p = new Person(personImporter);

        Person.Exporter personExporter = new PersonExporterTest();
        p.export(personExporter);

        String exportedPerson = personExporter.toString();

        assertThat(exportedPerson)
                .isEqualTo("FirstName : John; LastName : Wayne; Born : 1907/05/26; Email : email@domain.com; Fav. db : 1; Notes : He liked westerns;");
    }

    protected class PersonManualImporter extends PersonAbstractImporter{
        public PersonManualImporter(String firstName, String lastName, LocalDate birthDate, String email, int favouriteDB, String notes) {
            super(firstName,
                    lastName,
                    birthDate,
                    email,
                    favouriteDB,
                    notes,
                    new Long(10)
            );
        }

        public void open(){ }
        public void close(){ }
    }

    protected class PersonExporterTest extends PersonAbstractExporter {
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
