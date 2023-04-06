package stark.dataworks.basic.collections;

import stark.dataworks.basic.ArgumentOutOfRangeException;
import stark.dataworks.basic.KeyNotFountException;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The {@link HashDictionary} class represents a generic hash table implementation.
 * @param <TKey> Type of keys in this directory.
 * @param <TValue> Type of values in this directory.
 */
public class HashDictionary<TKey, TValue> implements IDictionary<TKey, TValue>, Serializable
{
    private class Entry
    {
        /**
         * Lower 31 bits of hash code, -1 if unused.
         */
        public int hashCode;

        /**
         * Index of next entry, -1 if last.
         */
        public int next;

        /**
         * Key of entry.
         */
        public TKey key;

        /**
         * Value of entry.
         */
        public TValue value;
    }

    private int[] buckets;
    private Entry[] entries;
    private int count;
    private int version;
    private int freeList;
    private int freeCount;
    private Comparator<TKey> comparator;
    private KeyCollection keys;
    private ValueCollection values;
    private Object syncRoot;

    public HashDictionary(int capacity, Comparator<TKey> comparator)
    {
        if (capacity < 0)
            throw new ArgumentOutOfRangeException("Capacity of a HashDictionary must be a non-negative integer.");

    }

    private void initialize(int capacity)
    {

    }

    /**
     * Gets the element associated with the specified key.
     *
     * @param tKey The key of the element to get.
     * @return The value associated with the specified key.
     * @throws NullPointerException The specified key is null.
     * @throws KeyNotFountException The specified key is not in the {@link IDictionary}.
     */
    @Override
    public TValue get(TKey tKey)
    {
        return null;
    }

    /**
     * Sets the element associated with the specified key.
     *
     * @param tKey   The key of the element to get.
     * @param tValue The new value associated with the specified key.
     * @throws NullPointerException The specified key is null.
     * @throws KeyNotFountException The specified key is not in the {@link IDictionary}.
     */
    @Override
    public void set(TKey tKey, TValue tValue)
    {

    }

    /**
     * Gets an {@link Iterable} containing all the keys of the {@link IDictionary}.
     *
     * @return an {@link Iterable} containing all the keys of the {@link IDictionary}.
     */
    @Override
    public Iterable<TKey> keys()
    {
        return null;
    }

    /**
     * Gets an {@link Iterable} containing all the values of the {@link IDictionary}.
     *
     * @return an {@link Iterable} containing all the values of the {@link IDictionary}.
     */
    @Override
    public Iterable<TValue> values()
    {
        return null;
    }

    /**
     * Adds an element with the provided key and value to this {@link IDictionary}.
     *
     * @param tKey   The object to use as the key of the element to add.
     * @param tValue The object to use as the value of the element to add, which is associated with the key.
     * @throws NullPointerException     The specified key is null.
     * @throws IllegalArgumentException An element with the same key already exists in this {@link IDictionary}.
     */
    @Override
    public void add(TKey tKey, TValue tValue)
    {

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

    }

    /**
     * Determines whether this {@link IDictionary} contains an element with the specified key.
     *
     * @param tKey The key to locate in this {@link IDictionary}.
     * @return <code>true</code> if this {@link IDictionary} contains an element with the key; otherwise,
     * <code>false</code>.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean containsKey(TKey tKey)
    {
        return false;
    }

    /**
     * Determines whether this {@link IDictionary} contains an element with the specified value.
     *
     * @param tValue The value to locate in this {@link IDictionary}.
     * @return <code>true</code> if this {@link IDictionary} contains an element with the value; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean containsValue(TValue tValue)
    {
        return false;
    }

    /**
     * Determines whether this {@link IDictionary} contains an element with the specified key and value.
     *
     * @param tKey   The key to locate in this {@link IDictionary}.
     * @param tValue The value associated with the specified key.
     * @return <code>true</code> if this {@link IDictionary} contains an element with the key and value; otherwise,
     * <code>false</code>.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean containsKeyValue(TKey tKey, TValue tValue)
    {
        return false;
    }

    /**
     * Tries to remove a {@link KeyValuePair} with the specified key.
     * Java's generic programming mechanism makes this method have a long name instead just "remove".
     *
     * @param tKey The key of the element to remove.
     * @return <code>true</code> if the element is successfully removed; otherwise, <code>false</code>. This method
     * also returns <code>false</code> if key was not found in the original {@link IDictionary}.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean removeByKey(TKey tKey)
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
     * Removes the first occurrence of a specific object from this {@link ICollection}.
     *
     * @param value The object to remove from this {@link ICollection}.
     * @return <code>true</code> if item was successfully removed from the {@link ICollection}; otherwise,
     * <code>false</code>. This method also returns <code>false</code> if item is not found in the original
     * {@link ICollection}.
     */
    @Override
    public boolean remove(KeyValuePair<TKey, TValue> value)
    {
        return false;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<KeyValuePair<TKey, TValue>> iterator()
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
     * Determines whether this {@link ICollection} contains a specific value.
     *
     * @param value The value to locate in this {@link ICollection}.
     * @return <code>true</code> if the specified value is found in this {@link ICollection}; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean contains(KeyValuePair<TKey, TValue> value)
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
    public void copyTo(KeyValuePair<TKey, TValue>[] array)
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
    public void copyTo(KeyValuePair<TKey, TValue>[] array, int startIndex)
    {

    }

    public final class KeyCollection implements ICollection<TKey>
    {

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
        public boolean remove(TKey value)
        {
            return false;
        }

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<TKey> iterator()
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
         * Determines whether this {@link ICollection} contains a specific value.
         *
         * @param value The value to locate in this {@link ICollection}.
         * @return <code>true</code> if the specified value is found in this {@link ICollection}; otherwise,
         * <code>false</code>.
         */
        @Override
        public boolean contains(TKey value)
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
        public void copyTo(TKey[] array)
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
        public void copyTo(TKey[] array, int startIndex)
        {

        }
    }

    public final class ValueCollection implements ICollection<TValue>
    {

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
        public boolean remove(TValue value)
        {
            return false;
        }

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<TValue> iterator()
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
         * Determines whether this {@link ICollection} contains a specific value.
         *
         * @param value The value to locate in this {@link ICollection}.
         * @return <code>true</code> if the specified value is found in this {@link ICollection}; otherwise,
         * <code>false</code>.
         */
        @Override
        public boolean contains(TValue value)
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
        public void copyTo(TValue[] array)
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
        public void copyTo(TValue[] array, int startIndex)
        {

        }
    }
}
