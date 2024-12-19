package stark.dataworks.basic.data;

import junit.framework.TestCase;
import stark.dataworks.basic.data.test.entities.Address;
import stark.dataworks.basic.data.test.entities.Person;

import java.util.HashMap;

public class ObjectToHashMapConverterTest extends TestCase
{

    public void testConvertObjectToHashMap()
    {
        Address address = new Address("123 Street", "City", "12345");
        Person person = new Person("John Doe", 30, address);

        HashMap<Object, Object> personMap = ObjectToHashMapConverter.convertObjectToHashMap(person);
        System.out.println(personMap);
    }
}