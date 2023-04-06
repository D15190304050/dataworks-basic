package stark.dataworks.basic.data.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer
{
    private JsonSerializer()
    {
    }

    private static final ObjectMapper MAPPER;

    static
    {
        MAPPER = new ObjectMapper();
    }

    public static String serialize(Object object)
    {
        String result = null;

        try
        {
            result = MAPPER.writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
//            e.printStackTrace();
        }

        return result;
    }

    public static <T> T deserialize(String value, Class<T> clazz)
    {
        try
        {
            return MAPPER.readValue(value, clazz);
        }
        catch (JsonProcessingException e)
        {
//            e.printStackTrace();
            return null;
        }
    }

    public static <T> T deserialize(JsonNode jsonNode, Class<T> clazz)
    {
        return MAPPER.convertValue(jsonNode, clazz);
    }
}
