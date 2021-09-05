package mvc.model;

public class Person {
    private String personName;
    private int age;

    public Person(String personName, int age) {
        this.personName = personName;
        this.age = age;
    }

    @Override
    public String toString() {
        return getPersonName();
    }

    // accessors

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
