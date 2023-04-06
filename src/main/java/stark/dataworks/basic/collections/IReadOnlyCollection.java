package stark.dataworks.basic.collections;

public interface IReadOnlyCollection<T> extends Iterable<T>
{
    /**
     * Gets the number of elements contained in this {@link ICollection}.
     * @return The number of elements contained in this {@link ICollection}.
     * */
    int count();
}
