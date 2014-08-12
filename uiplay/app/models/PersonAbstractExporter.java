package models;

/**
 * Created by karolt on 2014-08-08.
 */
public abstract class PersonAbstractExporter implements Person.Exporter {
    protected String firstName;
    protected String lastName;
    protected String birthDate;
    protected String email;
    protected String favouriteDB;
    protected String notes;
    protected String id;

    public void addFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void addLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void addEmail(String email) {
        this.email = email;
    }

    public void addFavDB(String favDb) {
        this.favouriteDB = favDb;
    }

    public void addNotes(String notes) {
        this.notes = notes;
    }

    public void addPersonID(String id) {
        this.id = id;
    }
}
