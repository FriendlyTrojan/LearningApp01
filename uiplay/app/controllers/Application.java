package controllers;

import models.Person;
import play.*;
import play.mvc.*;

import views.html.*;

import java.util.Date;

import javax.persistence.*;
import play.db.jpa.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
