package springboot.model;

import javax.persistence.*;


@Entity
@Table(name = "people")

public class People {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "firstName", nullable = false, length = 100)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "dob", nullable = false, length = 100)
    private String dateOfBirth;

    public People() {}

    @Override
    public String toString() {
        return firstName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getDateOfBirth(){
        return dateOfBirth;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String firstName) {
        this.lastName = lastName;
    }
    public void setDateOfBirth(String firstName) {
        this.dateOfBirth = dateOfBirth;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}