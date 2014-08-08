package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by karolt on 2014-08-06.
 */
@Entity
@SequenceGenerator(name = "Token_generator", sequenceName = "person_sequence", allocationSize = 1, initialValue = 1)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Token_generator")
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String email;

    private Integer favouriteDb;

    private String notes;

    private void setId(Long id) {
        this.id=id;
    }
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFavouriteDb() {
        return favouriteDb;
    }

    public void setFavouriteDb(int favouriteDb) {
        this.favouriteDb = favouriteDb;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Empty constructor for Hibernate purpose
    public Person(){
    }

    public Person(Importer builder){
        builder.open();

        this.firstName = builder.provideFirstName();
        this.lastName = builder.provideLastName();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.birthDate = LocalDate.parse(builder.provideBirthDate(), dtf);

        this.email = builder.provideEmail();
        this.favouriteDb = Integer.parseInt(builder.provideFavDB());
        this.notes = builder.provideNotes();
        this.id = Long.parseLong(builder.provideID());

        builder.close();
    }

    // TODO implement equals for purposes of using in sets
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if ( !(other instanceof Person) ) {
            return false;
        }

        final Person p = (Person) other;

        /*
        if ( !cat.getLitterId().equals( getLitterId() ) ) return false;
        if ( !cat.getMother().equals( getMother() ) ) return false;
        */
        return true;
    }

    // TODO implement hashCode for purposes of using in sets
    public int hashCode() {
        int result = 1;
        /*
        result = getMother().hashCode();
        result = 29 * result + getLitterId();
        */
        return result;
    }

    public void export( Exporter builder ){
        builder.addFirstName(this.firstName);
        builder.addLastName(this.lastName);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        builder.addBirthDate(this.birthDate.format(dtf));

        builder.addEmail(this.email);
        builder.addFavDB(this.favouriteDb.toString());
        builder.addNotes(this.notes);
    }

    public interface Importer {
        String provideFirstName();
        String provideLastName();
        String provideBirthDate();
        String provideEmail();
        String provideFavDB();
        String provideNotes();
        String provideID();
        void   open();
        void   close();
     }

    public interface Exporter{
        void addFirstName (String firstName);
        void addLastName (String lastName);
        void addBirthDate (String birthDate);
        void addEmail (String email);
        void addFavDB (String favDb);
        void addNotes (String notes);
        void addID (String id);
    }
}
