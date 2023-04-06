package stark.dataworks.basic.collections;

import java.io.Serializable;
import java.util.Iterator;

public class SortedLinkedList<T extends Comparable<T>> implements ICollection<T>, Serializable
{
    private LinkedList<T> linkedList;

    public SortedLinkedList()
    {
        linkedList = new LinkedList<>();
    }

    public SortedLinkedList(Iterable<T> collection)
    {
        this();

        if (collection == null)
            throw new NullPointerException("Argument \"collection\" cannot be null.");

        for (T value : collection)
            add(value);
    }

    public SortedLinkedList(T[] collection)
    {
        this();

        if (collection == null)
            throw new NullPointerException("Argument \"collection\" cannot be null.");

        for (T value : collection)
            add(value);
    }

    public void add(T value)
    {
        if (value == null)
            throw new NullPointerException("Argument \"value\" is null.");

        if (value.compareTo(linkedList.getFirst().getValue()) < 0)
            linkedList.addFirst(value);
        else if (value.compareTo(linkedList.getLast().getValue()) > 0)
            linkedList.addLast(value);
        else
        {
            LinkedListNode<T> current = linkedList.getFirst();
            for (; ; )
            {
                LinkedListNode<T> next = current.next;
                if (value.compareTo(next.getValue()) < 0)
                {
                    linkedList.addAfter(current, value);
                    break;
                }
            }
        }
    }

    public T getMin()
    {
        LinkedListNode<T> first = linkedList.getFirst();
        return first == null ? null : first.getValue();
    }

    public T getMax()
    {
        LinkedListNode<T> last = linkedList.getLast();
        return last == null ? null : last.getValue();
    }

    /**
     * Gets the number of elements in the collection.
     *
     * @return The number of elements in the collection.
     */
    @Override
    public int count()
    {
        return linkedList.count();
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
        linkedList.clear();
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
        return linkedList.remove(value);
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
        return linkedList.contains(value);
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
        ArrayHelper.copy(this, array, 0, linkedList.count());
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
        ArrayHelper.copy(this, array, startIndex, linkedList.count());
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    @Override
    public Iterator<T> iterator()
    {
        return linkedList.iterator();
    }

    public Iterable<T> reverse()
    {
        return linkedList.reverse();
    }
}
