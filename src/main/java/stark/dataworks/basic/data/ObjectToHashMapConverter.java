package stark.dataworks.basic.data;

import stark.dataworks.basic.beans.FieldExtractor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public final class ObjectToHashMapConverter
{
    private ObjectToHashMapConverter()
    {
    }

    public static HashMap<String, Object> convertObjectToHashMap(Object object)
    {
        if (object == null)
            return null;

        Class<?> clazz = object.getClass();
        if (isPrimitiveOrWrapper(clazz))
            throw new IllegalArgumentException("Cannot convert primitive or wrapper type " + clazz.getName() + " to HashMap.");

        HashMap<String, Object> map = new HashMap<>();

        List<Field> fields = FieldExtractor.getAllFields(clazz);
        for (Field field : fields)
        {
            field.setAccessible(true);

            try
            {
                Object value = field.get(object);
                Class<?> fieldType = field.getType();
                if (isPrimitiveOrWrapper(fieldType) || fieldType == String.class)
                    map.put(field.getName(), value);
                else
                    map.put(field.getName(), convertObjectToHashMap(value));
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException("Error accessing field " + field.getName() + " of class " + clazz.getName(), e);
            }
        }

        return map;
    }

    public static boolean isPrimitiveOrWrapper(Class<?> clazz)
    {
        return clazz.isPrimitive() ||
            clazz == Boolean.class ||
            clazz == Byte.class ||
            clazz == Character.class ||
            clazz == Short.class ||
            clazz == Integer.class ||
            clazz == Long.class ||
            clazz == Float.class ||
            clazz == Double.class;
    }
}
