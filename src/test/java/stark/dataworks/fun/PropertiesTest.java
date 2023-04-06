package stark.dataworks.fun;

import org.junit.Test;
import stark.dataworks.basic.fun.Properties;

import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesTest
{
    private static final String TEST_FILE_PATH = "src/test/resources/stark/dataworks/PropertiesTest.properties";

    @Test
    public void testLoad() throws IOException
    {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TEST_FILE_PATH));
        System.out.println(properties.getProperty("student0.name"));
        System.out.println(properties.getProperty("array"));
    }
}