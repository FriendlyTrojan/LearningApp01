package models;

import org.hibernate.validator.constraints.Length;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Created by karolt on 2014-08-06.
 */
@Entity
public class Person {
    @Id
    @SequenceGenerator(name = "person_generator", sequenceName = "person_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @Column(name="PERSON_ID")
    private Long personId;

    @Column(length=100, columnDefinition= "varchar(100)")
    @Constraints.MaxLength(message = "Field should not exceed 100 characters" ,value = 100)
    @Length(max=100)
    private String firstName;

    @Column(length=100, columnDefinition= "varchar(100)")
    @Constraints.MaxLength(message = "Field should not exceed 100 characters" ,value = 100)
    @Length(max=100)
    private String lastName;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(length=200, columnDefinition= "varchar(200)")
    @Constraints.MaxLength(message = "Field should not exceed 200 characters" ,value = 200)
    @Length(max=200)
    @Constraints.Required(message = "Email is required")
    @Constraints.Email(message = "Email format is not correct")
    private String email;

    @ManyToOne
    private FavouriteDb favouriteDb;

    @Column(length=5000, columnDefinition= "varchar(5000)")
    @Constraints.MaxLength(message = "Field should not exceed 5000 characters" ,value = 5000)
    @Length(max=5000)
    private String notes;

    private void setPersonId(Long personId) {
        this.personId=personId;
    }

    public Long getPersonId() {
        return personId;
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

    public FavouriteDb getFavouriteDb() {
        return favouriteDb;
    }

    public void setFavouriteDb(FavouriteDb favouriteDb) {
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
        // TODO fix mapping to favouriteDB in builder
        // this.favouriteDb = builder.provideFavDB();
        this.notes = builder.provideNotes();
        try {
            this.personId = Long.parseLong(builder.provideID());
        }
        catch(Exception ex){}

        builder.close();
    }

    public static List<Person> all() {
        return JPA.em().createQuery(
                "SELECT p FROM Person p").getResultList();

    }

    public static void save(Person person) {
        JPA.em().persist(person);
    }

    public static void delete(Long id) {
        JPA.em().remove(JPA.em().find(Person.class, id));
    }

    // TODO implement equals for purposes of using in sets
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if ( !(other instanceof Person) ) {
            return false;
        }

        if(!this.getPersonId().equals(((Person) other).getPersonId())) {
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
        /*
        result = getMother().hashCode();
        result = 29 * result + getLitterId();
        */
        Long id = this.getPersonId();
        return id == null? -1: id.intValue();
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
        void addPersonID (String id);
    }
}
