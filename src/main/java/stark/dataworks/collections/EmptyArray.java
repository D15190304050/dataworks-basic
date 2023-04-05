package stark.dataworks.collections;

public class EmptyArray
{
    private EmptyArray(){}
    public static <T> T[] getValue()
    {
        return (T[])new Object[0];
    }
}
