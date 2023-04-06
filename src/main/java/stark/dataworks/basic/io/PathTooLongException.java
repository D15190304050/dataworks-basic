package stark.dataworks.basic.io;

public class PathTooLongException extends RuntimeException
{
    public PathTooLongException(String message)
    {
        super(message);
    }
}
