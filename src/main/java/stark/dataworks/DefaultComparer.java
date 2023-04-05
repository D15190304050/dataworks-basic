package stark.dataworks;

import java.util.Objects;

public class DefaultComparer<T> implements IEqualityComparer<T>
{
    @Override
    public int compare(T x, T y)
    {
        if ((x == null) && (y == null))
            return 0;

        if ((x == null) && (y != null))
            return -1;

        if ((x != null) && (y == null))
            return 1;

        if ((x == y) || (x.equals(y)))
            return 0;

        return getHashCode(x) - getHashCode(y);
    }

    @Override
    public int getHashCode(T o)
    {
        if (o == null)
            throw new NullPointerException("Argument \"o\" cannot be null.");

        return Objects.hash(o);
    }
}
