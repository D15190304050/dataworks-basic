package stark.dataworks.basic.collections;

import stark.dataworks.basic.DefaultComparer;
import stark.dataworks.basic.InvalidOperationException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class QueueStackBase<T> implements ICollection<T>
{
    protected class Node
    {
        public T value;
        public Node next;

        public Node(T value)
        {
            this.value = value;
            this.next = null;
        }

        public void invalidate()
        {
            this.value = null;
            this.next = null;
        }
    }

    protected Node first;
    protected Node last;
    protected int count;
    protected int version;
    protected DefaultComparer<T> comparator;


    protected QueueStackBase()
    {
        first = null;
        count = 0;
        version = 0;
        comparator = new DefaultComparer<>();
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
     * Removes all items from this {@link ICollection}.
     */
    @Override
    public void clear()
    {
        Node current = first;
        while (current != null)
        {
            Node nodeToInvalidate = current;
            current = current.next;
            nodeToInvalidate.invalidate();
        }

        count = 0;
        version++;
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
        for (Node current = first; current != null; current = current.next)
        {
            if (comparator.compare(current.value, value) == 0)
                return true;
        }

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
        copyTo(array, 0);
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
        if (array == null)
            throw new NullPointerException("Argument \"array\" cannot be null.");
        if (startIndex < 0)
            throw new ArrayIndexOutOfBoundsException("The index of an array cannot be less than 0.");
        if ((startIndex > array.length) || (startIndex + count >= array.length))
            throw new ArrayIndexOutOfBoundsException("Not enough capacity for copying items from the Queue to" +
                    " the given array.");

        for (Node current = first; current != null; current = current.next)
            array[startIndex++] = current.value;
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
        if (first == null)
            return false;

        if (comparator.compare(first.value, value) == 0)
        {
            Node oldFirst = first;
            first = first.next;
            oldFirst.invalidate();

            count--;
            version++;
            return true;
        }

        for (Node current = first; current.next != null; current = current.next)
        {
            if (comparator.compare(current.next.value, value) == 0)
            {
                Node nodeToRemove = current.next;
                current.next = current.next.next;
                nodeToRemove.invalidate();

                count--;
                version++;
                return true;
            }
        }

        return false;
    }

    private class QueueStackIterator implements Iterator<T>
    {
        private Node current;
        private int version;
        private QueueStackBase<T> queueStack;

        public QueueStackIterator(QueueStackBase<T> queueStack)
        {
            this.current = queueStack.first;
            this.version = queueStack.version;
            this.queueStack = queueStack;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext()
        {
            return current != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next()
        {
            if (version != queueStack.version)
                throw new InvalidOperationException("ICollection object is not allowed to be modified during iterating through it.");

            T value = current.value;
            current = current.next;

            return value;
        }
    }

    public Iterator<T> iterator()
    {
        return new QueueStackIterator(this);
    }
}
