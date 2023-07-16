package stark.dataworks.basic.exceptions;

public class QuickExceptionFactory
{
    private QuickExceptionFactory()
    {
    }

    public static void nullPointerException(String argumentName)
    {
        throw  new NullPointerException("Argument \"" + argumentName + "\" cannot be null.");
    }
}
