package stark.dataworks.io;

public class PathTooLongException extends RuntimeException
{
    public PathTooLongException(String message)
    {
        super(message);
    }
}
