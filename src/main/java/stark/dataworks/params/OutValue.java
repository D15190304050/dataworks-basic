package stark.dataworks.params;

public class OutValue<T>
{
    private T value;

    public OutValue()
    {
        value = null;
    }

    public OutValue(T value)
    {
        this.value = value;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }
}
