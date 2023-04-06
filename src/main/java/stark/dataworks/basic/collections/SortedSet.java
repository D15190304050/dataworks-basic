package stark.dataworks.basic.collections;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

public class SortedSet<T> implements ISet<T>, Serializable
{
    private Comparator<T> comparator;

    /**
     * Gets the maximum value in the {@link SortedSet}, as defined by the comparator.
     * @return The maximum value in the set.
     * */
    public T max()
    {
        return null;
    }

    /**
     * Gets the minimum value in the {@link SortedSet}, as defined by the comparator.
     * @return The minimum value in the set.
     * */
    public T min()
    {
        return null;
    }

    /**
     * Gets the {@link Comparator} object that is used to order the values in the {@link SortedSet}.
     * @return The comparator that is used to order the values in the {@link SortedSet}.
     * */
    public Comparator<T> getComparator()
    {
        return comparator;
    }

    /**
     * Adds an element to the current set and returns a value to indicate if the element was successfully added.
     *
     * @param item The element to add to the set.
     * @return {@code true} if the element is added to the set; {@code false} if the element is already in the set.
     */
    @Override
    public boolean add(T item)
    {
        return false;
    }

    /**
     * Modifies the current set so that it contains all elements that are present in the current set, in the specified
     * collection, or in both.
     *
     * @param other The collection of items to remove from the set.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public void unionWith(Iterable<T> other)
    {

    }

    /**
     * Modifies the current set so that it contains only elements that are also in a specified collection.
     *
     * @param other The collection to compare to the current set.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public void intersectWith(Iterable<T> other)
    {

    }

    /**
     * Removes all elements in the specified collection from the current set.
     *
     * @param other The collection of items to remove from the set.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public void exceptWith(Iterable<T> other)
    {

    }

    /**
     * Modifies the current set so that it contains only elements that are present either in the current set or in the
     * specified collection, but not both.
     *
     * @param other The collection to compare to the current set.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public void symmetricExceptWith(Iterable<T> other)
    {

    }

    /**
     * Determines whether a set is a subset of a specified collection.
     *
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is a proper subset of other; otherwise, {@code false}.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public boolean isSubsetOf(Iterable<T> other)
    {
        return false;
    }

    /**
     * Determines whether a set is a superset of a specified collection.
     *
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is a proper superset of other; otherwise, {@code false}.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public boolean isSupersetOf(Iterable<T> other)
    {
        return false;
    }

    /**
     * Determines whether the current set is a proper (strict) superset of a specified collection.
     *
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is a proper superset of other; otherwise, {@code false}.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public boolean isProperSupersetOf(Iterable<T> other)
    {
        return false;
    }

    /**
     * Determines whether the current set is a proper (strict) subset of a specified collection.
     *
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is a proper subset of other; otherwise, {@code false}.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public boolean isProperSubsetOf(Iterable<T> other)
    {
        return false;
    }

    /**
     * Determines whether the current set overlaps with the specified collection.
     *
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set and other share at least one common element; otherwise, {@code false}.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public boolean overlaps(Iterable<T> other)
    {
        return false;
    }

    /**
     * Determines whether the current set and the specified collection contain the same elements.
     *
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is equal to other; otherwise, {@code false}.
     * @throws NullPointerException {@code other} is null.
     */
    @Override
    public boolean setEquals(Iterable<T> other)
    {
        return false;
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

    /**
     * Returns an {@link Iterable} that iterates over the {@link SortedSet} in reverse order.
     * @return An {@link Iterable} that iterates over the {@link SortedSet} in reverse order.
     * */
    public Iterable<T> reverse()
    {
        return null;
    }

    /**
     * Returns a view of a subset in a {@link SortedSet}.
     * @param lowerValue The lowest desired value in the view (inclusive).
     * @param upperValue The highest desired value in the view (inclusive).
     * @return A subset view that contains only the values in the specified range.
     * @exception IllegalArgumentException {@code lowerValue} is more than {@code upperValue} according to the comparator.
     * @exception dataworks.ArgumentOutOfRangeException A tried operation on the view was outside the range specified by lowerValue and upperValue.
     * */
    public SortedSet<T> getViewBetween(T lowerValue, T upperValue)
    {
        return null;
    }
}
