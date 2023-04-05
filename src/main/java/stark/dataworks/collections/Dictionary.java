package stark.dataworks.collections;

import stark.dataworks.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Dictionary<TKey, TValue> implements IDictionary<TKey, TValue>, Serializable
{
    private class Node
    {
        public TKey key;
        public TValue value;
        public Node next;

        public Node(TKey key, TValue value, Node next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public void invalidate()
        {
            this.key = null;
            this.value = null;
            this.next = null;
        }
    }

    private int count;
    private Node first;
    private Node last;
    private final IEqualityComparer<TKey> keyComparer;
    private final IComparer<TValue> valueComparer;
    private int version;

    public Dictionary()
    {
        this(new DefaultComparer<>());
    }

    public Dictionary(IEqualityComparer<TKey> keyComparer)
    {
        count = 0;
        first = null;
        last = null;
        this.keyComparer = keyComparer;
        valueComparer = new DefaultComparer<>();
        version = 0;
    }

    public Dictionary(IDictionary<TKey, TValue> dictionary)
    {
        this(dictionary, new DefaultComparer<>());
    }

    public Dictionary(IDictionary<TKey, TValue> dictionary, IEqualityComparer<TKey> keyComparer)
    {
        count = 0;
        first = null;
        last = null;
        this.keyComparer = keyComparer;
        valueComparer = new DefaultComparer<>();
        version = 0;

        for (KeyValuePair<TKey, TValue> kvp : dictionary)
            add(kvp.getKey(), kvp.getValue());
    }

    /**
     * Gets the element associated with the specified key.
     *
     * @param key The TKey of the element to get.
     * @return The TValue associated with the specified TKey, null if this {@link Dictionary} doesn't contains the
     * specified key.
     * @throws NullPointerException The specified TKey is null.
     * @throws KeyNotFountException The specified TKey is not in the {@link IDictionary}.
     */
    @Override
    public TValue get(TKey key)
    {
        for (Node current = first; current != null; current = current.next)
        {
            if (keyComparer.compare(current.key, key) == 0)
                return current.value;
        }

        return null;
    }

    /**
     * Sets the element associated with the specified key. Do nothing if this {@link Dictionary} doesn't contains the
     * specified key.
     *
     * @param key   The key of the element to get.
     * @param value The new value associated with the specified TKey.
     * @throws NullPointerException The specified key is null.
     * @throws KeyNotFountException The specified key is not in the {@link IDictionary}.
     */
    @Override
    public void set(TKey key, TValue value)
    {
        for (Node current = first; current != null; current = current.next)
        {
            if (keyComparer.compare(current.key, key) == 0)
            {
                current.value = value;
                version++;
                return;
            }
        }
    }

    public IComparer<TKey> getKeyComparer()
    {
        return keyComparer;
    }

    /**
     * Gets an {@link Iterable} containing all the keys of the {@link IDictionary}.
     *
     * @return an {@link Iterable} containing all the keys of the {@link IDictionary}.
     */
    @Override
    public Iterable<TKey> keys()
    {
        return () -> new KeyIterator(this);
    }

    /**
     * Gets an {@link Iterable} containing all the values of the {@link IDictionary}.
     *
     * @return an {@link Iterable} containing all the values of the {@link IDictionary}.
     */
    @Override
    public Iterable<TValue> values()
    {
        return () -> new ValueIterator(this);
    }

    /**
     * Adds an element with the provided TKey and TValue to this {@link IDictionary}.
     *
     * @param key   The object to use as the TKey of the element to add.
     * @param value The object to use as the TValue of the element to add, which is associated with the TKey.
     * @throws NullPointerException The specified TKey is null.
     */
    @Override
    public void add(TKey key, TValue value)
    {
        validateKey(key);

        // Search for key. Update value if found; grow table if new.
        for (Node current = first; current != null; current = current.next)
        {
            // Search hit: update value.
            if (keyComparer.compare(current.key, key) == 0)
                current.value = value;
        }

        // Search miss: add new node, and update the node counter.
        first = new Node(key, value, first);
        count++;
        version++;

        // Save the reference of the last node.
        if (last == null)
            last = first;
    }

    /**
     * Adds an element with the provided key and value to this {@link IDictionary}.
     *
     * @param keyValuePair The object to add with the key and the value.
     * @throws NullPointerException     The specified key value pair is null.
     * @throws IllegalArgumentException An element with the same key already exists in this {@link IDictionary}.
     */
    @Override
    public void add(KeyValuePair<TKey, TValue> keyValuePair)
    {
        add(keyValuePair.getKey(), keyValuePair.getValue());
    }

    /**
     * Determines whether this {@link IDictionary} contains an element with the specified TKey.
     *
     * @param key The TKey to locate in this {@link IDictionary}.
     * @return <code>true</code> if this {@link IDictionary} contains an element with the TKey; otherwise,
     * <code>false</code>.
     * @throws NullPointerException The specified TKey is null.
     */
    @Override
    public boolean containsKey(TKey key)
    {
        validateKey(key);

        for (Node current = first; current != null; current = current.next)
        {
            if (keyComparer.compare(current.key, key) == 0)
                return true;
        }

        return false;
    }

    public boolean containsValue(TValue value)
    {
        for (Node current = first; current != null; current = current.next)
        {
            if (valueComparer.compare(current.value, value) == 0)
                return true;
        }

        return false;
    }

    public boolean containsKeyValue(TKey key, TValue value)
    {
        validateKey(key);

        for (Node current = first; current != null; current = current.next)
        {
            if (keyComparer.compare(current.key, key) == 0 &&
                    valueComparer.compare(current.value, value) == 0)
                return true;
        }

        return false;
    }

    /**
     * Tries to remove a {@link KeyValuePair} with the specified TKey. Do nothing if the {@link Dictionary} doesn't
     * contains the specified key.
     * Java's generic programming mechanism makes this method have a long name instead just "remove".
     *
     * @param key The TKey of the element to remove.
     * @return <code>true</code> if the element is successfully removed; otherwise, <code>false</code>. This method
     * also returns <code>false</code> if TKey was not found in the original {@link IDictionary}.
     * @throws NullPointerException The specified TKey is null.
     */
    @Override
    public boolean removeByKey(TKey key)
    {
        validateKey(key);

        // Do nothing if the symbol table is empty.
        if (count == 0 || first == null)
            return false;

        // If the first node contains the item, make first point to first.next and decrease the count by 1.
        if (keyComparer.compare(first.key, key) == 0)
        {
            first = first.next;
            count--;
            version++;
            return true;
        }

        // If the first node doesn't contains the item, then find the node whose next contains the item and modify the next and Size.
        for (Node precursor = first; precursor.next != null; precursor = precursor.next)
        {
            if (keyComparer.compare(precursor.next.key, key) == 0)
            {
                precursor.next = precursor.next.next;
                count--;
                version++;
                return true;
            }
        }

        // If the process reaches here, the symbol table doesn't contain the key-value pair.
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
     * Removes all items from this {@link ICollection}.
     */
    @Override
    public void clear()
    {
        Node current = first;
        while (current != null)
        {
            Node temp = current;
            current = current.next;
            temp.invalidate();
        }
        first = null;
        count = 0;
        version++;
    }

    /**
     * Determines whether this {@link ICollection} contains a specific TValue.
     *
     * @param keyValuePair The {@link KeyValuePair} to locate in this {@link ICollection}.
     * @return <code>true</code> if the specified TValue is found in this {@link ICollection}; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean contains(KeyValuePair<TKey, TValue> keyValuePair)
    {
        return containsKeyValue(keyValuePair.getKey(), keyValuePair.getValue());
    }

    /**
     * Copies the elements of this {@link ICollection} to an array, starting at index 0.
     *
     * @param array The one-dimensional array that is the destination of the elements copied from this
     *              {@link ICollection}. The array must have zero-based indexing.
     * @throws NullPointerException     The given array is null.
     * @throws IllegalArgumentException The number of elements in the source {@link ICollection} is greater than the
     *                                  available space from 0 to the end of the destination array, i.e. the capacity of the given array.
     */
    @Override
    public void copyTo(KeyValuePair<TKey, TValue>[] array)
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
    public void copyTo(KeyValuePair<TKey, TValue>[] array, int startIndex)
    {
        validateArray(array, array.length);
        if ((startIndex < 0) || (startIndex >= array.length))
            throw new ArrayIndexOutOfBoundsException("Array index is less than 0 or greater than or equal to the " +
                                                             "length of the array.");

        Node current = first;
        while (current != null)
        {
            array[startIndex++] = new KeyValuePair<>(current.key, current.value);
            current = current.next;
        }
    }

    /**
     * Removes the first occurrence of a specific object from this {@link ICollection}.
     *
     * @param keyValuePair The object to remove from this {@link ICollection}.
     * @return <code>true</code> if item was successfully removed from the {@link ICollection}; otherwise,
     * <code>false</code>. This method also returns <code>false</code> if item is not found in the original
     * {@link ICollection}.
     */
    @Override
    public boolean remove(KeyValuePair<TKey, TValue> keyValuePair)
    {
        validateKey(keyValuePair.getKey());

        // Do nothing if the symbol table is empty.
        if (count == 0 || first == null)
            return false;

        // If the first node contains the item, make first point to first.next and decrease the count by 1.
        if ((keyComparer.compare(first.key, keyValuePair.getKey()) == 0) &&
                (valueComparer.compare(first.value, keyValuePair.getValue()) == 0))
        {
            first = first.next;
            count--;
            version++;
            return true;
        }

        // If the first node doesn't contains the item, then find the node whose next contains the item and modify the next and count.
        for (Node precursor = first; precursor.next != null; precursor = precursor.next)
        {
            if ((keyComparer.compare(precursor.next.key, keyValuePair.getKey()) == 0) &&
                    (valueComparer.compare(precursor.next.value, keyValuePair.getValue()) == 0))
            {
                precursor.next = precursor.next.next;
                count--;
                version++;
                return true;
            }
        }

        // If the process reaches here, the symbol table doesn't contain the key-value pair.
        return false;
    }

    private abstract class CollectionIterator<T> implements Iterator<T>
    {

        private int version;
        private Dictionary<TKey, TValue> dictionary;

        protected abstract T getItem(Node node);

        private Iterator<T> iterator;

        private CollectionIterator(Dictionary<TKey, TValue> dictionary)
        {
            this.version = dictionary.version;
            this.dictionary = dictionary;
            LinkedList<T> collection = new LinkedList<>();
            for (Node current = first; current != null; current = current.next)
                collection.addLast(getItem(current));

            iterator = collection.iterator();
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
            return iterator.hasNext();
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
            if (version != dictionary.version)
                throw new InvalidOperationException("ICollection object is not allowed to be modified during iterating through it.");

            return iterator.next();
        }
    }

    private class DictionaryIterator extends CollectionIterator<KeyValuePair<TKey, TValue>>
    {

        public DictionaryIterator(Dictionary<TKey, TValue> dictionary)
        {
            super(dictionary);
        }

        @Override
        protected KeyValuePair<TKey, TValue> getItem(Node node)
        {
            return new KeyValuePair<>(node.key, node.value);
        }
    }

    private class KeyIterator extends CollectionIterator<TKey>
    {
        public KeyIterator(Dictionary<TKey, TValue> dictionary)
        {
            super(dictionary);
        }

        @Override
        protected TKey getItem(Node node)
        {
            return node.key;
        }
    }

    private class ValueIterator extends CollectionIterator<TValue>
    {
        public ValueIterator(Dictionary<TKey, TValue> dictionary)
        {
            super(dictionary);
        }

        @Override
        protected TValue getItem(Node node)
        {
            return node.value;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    @Override
    public Iterator<KeyValuePair<TKey, TValue>> iterator()
    {
        return new DictionaryIterator(this);
    }

    private void validateKey(TKey key)
    {
        if (key == null)
            throw new NullPointerException("Argument \"key\" cannot be null.");
    }

    private void validateArray(KeyValuePair<TKey, TValue>[] array, int remainingLength)
    {
        if (array == null)
            throw new NullPointerException("Argument \"array\" cannot be null.");

        if (remainingLength < count)
            throw new IllegalArgumentException("The number of elements in the source Dictionary is greater than the " +
                                                       "available space from startIndex to the end of the destination array.");
    }

    public static void main(String[] args) throws IOException
    {
        // Initialize the dictionary to run the test.
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        stark.dataworks.tests.DictionaryTest.runTest(dictionary);
    }
}
