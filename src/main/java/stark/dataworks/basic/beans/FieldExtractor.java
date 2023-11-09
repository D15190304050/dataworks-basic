package stark.dataworks.basic.beans;

import stark.dataworks.basic.params.ArgumentValidator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FieldExtractor
{
    private FieldExtractor()
    {
    }

    public static <TField, TSource> List<TField> extractField(List<TSource> sourceList, Function<TSource, TField> mapper)
    {
        ArgumentValidator.requireNonNull(mapper, "mapper");
        ArgumentValidator.requireNonNull(sourceList, "sourceList");

        return sourceList.stream().map(mapper).toList();
    }
}
