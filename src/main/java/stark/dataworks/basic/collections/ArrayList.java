package stark.dataworks.basic.collections;

import stark.dataworks.basic.ArgumentOutOfRangeException;
import stark.dataworks.basic.IComparer;

import java.util.Iterator;

public class ArrayList implements IList, Cloneable
{
    /**
     * This class wraps an IList, exposing it as a {@link ArrayList}.
     * Note this requires re-implementing half of {@link ArrayList}.
     */
    private static class IListWrapper extends ArrayList
    {
        public IListWrapper(IList list)
        {

        }
    }

    private final int DEFAULT_CAPACITY = 4;

    private Object[] items;

    private int count;
    private int version;
    private static final Object[] emptyArray = new Object[0];

    /**
     * Constructs a {@link ArrayList}. The list is initially empty and has a capacity of 0. Upon adding the first element to the
     * capacity is increased to DEFAULT_CAPACITY, and then increase in multiples of 2 as required.
     */
    ArrayList()
    {
        items = emptyArray;
    }

    /**
     * Constructs a {@link ArrayList} with a given initial capacity. The list is initially empty, but wll have room for the
     * given number of elements before any re-allocations are required.
     *
     * @param initialCapacity Initial capacity of the {@link ArrayList}.
     */
    public ArrayList(int initialCapacity)
    {
        if (initialCapacity < 0)
            throw new ArgumentOutOfRangeException("\"initialCapacity\" must be a non-negative integer.");

        if (initialCapacity == 0)
            items = emptyArray;
        else
            items = new Object[initialCapacity];
    }

    /**
     * Constructs a {@link ArrayList}, copying the contents of the given collection. The size and capacity of the new
     * list will both be equal to the size of the given collection.
     *
     * @param c The collection that contains the elements to copy.
     */
    public ArrayList(ICollection c)
    {
        if (c == null)
            throw new NullPointerException("\"c\" cannot be null.");

        int count = c.count();
        if (count == 0)
            items = emptyArray;
        else
        {
            items = new Object[count];
            addRange(c);
        }
    }

    /**
     * Gets the capacity of this {@link ArrayList}. The capacity is the size of the internal array used to hold items.
     *
     * @return The capacity of this {@link ArrayList}.
     */
    public int getCapacity()
    {
        return items.length;
    }

    /**
     * Sets the capacity of this {@link ArrayList}. The capacity is the size of the internal array used to hold items.
     * When set, the internal array of the {@link ArrayList} is re-allocated to the given capacity.
     *
     * @param newCapacity New capacity of the {@link ArrayList}.
     * @throws ArgumentOutOfRangeException If the given new capacity is less than the number of elements in the
     *                                     {@link ArrayList}.
     */
    public void setCapacity(int newCapacity)
    {
        if (newCapacity < count)
            throw new ArgumentOutOfRangeException("The given new capacity is less than the number of elements in the ArrayList.");

        // We don't want to update the version number when we change the capacity.
        // Some existing applications have dependency on this.
        if (newCapacity != items.length)
        {
            if (newCapacity > 0)
            {
                Object[] newItems = new Object[newCapacity];
                if (count > 0)
                    System.arraycopy(items, 0, newItems, 0, count);
                items = newItems;
            }
            else
                items = new Object[DEFAULT_CAPACITY];
        }
    }


    /**
     * Gets the element at the given index.
     *
     * @param index The index of the element to get.
     * @return Object at the index.
     * @throws ArgumentOutOfRangeException If the given index is out of range [0, count - 1].
     */
    public Object get(int index)
    {
        if ((index < 0) || (index > count))
            throw new ArgumentOutOfRangeException("index (with value " + index + ") out of range.");
        return items[index];
    }

    /**
     * Sets the element at the given index.
     *
     * @param index The index of the element to get.
     * @param value The new value at the given index.
     * @throws ArgumentOutOfRangeException If the given index is out of range [0, count - 1].
     */
    @Override
    public void set(int index, Object value)
    {
        if ((index < 0) || (index > count))
            throw new ArgumentOutOfRangeException("index (with value " + index + ") out of range.");
        items[index] = value;
        version++;
    }

    /**
     * Creates a {@link ArrayList} wrapper for a particular IList. This does not copy the contents of the IList, but
     * only wraps the ILists. So any changes to the underlying list will after the {@link ArrayList}. This would be
     * useful if you want to reverse a sub-range of an IList, or want to use a generic binarySearch() or sort() method
     * without implementing one yourself. However, since these methods are generic, the performance may not be nearly as
     * good as for some operations as they would be on the IList itself.
     *
     * @param list
     * @return
     */
    public static ArrayList adapter(IList list)
    {
        if (list == null)
            throw new NullPointerException("list is null.");
        return new IListWrapper(list);
    }

    private void ensureCapacity(int min)
    {
        if (items.length < min)
        {
            int newCapacity = items.length == 0 ? DEFAULT_CAPACITY : items.length * 2;

            // Allow the list to grow to maximum possible capacity (~2G elements) before encountering overflow.
            if (newCapacity > ArrayHelper.MAX_ARRAY_LENGTH)
                newCapacity = ArrayHelper.MAX_ARRAY_LENGTH;
            if (newCapacity < min)
                newCapacity = min;
            setCapacity(newCapacity);
        }
    }

    /**
     * Adds the given object to the end of this {@link ArrayList}. The size of the list is increased by 1. If required,
     * the capacity of the {@link ArrayList} is doubled before adding the new element.
     *
     * @param value The object to add.
     * @return The index at which the value has been added.
     */
    public int add(Object value)
    {
        if (count == items.length)
            ensureCapacity(count + 1);

        items[count] = value;
        version++;
        return count++;
    }

    /**
     * Inserts the elements of the given collection at a given index. If required, the capacity of the list is increased
     * to twice the previous capacity or the new size, whichever is larger. Ranges may be added to the end of the
     * {@link ArrayList} by setting the index to the {@link ArrayList}'s size.
     *
     * @param startIndex
     * @param c
     */
    public void insertRange(int startIndex, ICollection c)
    {
        if (c == null)
            throw new NullPointerException("c is null.");

        if ((startIndex < 0) || (startIndex > this.count))
            throw new ArgumentOutOfRangeException("startIndex (with value " + startIndex + ") out of range.");

        int count = c.count();
        if (count > 0)
        {
            ensureCapacity(this.count + count);

            // Shifting existing items (moving existing elements from [startIndex, this.count-1] to
            // [startIndex+count, startIndex+count+this.count-1]).
            if (startIndex < count)
                ArrayHelper.copy(items, startIndex, items, startIndex + count, count - startIndex);

            ArrayHelper.copy(c, items, startIndex, count);

            this.count += count;
            version++;
        }
    }

    /**
     * Adds the elements of the given collection to the end of this {@link ArrayList}. If required, the capacity of the
     * {@link ArrayList} is increased to twice the previous capacity or the new size, whichever is larger.
     *
     * @param c The {@link ICollection} whose elements should be added to the end
     *          of the System.Collections.ArrayList. The collection itself cannot be null, but
     *          it can contain elements that are null.
     * @throws NullPointerException If c is null.
     */
    public void addRange(ICollection c)
    {
        insertRange(count, c);
    }

    /**
     * Searches a section of the {@link ArrayList} for a given element using a binary search algorithm. Elements of the
     * {@link ArrayList} are compared to search value using the given {@link IComparer} interface. If comparer is null,
     * elements of the {@link ArrayList} are compared to the search value using the {@link Comparable} interface, which
     * in that case must be implemented by all elements of the {@link ArrayList} and the given search value. This
     * method assumes that the given section of the list is already sorted; if this is not the case, the result will be
     * incorrect.
     * <p/>
     * The method returns the index of hte given value in the {@link ArrayList}. If the {@link ArrayList} does not
     * contain the given value, the method returns a negative integer. The bitwise complement operator (~) can be
     * applied to a negative result to produce the index of the first element (if any) that is larger than the given
     * search value. This is also the index at which the search value should be inserted into the {@link ArrayList} in
     * order for the {@link ArrayList} to remain sorted.
     * <p/>
     * This method uses the {@link ArrayHelper#binarySearch} method to perform the search.
     *
     * @param startIndex
     * @param count
     * @param value
     * @param comparer
     * @return
     */
    public int binarySearch(int startIndex, int count, Object value, IComparer comparer)
    {
        return 0;
    }

    @Override
    public int indexOf(Object value)
    {
        return 0;
    }

    /**
     * Removes all items from this {@link ICollection}.
     */
    @Override
    public void clear()
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
    public boolean remove(Object value)
    {
        return false;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator()
    {
        return null;
    }

    /**
     * Gets the number of elements contained in this {@link ICollection}.
     *
     * @return The number of elements contained in this {@link ICollection}.
     */
    @Override
    public int count()
    {
        return count;
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
     * Determines whether this {@link ICollection} contains a specific value.
     *
     * @param value The value to locate in this {@link ICollection}.
     * @return <code>true</code> if the specified value is found in this {@link ICollection}; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean contains(Object value)
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
    public void copyTo(Object[] array)
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
    public void copyTo(Object[] array, int startIndex)
    {

    }
}
