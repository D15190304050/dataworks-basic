package stark.dataworks.basic.params;

import java.util.Objects;

public class ArgumentValidator
{
    private ArgumentValidator()
    {}

    public static String generateNonNullMessage(String paramName)
    {
        return String.format("\"%s\" must not be null.", paramName);
    }

    public static void requireNonNull(Object param, String paramName)
    {
        Objects.requireNonNull(param, generateNonNullMessage(paramName));
    }
}
