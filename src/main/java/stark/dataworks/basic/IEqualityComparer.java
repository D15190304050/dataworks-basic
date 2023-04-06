package stark.dataworks.basic;

public interface IEqualityComparer<T> extends IComparer<T>
{
    default boolean equals(T x, T y)
    {
        return compare(x, y) == 0;
    }

    int getHashCode(T o);
}
