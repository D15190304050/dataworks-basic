package stark.dataworks.basic.bean;

import stark.dataworks.basic.params.ArgumentValidator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldExtractor
{
    private FieldExtractor()
    {
    }

    public static <TField, TSource> List<TField> extractField(List<TSource> sourceList, String fieldName) throws NoSuchFieldException, IllegalAccessException
    {
        ArgumentValidator.requireNonNull(fieldName, "fieldName");
        ArgumentValidator.requireNonNull(sourceList, "sourceList");
        if (sourceList.size() <= 0)
            throw new IllegalArgumentException("Size of \"sourceList\" must be greater than 0.");

        TSource firstElement = sourceList.get(0);
        Class<?> sourceClass = firstElement.getClass();
        Field targetField = sourceClass.getField(fieldName);
        boolean canAccess = targetField.canAccess(firstElement);

        List<TField> targetValues = new ArrayList<>();
        for (TSource element : sourceList)
        {
            targetField.setAccessible(true);
            TField value = (TField) targetField.get(element);
            targetValues.add(value);
        }

        targetField.setAccessible(canAccess);

        return targetValues;
    }

    public static <TSource> List<Long> extractIds(List<TSource> sourceList) throws NoSuchFieldException, IllegalAccessException
    {
        return extractField(sourceList, "ids");
    }
}
