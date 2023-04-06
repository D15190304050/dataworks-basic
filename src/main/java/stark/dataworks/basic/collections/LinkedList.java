package stark.dataworks.basic.collections;

import stark.dataworks.basic.DefaultComparer;
import stark.dataworks.basic.IEqualityComparer;
import stark.dataworks.basic.InvalidOperationException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * The {@link LinkedList} class represents a doubly linked list.
 *
 * @param <T> Specifies the element type of the linked list.
 */
public class LinkedList<T> implements ICollection<T>, Serializable
{
    private LinkedListNode<T> first;
    private int count;
    private int version;

    /**
     * Initializes a new instance of the {@link LinkedList} class that is empty.
     */
    public LinkedList()
    {
        clear();
    }

    /**
     * Initializes a new instance of the {@link LinkedList} class that contains elements copied the specified
     * {@link Iterable} and has sufficient capacity to accommodate the number of elements copied.
     */

    public LinkedList(Iterable<T> collection)
    {
        if (collection == null)
            throw new NullPointerException("Argument \"collection\" cannot be null.");

        clear();
        addAll(collection);
    }

    /**
     * Initializes a new instance of the {@link LinkedList} class that contains elements copied the specified
     * array and has sufficient capacity to accommodate the number of elements copied.
     */

    public LinkedList(T[] collection)
    {
        if (collection == null)
            throw new NullPointerException("Argument \"collection\" cannot be null.");

        clear();

        for (T item : collection)
            addLast(item);
    }

    @Override
    public int count()
    {
        return count;
    }

    /**
     * Gets the first node of this {@link LinkedList}.
     *
     * @return the first node of this {@link LinkedList}.
     */
    public LinkedListNode<T> getFirst()
    {
        return first;
    }

    /**
     * Gets the last node of this {@link LinkedList}.
     *
     * @return the last node of this {@link LinkedList}.
     */
    public LinkedListNode<T> getLast()
    {
        return first == null ? null : first.previous;
    }

    /**
     * Adds a new node containing the specified value after the specified existing node in this {@link LinkedList}.
     *
     * @param node  The {@link LinkedListNode} after which to insert a new {@link LinkedListNode} containing value.
     * @param value The value to add to this {@link LinkedList}.
     * @return The new {@link LinkedListNode} containing the specified value.
     * @throws NullPointerException     One of the given nodes is null.
     * @throws IllegalArgumentException One of the given nodes is not in this {@link LinkedList}.
     */
    public LinkedListNode<T> addAfter(LinkedListNode<T> node, T value)
    {
        validateNode(node);
        LinkedListNode<T> newNode = new LinkedListNode<>(this, value);
        internalInsertNodeBefore(node.next, newNode);
        return newNode;
    }

    /**
     * Adds a new node containing the specified value after the specified existing node in this {@link LinkedList}.
     *
     * @param node    The {@link LinkedListNode} after which to insert a new {@link LinkedListNode} containing value.
     * @param newNode The new {@link LinkedListNode} to add to this {@link LinkedList}.
     * @throws NullPointerException     The given node is null.
     * @throws IllegalArgumentException The given node is not in this {@link LinkedList}.
     */
    public void addAfter(LinkedListNode<T> node, LinkedListNode<T> newNode)
    {
        validateNode(node);
        validateNewNode(newNode);
        internalInsertNodeBefore(node.next, newNode);
        newNode.list = this;
    }

    /**
     * Adds a new node containing the specified value before the specified existing node in this {@link LinkedList}.
     *
     * @param node  The {@link LinkedListNode} before which to insert a new {@link LinkedListNode} containing value.
     * @param value The value to add to this {@link LinkedList}.
     * @return The new {@link LinkedListNode} containing the specified value.
     * @throws NullPointerException     The given node is null.
     * @throws IllegalArgumentException The given node is not in this {@link LinkedList}.
     */
    public LinkedListNode<T> addBefore(LinkedListNode<T> node, T value)
    {
        validateNode(node);
        LinkedListNode<T> newNode = new LinkedListNode<>(this, value);
        internalInsertNodeBefore(node, newNode);
        if (node == first)
            first = newNode;
        return newNode;
    }

    /**
     * Adds a new node containing the specified value before the specified existing node in this {@link LinkedList}.
     *
     * @param node    The {@link LinkedListNode} before which to insert a new {@link LinkedListNode} containing value.
     * @param newNode The new {@link LinkedListNode} to add to this {@link LinkedList}.
     * @throws NullPointerException     One of the given nodes is null.
     * @throws IllegalArgumentException One of the given nodes is not in this {@link LinkedList}.
     */
    public void addBefore(LinkedListNode<T> node, LinkedListNode<T> newNode)
    {
        validateNode(node);
        validateNewNode(newNode);
        internalInsertNodeBefore(node, newNode);
        node.list = this;
        if (node == first)
            first = newNode;
    }

    /**
     * Adds a new node containing the specified value at the start of this {@link LinkedList}.
     *
     * @param value The value to add at the start of this {@link LinkedList}.
     * @return The new {@link LinkedListNode} containing the specified value.
     */
    public LinkedListNode<T> addFirst(T value)
    {
        LinkedListNode<T> newNode = new LinkedListNode<>(this, value);
        if (first == null)
            internalInsertNodeToEmptyList(newNode);
        else
        {
            internalInsertNodeBefore(first, newNode);
            first = newNode;
        }

        return newNode;
    }

    /**
     * Adds the specified new node at the start of this {@link LinkedList}.
     *
     * @param node The new {@link LinkedListNode} to add at the start of this {@link LinkedList}.
     * @throws NullPointerException     The given node is null.
     * @throws IllegalArgumentException The given node is not in this {@link LinkedList}.
     */
    public void addFirst(LinkedListNode<T> node)
    {
        validateNewNode(node);
        if (first == null)
            internalInsertNodeToEmptyList(node);
        else
        {
            internalInsertNodeBefore(first, node);
            first = node;
        }

        node.list = this;
    }

    /**
     * Adds a new node containing the specified value at the end of this {@link LinkedList}.
     *
     * @param value The value to add at the end of this {@link LinkedList}.
     * @return The new {@link LinkedListNode} containing the specified value.
     */
    public LinkedListNode<T> addLast(T value)
    {
        LinkedListNode<T> newNode = new LinkedListNode<>(this, value);
        if (first == null)
            internalInsertNodeToEmptyList(newNode);
        else
            internalInsertNodeBefore(first, newNode);

        return newNode;
    }

    /**
     * Adds the specified new node at the end of this {@link LinkedList}.
     *
     * @param node The new {@link LinkedListNode} to add at the end of this {@link LinkedList}.
     * @throws NullPointerException     The given node is null.
     * @throws IllegalArgumentException The given node is not in this {@link LinkedList}.
     */
    public void addLast(LinkedListNode<T> node)
    {
        validateNewNode(node);

        if (first == null)
            internalInsertNodeToEmptyList(node);
        else
            internalInsertNodeBefore(first, node);

        node.list = this;
    }

    public void addAll(Iterable<T> collection)
    {
        Objects.requireNonNull(collection, "Argument \"collection\" cannot be null.");
        for (T item : collection)
            addLast(item);
    }

    public void addAll(T[] array)
    {
        Objects.requireNonNull(array, "Argument \"array\" cannot be null.");
        for (T item : array)
            addLast(item);
    }

    /**
     * Finds the first node that contains the specified value.
     *
     * @param value The value to contained in the {@link LinkedListNode}.
     * @return The first {@link LinkedListNode} that contains the specified value, if found; otherwise, null.
     */
    public LinkedListNode<T> find(T value)
    {
        if (first != null)
        {
            LinkedListNode<T> node = first;
            IEqualityComparer<T> c = new DefaultComparer<>();
            if (value != null)
            {
                do
                {
                    if (c.equals(node.getValue(), value))
                        return node;
                    node = node.next;
                }
                while (node != first);
            }
            else
            {
                do
                {
                    if (node.getValue() == null)
                        return node;
                    node = node.next;
                }
                while (node != first);
            }
        }

        return null;
    }

    public Iterable<T> reverse()
    {
        LinkedList<T> linkedList = this;
        return new Iterable<T>()
        {

            @Override
            public Iterator<T> iterator()
            {
                return new ReverseListIterator(linkedList);
            }
        };
    }

    /**
     * Finds the last node that contains the specified value.
     *
     * @param value The value to contained in the {@link LinkedListNode}.
     * @return The last {@link LinkedListNode} that contains the specified value, if found; otherwise, null.
     */
    public LinkedListNode<T> findLast(T value)
    {
        if (first != null)
        {
            LinkedListNode<T> last = first.previous;
            LinkedListNode<T> current = last;
            IEqualityComparer c = new DefaultComparer<T>();
            if (current != null)
            {
                if (value != null)
                {
                    do
                    {
                        if (c.equals(current.getValue(), value))
                            return current;
                        current = current.previous;
                    }
                    while (current != last);
                }
                else
                {
                    do
                    {
                        if (current.getValue() == null)
                            return current;
                        current = current.previous;
                    }
                    while (current != last);
                }
            }
        }

        return null;
    }

    /**
     * Removes all nodes from this {@link LinkedList}.
     */
    @Override
    public void clear()
    {
        LinkedListNode<T> current = first;
        while (current != null)
        {
            LinkedListNode<T> nodeToInvalidate = current;
            current = current.next;
            nodeToInvalidate.invalidate();
        }

        first = null;
        count = 0;
        version++;
    }

    /**
     * Determines whether a value is in this {@link LinkedList}. This method uses Object.equals() method to test
     * whether the given value equals one of the value contained in this {@link LinkedList}.
     *
     * @param value The value to locate in this {@link LinkedList}. The value can be null.
     * @return <code>true</code> if value is found in this {@link LinkedList}; otherwise, <code>false</code>.
     */
    @Override
    public boolean contains(T value)
    {
        return find(value) != null;
    }

    /**
     * Removes the first occurrence of the specified value from this {@link LinkedList}.
     *
     * @param value The value to remove from this {@link LinkedList}.
     * @return <code>true</code> if the element containing value is successfully removed; otherwise, false. This method
     * also returns false if value was not found in this {@link LinkedList}.
     */
    @Override
    public boolean remove(T value)
    {
        LinkedListNode<T> node = find(value);
        if (node != null)
        {
            internalRemoveNode(node);
            return true;
        }

        return false;
    }

    /**
     * Removes the specified node from this {@link LinkedList}.
     *
     * @param node The {@link LinkedListNode} to remove from this {@link LinkedList}.
     * @throws NullPointerException     The given node is null.
     * @throws IllegalArgumentException The given node is not in this {@link LinkedList}.
     */
    public void remove(LinkedListNode<T> node)
    {
        validateNode(node);
        internalRemoveNode(node);
    }

    /**
     * Removes the node at the start of this {@link LinkedList}.
     *
     * @throws InvalidOperationException This {@link LinkedList} is empty when this method is called.
     */
    public void removeFirst()
    {
        if (first == null)
            throw new InvalidOperationException("Cannot call removeLast() on an empty LinkedList.");
        internalRemoveNode(first);
    }

    /**
     * Removes the node at the end of this {@link LinkedList}.
     *
     * @throws InvalidOperationException This {@link LinkedList} is empty when this method is called.
     */
    public void removeLast()
    {
        if (first == null)
            throw new InvalidOperationException("Cannot call removeLast() on an empty LinkedList.");

        internalRemoveNode(first.previous);
    }

    private void validateNode(LinkedListNode<T> node)
    {
        if (node == null)
            throw new NullPointerException("LinkedListNode<T> cannot be null.");
        if (node.list != this)
            throw new IllegalArgumentException("The LinkedListNode<T> does not belong to the LinkedList<T>.");
    }

    private void validateNewNode(LinkedListNode<T> node)
    {
        if (node == null)
            throw new NullPointerException("LinkedListNode<T> cannot be null.");
        if (node.list != null)
            throw new IllegalArgumentException("The LinkedListNode<T> does not belong to the LinkedList<T>.");
    }

    /**
     * Inserts a new {@link LinkedListNode} to this {@link LinkedList} when this {@link LinkedList} is empty.
     *
     * @param newNode The new {@link LinkedListNode} to add to this {@link LinkedList}.
     */
    private void internalInsertNodeToEmptyList(LinkedListNode<T> newNode)
    {
        assert ((first == null) && (count == 0));
        newNode.next = newNode;
        newNode.previous = newNode;
        first = newNode;
        count++;
        version++;
    }

    /**
     * Adds a new node containing the specified value before the specified existing node in this {@link LinkedList}.
     *
     * @param node    The {@link LinkedListNode} before which to insert a new {@link LinkedListNode} containing value.
     * @param newNode The new {@link LinkedListNode} to add to this {@link LinkedList}.
     */
    private void internalInsertNodeBefore(LinkedListNode<T> node, LinkedListNode<T> newNode)
    {
        newNode.next = node;
        newNode.previous = node.previous;
        node.previous.next = newNode;
        node.previous = newNode;
        count++;
        version++;
    }

    /**
     * Removes the specified node from this {@link LinkedList}.
     *
     * @param node The {@link LinkedListNode} to remove from this {@link LinkedList}.
     * @throws IllegalArgumentException The specified {@link LinkedListNode} is not in this {@link LinkedList}.
     */
    private void internalRemoveNode(LinkedListNode<T> node)
    {
        assert (node.list == this);
        assert (first != null);

        if (first.next == first)
        {
            assert ((count == 1) && (first == node));
            first = null;
        }
        else
        {
            node.next.previous = node.previous;
            node.previous.next = node.next;
            if (first == node)
                first = node.next;
        }

        node.invalidate();
        count--;
        version++;
    }

    /**
     * Returns an iterator over elements of type {@code T} in this {@link LinkedList}.
     *
     * @return An iterator over elements of type {@code T} in this {@link LinkedList}.
     */

    @Override
    public Iterator<T> iterator()
    {
        return new ListIterator(this);
    }

    /**
     * The {@link ListIterator} class represents a {@link Iterator} of the {@link LinkedList} class.
     */
    private class ListIterator implements Iterator<T>
    {
        private boolean started;

        /**
         * Current {@link LinkedListNode} in the iteration.
         */
        private LinkedListNode<T> current;

        /**
         * The {@link LinkedList} to iterator over.
         */
        private LinkedList<T> list;

        /**
         * The number of operations applied on the {@link LinkedList} to iterate over when instantiate this
         * {@link Iterator}.
         */
        private int version;

        /**
         * Initializes an {@link Iterator} for the specified {@link LinkedList}.
         *
         * @param list The specified {@link LinkedList}.
         */
        public ListIterator(LinkedList<T> list)
        {
            this.started = false;
            this.current = list.first;
            this.list = list;
            this.version = list.version;
        }

        /**
         * Returns a value indicating whether there is node in the {@link LinkedList} not iterate over.
         *
         * @return <code>true</code> if there is node in the {@link LinkedList} not iterator over; otherwise, false.
         */
        @Override
        public boolean hasNext()
        {
            if (!started)
                return true;
            return current != first;
        }

        /**
         * Gets next element in the {@link LinkedList}.
         *
         * @return ext element in the {@link LinkedList}.
         * @throws InvalidOperationException If some update operation is applied on the {@link LinkedList} during
         *                                   the iteration over the same {@link LinkedList}.
         */
        @Override
        public T next()
        {
            started = true;
            if (version != list.version)
                throw new InvalidOperationException("ICollection object is not allowed to be modified during iterating through it.");

            T value = current.getValue();
            current = current.next;
            return value;
        }
    }

    private class ReverseListIterator implements Iterator<T>
    {
        private boolean started;

        /**
         * Current {@link LinkedListNode} in the iteration.
         */
        private LinkedListNode<T> current;

        /**
         * The {@link LinkedList} to iterator over.
         */
        private LinkedList<T> list;

        /**
         * The number of operations applied on the {@link LinkedList} to iterate over when instantiate this
         * {@link Iterator}.
         */
        private int version;

        public ReverseListIterator(LinkedList<T> list)
        {
            this.started = false;
            this.current = list.first.previous;
            this.list = list;
            this.version = list.version;
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
            return (!started) || current != list.first.previous;
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
            started = true;
            if (version != list.version)
                throw new InvalidOperationException("ICollection object is not allowed to be modified during iterating through it.");

            T value = current.getValue();
            current = current.previous;
            return value;
        }
    }

    /**
     * Copies all elements in this {@link LinkedList} to a compatible one-dimensional array, starting at the index of 0.
     *
     * @param array The one-dimensional array that is the destination of the elements copied from this
     *              {@link LinkedList}. The array must have zero-based indexing.
     * @throws NullPointerException     The given array is null.
     * @throws IllegalArgumentException The number of elements in this {@link LinkedList} is greater than the
     *                                  vailable space from index to the end of the destination array.
     */
    public void copyTo(T[] array)
    {
        copyTo(array, 0);
    }

    /**
     * Copies all elements in this {@link LinkedList} to a compatible one-dimensional array, starting at the index of 0.
     *
     * @param array The one-dimensional array that is the destination of the elements copied from this
     *              {@link LinkedList}. The array must have zero-based indexing.
     * @throws NullPointerException      The given array is null.
     * @throws IndexOutOfBoundsException The given index is less than 0.
     * @throws IllegalArgumentException  The number of elements in this {@link LinkedList} is greater than the
     *                                   available space from index to the end of the destination array.
     */
    @Override
    public void copyTo(T[] array, int startIndex)
    {
        if (array == null)
            throw new NullPointerException("Argument \"array\" cannot be null.");
        if (startIndex < 0)
            throw new IndexOutOfBoundsException("The start index of an array must be greater than or equal to 0.");

        int copyLength = array.length - startIndex;
        if (copyLength < count)
            throw new IllegalArgumentException("The length between the start index and the end of the given array is not enough for copying contents.");

        int i = startIndex;
        for (T v : this)
            array[i++] = v;
    }

    /**
     * Returns {@code true} if the {@link LinkedList} is read only; otherwise, {@code false}.
     *
     * @return {@code true} if the {@link LinkedList} is read only; otherwise, {@code false}.
     */
    @Override
    public boolean isReadOnly()
    {
        return false;
    }

    /**
     * A unit test method for this class.
     */
    public static void main(String[] args)
    {
        String[] line = {"1", "2", "3", "4", "5"};
        LinkedList<String> list = new LinkedList<>();
        for (String s : line)
            list.addLast(s);
        System.out.println(list.count());

        for (String s : line)
            list.addFirst(s);
        System.out.println(list.count());

        for (String s : list)
            System.out.print(s + " ");
        System.out.println();

        String[] contents = new String[list.count()];
        list.copyTo(contents);

        for (String s : contents)
            System.out.print(s + " ");
        System.out.println();
        //        java.util.LinkedList
    }
}
