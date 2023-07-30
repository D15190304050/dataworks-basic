package stark.dataworks.basic.io.net.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message that is sent/received by netty channels, wrapped as JSON.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JsonMessage
{
    /**
     * Message body to send through network.
     */
    private Object body;

    /**
     * Type of the message, used to deserialize the message to an object.
     */
    private String type;

    public static JsonMessage toJsonMessage(Object body)
    {
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setBody(body);
        jsonMessage.setType(body.getClass().getName());
        return jsonMessage;
    }
}
