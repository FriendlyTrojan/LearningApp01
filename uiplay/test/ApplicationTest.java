import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import org.testng.annotations.*;

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


    @Test(enabled = false)
    public void renderTemplate() {
        /*
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
        */
    }

    @Test
    public void testDao() {
        running(fakeApplication(), () -> {
            PersonDaoJpa jpa = new PersonDaoJpa();
            List<PersonEntity> people = jpa.findAll();
        }
        );
    }
}
