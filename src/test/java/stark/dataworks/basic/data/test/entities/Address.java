package stark.dataworks.basic.data.test.entities;

public class Address
{
    private String street;
    private String city;
    private String zipcode;

    public Address(String street, String city, String zipcode)
    {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }
}