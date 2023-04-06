package stark.dataworks.basic.collections;

import stark.dataworks.basic.ArgumentOutOfRangeException;

import java.io.Serializable;
import java.util.Iterator;

public class List<T> implements IList<T>, Serializable
{
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    private T[] collection;
    private int count;
    private int version;

    public List()
    {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public List(int initialCapacity)
    {
        if (initialCapacity < 0)
            throw new ArgumentOutOfRangeException("Initial capacity of a List cannot be less than 0.");

        collection = (T[]) new Object[initialCapacity];
        count = 0;
        version = 0;
    }

    public List(Iterable<T> collection)
    {
        if (collection == null)
            throw new NullPointerException("Argument \"collection\" cannot be null.");

        if (collection instanceof IReadOnlyCollection)
        {

        }
    }

    @Override
    public T get(int index)
    {
        if ((index < 0) || (index > count))
            throw new IndexOutOfBoundsException("Argument \"index\" is out of bounds.");

        return collection[index];
    }

    @Override
    public void set(int index, T value)
    {

    }

    @Override
    public int indexOf(T value)
    {
        return 0;
    }

    /**
     * Gets the number of elements contained in this {@link ICollection}.
     *
     * @return The number of elements contained in this {@link ICollection}.
     */
    @Override
    public int count()
    {
        return 0;
    }

    /**
     * Returns {@code true} if the collection is read only; otherwise, {@code false}.
     *
     * @return {@code true} if the collection is read only; otherwise, {@code false}.
     */
    @Override
    public boolean isReadOnly()
    {
        return false;
    }

    /**
     * Removes all items from this {@link ICollection}.
     */
    @Override
    public void clear()
    {

    }

    /**
     * Determines whether this {@link ICollection} contains a specific value.
     *
     * @param value The value to locate in this {@link ICollection}.
     * @return <code>true</code> if the specified value is found in this {@link ICollection}; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean contains(T value)
    {
        return false;
    }

    /**
     * Copies the elements of this {@link ICollection} to an array, starting at index 0.
     *
     * @param array The one-dimensional array that is the destination of the elements copied from this
     *              {@link ICollection}. The array must have zero-based indexing.
     * @throws NullPointerException           The given array is null.
     * @throws ArrayIndexOutOfBoundsException Array index is greater than or equal to the length of the array.
     * @throws IllegalArgumentException       The number of elements in the source {@link ICollection} is greater than the
     *                                        available space from 0 to the end of the destination array, i.e. the capacity of the given array.
     */
    @Override
    public void copyTo(T[] array)
    {

    }

    /**
     * Copies the elements of this {@link ICollection} to an array, starting at a particular array index.
     *
     * @param array      The one-dimensional array that is the destination of the elements copied from this
     *                   {@link ICollection}. The array must have zero-based indexing.
     * @param startIndex The zero-based index in array at which copying begins.
     * @throws NullPointerException           The given array is null.
     * @throws ArrayIndexOutOfBoundsException Array index is less than 0 or greater than or equal to the length of
     *                                        the array.
     * @throws IllegalArgumentException       The number of elements in the source {@link ICollection} is greater than the
     *                                        available space from <code>startIndex</code> to the end of the destination array.
     */
    @Override
    public void copyTo(T[] array, int startIndex)
    {

    }

    /**
     * Removes the first occurrence of a specific object from this {@link ICollection}.
     *
     * @param value The object to remove from this {@link ICollection}.
     * @return <code>true</code> if item was successfully removed from the {@link ICollection}; otherwise,
     * <code>false</code>. This method also returns <code>false</code> if item is not found in the original
     * {@link ICollection}.
     */
    @Override
    public boolean remove(T value)
    {
        return false;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    @Override
    public Iterator<T> iterator()
    {
        return null;
    }
}
