package stark.dataworks.basic.io.net.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import stark.dataworks.basic.data.json.JsonSerializer;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonDecoder extends ReplayingDecoder<JsonMessage>
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        String executorMessageJsonString = new String(content, StandardCharsets.UTF_8);
        JsonMessage executorMessage = JsonSerializer.deserialize(executorMessageJsonString, JsonMessage.class);
        out.add(executorMessage);
    }
}
