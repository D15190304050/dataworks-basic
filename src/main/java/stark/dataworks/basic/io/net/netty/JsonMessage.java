package stark.dataworks.basic.io.net.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message that is sent/received by the executor.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JsonMessage
{
    /**
     * Message body to send through network.
     */
    private Object message;

    /**
     * Type of the message, used to deserialize the message to an object.
     */
    private String type;

    public static JsonMessage toJsonMessage(Object message)
    {
        JsonMessage executorMessage = new JsonMessage();
        executorMessage.setMessage(message);
        executorMessage.setType(message.getClass().getName());
        return executorMessage;
    }
}
