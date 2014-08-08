import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import models.Person;
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

    protected class PersonManualImporter implements Person.Importer{
        private final String firstName;
        private final String lastName;
        private final LocalDate birthDate;
        private final String email;
        private final Integer favouriteDB;
        private final String notes;
        private final Integer id = 10;

        public PersonManualImporter(String firstName, String lastName, LocalDate birthDate, String email, int favouriteDB, String notes) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.email = email;
            this.favouriteDB = favouriteDB;
            this.notes = notes;
        }

        @Override
        public String provideFirstName() {
            return this.firstName;
        }

        @Override
        public String provideLastName() {
            return this.lastName;
        }

        @Override
        public String provideBirthDate() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            return this.birthDate.format(dtf);
        }

        @Override
        public String provideEmail() {
            return this.email;
        }

        @Override
        public String provideFavDB() {
            return this.favouriteDB.toString();
        }

        @Override
        public String provideNotes() {
            return this.notes;
        }

        @Override
        public String provideID() {
            return this.id.toString();
        }

        @Override
        public void open() {

        }

        @Override
        public void close() {

        }
    }

    protected class PersonExporterTest implements Person.Exporter {
        private String firstName;
        private String lastName;
        private String birthDate;
        private String email;
        private String favouriteDB;
        private String notes;
        private String id = "10";

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

        @Override
        public void addFirstName(String firstName) {
            this.firstName = firstName;
        }

        @Override
        public void addLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public void addBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        @Override
        public void addEmail(String email) {
            this.email = email;
        }

        @Override
        public void addFavDB(String favDb) {
            this.favouriteDB = favDb;
        }

        @Override
        public void addNotes(String notes) {
            this.notes = notes;
        }

        @Override
        public void addID(String id) {
            this.id = id;
        }
    }
}
