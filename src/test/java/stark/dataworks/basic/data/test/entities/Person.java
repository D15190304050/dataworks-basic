package stark.dataworks.basic.data.test.entities;

public class Person
{
    private String name;
    private int age;
    private Address address;

    public Person(String name, int age, Address address)
    {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}