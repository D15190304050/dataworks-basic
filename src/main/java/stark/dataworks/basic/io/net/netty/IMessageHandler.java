package stark.dataworks.basic.io.net.netty;

public interface IMessageHandler<T>
{
    void handle(T arg);
}
