package stark.dataworks.basic.io.net.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.basic.exceptions.QuickExceptionFactory;

import java.nio.charset.StandardCharsets;

public class JsonEncoder extends MessageToByteEncoder<JsonMessage>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, JsonMessage msg, ByteBuf out) throws Exception
    {
        if (msg == null)
            QuickExceptionFactory.nullPointerException("msg");

        String messageJsonString = JsonSerializer.serialize(msg);
        if (messageJsonString == null)
            throw new IllegalArgumentException("Cannot serialize message to JSON string, check if there is a circular reference.");

        byte[] contentBytes = messageJsonString.getBytes(StandardCharsets.UTF_8);
        int length = contentBytes.length;

        out.writeInt(length);
        out.writeBytes(contentBytes);
    }
}
