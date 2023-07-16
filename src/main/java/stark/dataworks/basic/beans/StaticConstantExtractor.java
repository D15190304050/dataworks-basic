package stark.dataworks.basic.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Objects;

public class StaticConstantExtractor
{
    private StaticConstantExtractor(){}

    public static <TClass, TResult> HashSet<TResult> extractConstants(Class<TClass> clazz, Class<TResult> clazzResult)
    {
        HashSet<TResult> constants = new HashSet<>();

        try
        {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields)
            {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers()))
                {
                    Object fieldValue = field.get(clazz);
                    if (fieldValue != null && Objects.equals(fieldValue.getClass(), clazzResult))
                        constants.add((TResult) fieldValue);
                }
            }
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }

        return constants;
    }
}
