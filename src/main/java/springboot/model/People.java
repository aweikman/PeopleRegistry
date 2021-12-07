package springboot.model;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "people")

public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "dob", nullable = false, length = 100)
    private String dateOfBirth;

    @Column(name = "last_modified", nullable = false, updatable = false, insertable = false)
    private Instant lastModified;


    @Override
    public String toString() {
        return "People{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getLastModified() {
        return lastModified;
    }
    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }
}