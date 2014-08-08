package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by karolt on 2014-08-08.
 */
public abstract class PersonAbstractImporter implements Person.Importer {
    protected final String firstName;
    protected final String lastName;
    protected final LocalDate birthDate;
    protected final String email;
    protected final Integer favouriteDB;
    protected final String notes;
    protected final Long id;

    protected PersonAbstractImporter(String firstName,
            String lastName,
            LocalDate birthDate,
            String email,
            Integer favouriteDB,
            String notes,
            Long id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.favouriteDB = favouriteDB;
        this.notes = notes;
        this.id = id;
    }

    public String provideFirstName() {
        return this.firstName;
    }

    public String provideLastName() {
        return this.lastName;
    }

    public String provideBirthDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return this.birthDate.format(dtf);
    }

    public String provideEmail() {
        return this.email;
    }

    public String provideFavDB() {
        return this.favouriteDB.toString();
    }

    public String provideNotes() {
        return this.notes;
    }

    public String provideID() {
        return this.id.toString();
    }

    public abstract void open();

    public abstract void close();
}
