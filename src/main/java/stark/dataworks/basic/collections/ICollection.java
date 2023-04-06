package stark.dataworks.basic.collections;

/**
 * The {@link ICollection} interface defines methods to manipulate generic collections.
 * @param <T> The type of the elements in the collection.
 * */
public interface ICollection<T> extends Iterable<T>
{
    /**
     * Gets the number of elements in the collection.
     * @return The number of elements in the collection.
     */
    int count();

    /**
     * Returns {@code true} if the collection is read only; otherwise, {@code false}.
     * @return {@code true} if the collection is read only; otherwise, {@code false}.
     */
    boolean isReadOnly();

    /**
     * Removes all items from this {@link ICollection}.
     * */
    void clear();

    /**
     * Removes the first occurrence of a specific object from this {@link ICollection}.
     * @param value The object to remove from this {@link ICollection}.
     * @return <code>true</code> if item was successfully removed from the {@link ICollection}; otherwise,
     * <code>false</code>. This method also returns <code>false</code> if item is not found in the original
     * {@link ICollection}.
     * */
    boolean remove(T value);

    /**
     * Determines whether this {@link ICollection} contains a specific value.
     * @param value The value to locate in this {@link ICollection}.
     * @return <code>true</code> if the specified value is found in this {@link ICollection}; otherwise,
     * <code>false</code>.
     * */
    boolean contains(T value);

    /**
     * Copies the elements of this {@link ICollection} to an array, starting at index 0.
     * @param array The one-dimensional array that is the destination of the elements copied from this
     * {@link ICollection}. The array must have zero-based indexing.
     * @exception NullPointerException The given array is null.
     * @exception ArrayIndexOutOfBoundsException Array index is greater than or equal to the length of the array.
     * @exception IllegalArgumentException The number of elements in the source {@link ICollection} is greater than the
     * available space from 0 to the end of the destination array, i.e. the capacity of the given array.
     * */
    void copyTo(T[] array);

    /**
     * Copies the elements of this {@link ICollection} to an array, starting at a particular array index.
     * @param array The one-dimensional array that is the destination of the elements copied from this
     * {@link ICollection}. The array must have zero-based indexing.
     * @param startIndex The zero-based index in array at which copying begins.
     * @exception NullPointerException The given array is null.
     * @exception ArrayIndexOutOfBoundsException Array index is less than 0 or greater than or equal to the length of
     * the array.
     * @exception IllegalArgumentException The number of elements in the source {@link ICollection} is greater than the
     * available space from <code>startIndex</code> to the end of the destination array.
     * */
    void copyTo(T[] array, int startIndex);
}
