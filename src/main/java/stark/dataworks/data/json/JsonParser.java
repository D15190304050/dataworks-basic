package stark.dataworks.data.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonParser
{
    private JsonParser()
    {
    }

    private static final ObjectMapper MAPPER;

    static
    {
        MAPPER = new ObjectMapper();
    }

    public static boolean isValid(String string)
    {
        try
        {
            MAPPER.readTree(string);
            return true;
        }
        catch (JsonProcessingException e)
        {
            return false;
        }
    }

    public static JsonNode parseJson(String content) throws JsonProcessingException
    {
        return MAPPER.readTree(content);
    }

    public static JsonNode parseObjectToJsonNode(Object object)
    {
        return MAPPER.convertValue(object, JsonNode.class);
    }

    public static ObjectNode createObjectNode()
    {
        return MAPPER.createObjectNode();
    }

    public static ArrayNode createArrayNode()
    {
        return MAPPER.createArrayNode();
    }
}
