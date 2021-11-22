//package mvc.model;
//
//import java.time.LocalDate;
//
//public class Person {
//    private int id;
//    private String personFirstName;
//    private String personLastName;
//    private LocalDate dateOfBirth;
//    private int age;
//
//    public Person(int id, String personFirstName, String personLastName,  LocalDate dateOfBirth, int age) {
//        this.id = id;
//        this.personFirstName = personFirstName;
//        this.personLastName = personLastName;
//        this.dateOfBirth = dateOfBirth;
//        this.age = age;
//    }
//
//
//
//    @Override
//    public String toString() {
//        return getId() + " " + getPersonFirstName() + " " + getPersonLastName() + " " + getDateOfBirth() + " " + getAge();
//    }
//
//
//
//
//    // accessors
//
//    public int getId() { return id; }
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getPersonFirstName() {
//        return personFirstName;
//    }
//    public void setPersonFirstName(String personFirstName) {
//        this.personFirstName = personFirstName;
//    }
//
//    public String getPersonLastName() {
//        return personLastName;
//    }
//    public void setPersonLastName(String personLastName) {
//        this.personLastName = personLastName;
//    }
//
//    public LocalDate getDateOfBirth() { return dateOfBirth; }
//    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
//
//    public int getAge() {
//        return age;
//    }
//    public void setAge(int age) {
//        this.age = age;
//    }
//}

package mvc.model;

import org.json.JSONObject;

public class Person {

    private int id;
    private String personFirstName;
    private String personLastName;
    private String dateOfBirth;

    public Person(String personFirstName, String personLastName, String dateOfBirth, int id) {

        this.id = id;
        this.personFirstName = personFirstName;
        this.personLastName = personLastName;
        this.dateOfBirth = dateOfBirth;
    }

    public static Person fromJSONObject(JSONObject json) {
        try {
            Person person = new Person(json.getString("firstName"),
                    json.getString("lastName"), json.getString("dateOfBirth"), json.getInt("id"));
            return person;
        } catch(Exception e) {
            throw new IllegalArgumentException("Unable to parse person from provided json: " + json.toString());
        }
    }

    @Override
    public String toString() {
        return getId() + " " + getPersonFirstName() + " " + getPersonLastName() + " " + getDateOfBirth();
    }

    // accessors

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }
    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }
    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}

