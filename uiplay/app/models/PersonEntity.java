package models;

import org.hibernate.validator.constraints.Length;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by karolt on 2014-08-18.
 */
@Entity
@Table(name = "Person")
public class PersonEntity implements Serializable {
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
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
}
