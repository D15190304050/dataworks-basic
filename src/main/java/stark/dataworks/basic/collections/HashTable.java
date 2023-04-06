package stark.dataworks.basic.collections;

import stark.dataworks.basic.ArgumentOutOfRangeException;
import stark.dataworks.basic.IEqualityComparer;
import stark.dataworks.basic.params.OutValue;

import java.io.Serializable;
import java.util.Iterator;

/**
 * The {@link HashTable} class represents a dictionary of associated keys and values with constant lookup time.
 * Objects used as keys in a hash table must implement the hash()
 * and equals() methods (or they can rely on the default implementations
 * inherited from Object if key equality is simply reference
 * equality). Furthermore, the hash() and equals() methods of
 * a key object must produce the same results given the same parameters for the
 * entire time the key is present in the hash table. In practical terms, this
 * means that key objects should be immutable, at least for the time they are
 * used as keys in a hash table.
 * <p>
 * When entries are added to a hash table, they are placed into buckets based on the hash code of their keys. Subsequent
 * lookups of keys will use the hash code of the keys to only search a particular bucket, thus substantially reducing
 * the number of key comparisons required to find an entry. A hash table's maximum load factor, which can be specified
 * when the hash table is instantiated, determines the maximum ratio of hash table entries to hash buckets. Smaller load
 * factors cause faster average lookup times at the cost of increased memory consumption. The default maximum load
 * factor of 1.0 generally provides the best balance between speed and size. As entries are added to a hash table, the
 * hash table's actual load factor increases, and when the actual load factor reaches the maximum load factor value, the
 * number of buckets in the hash table is automatically increased by approximately a factor of 2 (to be precise, the
 * number of hash table buckets is increased to the smallest prime number that is larger than twice the current number
 * of hash table buckets).
 * <p>
 * Each object provides their own hash function, accessed by calling {@link Object#hashCode()}. However, one can write
 * their own object implementing {@link IEqualityComparer} and pass it to a constructor on the hash table. That hash
 * function (and the equals() method on the {@link IEqualityComparer} would be used for all objects in the hash table).
 */
public class HashTable implements ICollection<KeyValuePair<Object, Object>>, Serializable
{
    /*
        Implementation Notes:
        The generic Dictionary was copied from Hashtable's source - any bug
        fixes here probably need to be made to the generic Dictionary as well.

        This Hashtable uses double hashing.  There are hashsize buckets in the
        table, and each bucket can contain 0 or 1 element.  We a bit to mark
        whether there's been a collision when we inserted multiple elements
        (ie, an inserted item was hashed at least a second time and we probed
        this bucket, but it was already in use).  Using the collision bit, we
        can terminate lookups & removes for elements that aren't in the hash
        table more quickly.  We steal the most significant bit from the hash code
        to store the collision bit.

        Our hash function is of the following form:

        h(key, n) = h1(key) + n*h2(key)

        where n is the number of times we've hit a collided bucket and rehashed
        (on this particular lookup).  Here are our hash functions:

        h1(key) = GetHash(key);  // default implementation calls key.GetHashCode();
        h2(key) = 1 + (((h1(key) >> 5) + 1) % (hashsize - 1));

        The h1 can return any number.  h2 must return a number between 1 and
        hashsize - 1 that is relatively prime to hashsize (not a problem if
        hashsize is prime).  (Knuth's Art of Computer Programming, Vol. 3, p. 528-9)
        If this is true, then we are guaranteed to visit every bucket in exactly
        hashsize probes, since the least common multiple of hashsize and h2(key)
        will be hashsize * h2(key).  (This is the first number where adding h2 to
        h1 mod hashsize will be 0 and we will search the same bucket twice).

        We previously used a different h2(key, n) that was not constant.  That is a
        horrifically bad idea, unless you can prove that series will never produce
        any identical numbers that overlap when you mod them by hashsize, for all
        subranges from i to i+hashsize, for all i.  It's not worth investigating,
        since there was no clear benefit from using that hash function, and it was
        broken.

        For efficiency reasons, we've implemented this by storing h1 and h2 in a
        temporary, and setting a variable called seed equal to h1.  We do a probe,
        and if we collided, we simply add h2 to seed each time through the loop.

        A good test for h2() is to subclass Hashtable, provide your own implementation
        of GetHash() that returns a constant, then add many items to the hash table.
        Make sure Count equals the number of items you inserted.
    */

    static final int HASH_PRIME = 101;
    private static final int INITIAL_SIZE = 3;
    private static final String LOAD_FACTOR_NAME = "LoadFactor";
    private static final String VERSION_NAME = "Version";
    private static final String COMPARATOR_NAME = "Comparator";
    private static final String HASH_CODE_PROVIDER_NAME = "HashCodeProvider";
    private static final String HASH_SIZE_NAME = "HashSize";
    private static final String KEYS_NAME = "Keys";
    private static final String VALUES_NAME = "Values";
    private static final String KEY_COMPARATOR_NAME = "KeyComparator";

    // Deleted entries have their key set to buckets.

    // The hash table data.

    private class Bucket
    {
        public Object key;
        public Object value;

        /**
         * Store hash code; sign bit means there was a collision.
         */
        public int hashCollision;
    }

    private Bucket[] buckets;

    /**
     * The total number of entries in the hash table.
     */
    private int count;

    /**
     * The total number of entries in the hash table.
     */
    private int occupancy;

    private int loadSize;

    private double loadFactor;

    private volatile int version;
    private volatile boolean isWriterInProgress;

    private ICollection<Object> keys;
    private ICollection<Object> values;

    private IEqualityComparer keyComparer;

    public IEqualityComparer getKeyComparer()
    {
        return keyComparer;
    }

    protected IEqualityComparer getEqualityComparer()
    {
        return keyComparer;
    }

    /**
     * Note: this constructor is a bogus constructor that does nothing
     * and is for use only with {@link SyncHashTable}.
     *
     * @param trash
     */
    HashTable(boolean trash)
    {

    }

    /**
     * Constructs a new hash table. The hash table is created with an initial
     * capacity of 0 and a load factor of 1.0.
     */
    public HashTable()
    {
        this(0, 1.0);
    }

    /**
     * Constructs a new hash table with the given initial capacity and a load
     * factor of 1.0. The capacity argument serves as an indication of
     * the number of entries the hash table will contain. When this number (or
     * an approximation) is known, specifying it in the constructor can
     * eliminate a number of resizing operations that would otherwise be
     * performed when elements are added to the hashtable.
     */
    public HashTable(int capacity)
    {
        this(capacity, 1.0);
    }

    /**
     * Constructs a new hash table with the given initial capacity and load
     * factor. The capacity argument serves as an indication of the
     * number of entries the hashtable will contain. When this number (or an
     * approximation) is known, specifying it in the constructor can eliminate
     * a number of resizing operations that would otherwise be performed when
     * elements are added to the hashtable. The loadFactor argument
     * indicates the maximum ratio of hash table entries to hash table buckets.
     * Smaller load factors cause faster average lookup times at the cost of
     * increased memory consumption. A load factor of 1.0 generally provides
     * the best balance between speed and size.
     *
     * @param capacity
     * @param loadFactor
     */
    public HashTable(int capacity, double loadFactor)
    {
        if (capacity < 0)
            throw new ArgumentOutOfRangeException("The parameter \"capacity\" must be greater than or equal to 0.");
        if (!(loadFactor >= 0.1f && loadFactor <= 1.0f))
            throw new ArgumentOutOfRangeException("The parameter \"loadFactor\" must between [0.1, 1.0].");

        // Based on performance work, 0.72 is the optimal load factor for this table.
        this.loadFactor = 0.72f * loadFactor;

        double rawSize = capacity / this.loadFactor;

        if (rawSize > Integer.MAX_VALUE)
            throw new IllegalArgumentException("Capacity overflow.");

        int hashSize = (rawSize > INITIAL_SIZE) ? HashHelpers.getPrime((int) rawSize) : INITIAL_SIZE;
        buckets = (Bucket[]) new Object[hashSize];

        loadSize = (int) (this.loadFactor * hashSize);
        isWriterInProgress = false;

        // Based on the current algorithm, loadSize must be less than hashSize.
        assert (loadSize < hashSize);
    }

    public HashTable(int capacity, double loadFactor, IEqualityComparer comparer)
    {
        this(capacity, loadFactor);
        this.keyComparer = comparer;
    }

    public HashTable(int capacity, IEqualityComparer equalityComparer)
    {
        this(capacity, 1.0, equalityComparer);
    }

    public HashTable(IEqualityComparer equalityComparer)
    {
        this(0, 1.0, equalityComparer);
    }

    public HashTable(IDictionary<Object, Object> d, double loadFactor, IEqualityComparer equalityComparer)
    {
        this(d != null ? d.count() : 0, loadFactor, equalityComparer);
        if (d == null)
            throw new NullPointerException("Argument \"d\" can not be null.");

        for (KeyValuePair<Object, Object> kvp : d)
            add(kvp.getKey(), kvp.getValue());
    }

    public HashTable(IDictionary<Object, Object> d, double loadFactor)
    {
        this(d, loadFactor, (IEqualityComparer) null);
    }

    public HashTable(IDictionary<Object, Object> d, IEqualityComparer equalityComparer)
    {
        this(d, 1.0, equalityComparer);
    }

    // TODO: To be figured out.
    /*
    protected HashTable()
    {

    }
    */

    /**
     * Internal method to get the hash code for an {@link Object}. This will call {@link Object#hashCode()} on each
     * object if you haven't provided an {@link IEqualityComparer} instance. Otherwise, it calls
     * {@link IEqualityComparer#getHashCode(Object)}.
     *
     * @param key
     * @return
     */
    protected int getHash(Object key)
    {
        if (keyComparer != null)
            return keyComparer.getHashCode(key);
        return key.hashCode();
    }

    /**
     * initHash() is basically an implementation of classic DoubleHashing.
     * <p>
     * 1) This only "correctness" requirement is that "increment" used to probe
     * a. Be non-zero
     * b. Be relatively prime to the table size "hashSize". (This is needed to insure you probe)
     * 2) Because we choose table sizes to be primes, we just need to insure that the increment is 0 < "increment" < "hashSize".
     * <p>
     * Thus this function would work: increment = 1 + (seed % (hashSize - 1))
     * <p>
     * While this works well for "uniformly distributed" keys, in practice, non-uniformity is common.
     * In particular in practice we can see "mostly sequential" where you get long clusters of keys that "pack".
     * To Avoid bad behavior you want it to be the case that the increment is "large" even for "small" values (because
     * small values tend to happen more in practice). Thus we multiply "seed" by a number that will make these small
     * values bigger (and not hurt large values). We picked hashPrime(101) because it was prime, and if ("hashSize" - 1)
     * is not a multiple of HashPrime (enforced in getPrime()), then "increment" has the potential of being every value
     * from 1 to ("hashSize" - 1). The choice was largely arbitrary.
     * <p>
     * Computes the hash function: H(key, i) = h1(key) + i * h2(key, hashSize).
     * The out parameter "seed" is h1(key), while the out parameter "increment" is h2(key, hashSize). Callers of this
     * function should add "increment" each time through a loop.
     *
     * @param key
     * @param hashSize
     * @param seed
     * @param increment
     * @return
     */
    private int initHash(Object key, int hashSize, OutValue<Integer> seed, OutValue<Integer> increment)
    {
        // Hash code must be positive. Also, we must not use the sign bit, since that is used for the collision bit.
        int hashCode = getHash(key) & 0x7FFFFFFF;
        seed.setValue(hashCode);

        // Restriction: increment MUST be between 1 and hashSize - 1, inclusive for the modular arithmetic to work
        // correctly. This guarantees you will visit every bucket in the table exactly once within hashSize iterations.
        // Violate this and it will cause obscure bugs forever. If you change this calculation for h2(key), update
        // putEntry() too!
        increment.setValue(1 + ((seed.getValue() * HASH_PRIME) % (hashSize - 1)));
        return hashCode;
    }

    private void putEntry(Bucket[] newBuckets, Object key, Object newValue, int hashCode)
    {
        // Make sure collision bit (sign bit) wasn't set.
        assert hashCode >= 0;

        int seed = hashCode;
        int increment = (1 + ((seed % HASH_PRIME) % (newBuckets.length - 1)));
        int bucketNumber = seed % newBuckets.length;
        for (; ; )
        {
            // TODO: Figure out why Bucket.key can be a buckets.
            if ((newBuckets[bucketNumber].key == null) || (newBuckets[bucketNumber].key == buckets))
            {
                newBuckets[bucketNumber].value = newValue;
                newBuckets[bucketNumber].key = key;
                newBuckets[bucketNumber].hashCollision |= hashCode;
                return;
            }

            if (newBuckets[bucketNumber].hashCollision >= 0)
            {
                newBuckets[bucketNumber].hashCollision |= 0x80000000;
                occupancy++;
            }

            bucketNumber = (int) (((long) bucketNumber + increment) % newBuckets.length);
        }
    }

    private void rehash(int newSize, boolean forceNewHashCode)
    {
        // Reset occupancy.
        occupancy = 0;

        // Don't replace any internal state until we have finished adding to the new Bucket[]. This serves 2 purposes:
        //   1) Allow concurrent readers to see valid hash table contents at all times.
        //   2) Protect against an VMOutOfMemoryException while allocating this new Bucket[].
        Bucket[] newBuckets = (Bucket[]) new Object[newSize];

        // Rehash table into new buckets.
        int nb;
        for (nb = 0; nb < buckets.length; nb++)
        {
            Bucket oldBucket = buckets[nb];
            if ((oldBucket.key != null) && (oldBucket.key != buckets))
            {
                int hashCode = ((forceNewHashCode ? getHash(oldBucket.key) : oldBucket.hashCollision) & 0x7FFFFFFF);
                putEntry(newBuckets, oldBucket.key, oldBucket.value, hashCode);
            }
        }
    }

    private void expand()
    {
        int rawSize = HashHelpers.expandPrime(buckets.length);
        rehash(rawSize, false);
    }

    /**
     * Inserts an entry into this {@link HashTable}. This method is called from the set() and add() methods. If the add
     * parameter is true and the given key already exists in the {@link HashTable}, an exception is thrown.
     *
     * @param key
     * @param value
     * @param add
     */
    private void insert(Object key, Object value, boolean add)
    {
        if (key == null)
            throw new NullPointerException("The argument \"key\" cannot be null.");

        if (count > loadSize)
            expand();
    }

    /**
     * Adds an entry with the given key and value to this {@link HashTable}. An {@link IllegalArgumentException} is
     * thrown if the key is null or if the key is already present in the {@link HashTable}.
     *
     * @param key
     * @param value
     */
    public void add(Object key, Object value)
    {
        insert(key, value, true);
    }

    public void set(Object key, Object value)
    {
        insert(key, value, false);
    }

    /**
     * Removes all items from this {@link HashTable}.
     */
    @Override
    public void clear()
    {
        // Race condition detected in usages of Hashtable - multiple threads appear to be writing to a Hashtable
        // instance simultaneously!  Don't do that - use Hashtable.Synchronized.
        assert !isWriterInProgress;

        if ((count == 0) && (occupancy == 0))
            return;

        // Thread.beginCriticalRegion();
        isWriterInProgress = true;
        for (int i = 0; i < buckets.length; i++)
        {
            buckets[i].hashCollision = 0;
            buckets[i].key = null;
            buckets[i].value = null;
        }

        count = 0;
        occupancy = 0;
        version++;
        isWriterInProgress = false;
        // Thread.endCriticalRegion();
    }

    /**
     * Returns a virtually identical copy of this {@link HashTable}. This does a shallow copy - the Objects in the
     * table are not cloned, only the references to those Objects.
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        Bucket[] bucketsCopy = buckets;
        HashTable ht = new HashTable(count, keyComparer);
        ht.version = version;
        ht.loadFactor = loadFactor;
        ht.count = count; // It seems to be a bug in ReferenceSource.
        int bucketLength = bucketsCopy.length;
        while (bucketLength > 0)
        {
            bucketLength--;
            Object key = bucketsCopy[bucketLength].key;
            if ((key != null) && (key != bucketsCopy))
                ht.set(key, bucketsCopy[bucketLength].value);
        }

        return ht;
    }

    /**
     * Internal method to compare 2 keys. If you have provided an {@link IEqualityComparer} instance in the constructor,
     * this method will call keyComparer.equals(item, key). Otherwise, it will call item.equals(key).
     *
     * @param item
     * @param key
     * @return
     */
    public boolean keyEquals(Object item, Object key)
    {
        assert key != null;

        if (item == buckets)
            return false;

        if (item == key)
            return true;

        if (keyComparer != null)
            return keyComparer.equals(item, key);
        return item == null ? false : item.equals(key);
    }

    /**
     * Checks if this {@link HashTable} contains an entry with the given key. This is an O(1) operation.
     *
     * @param key
     * @return
     */
    public boolean containsKey(Object key)
    {
        if (key == null)
            throw new NullPointerException("Argument \"key\" cannot be null.");

        OutValue<Integer> seed = new OutValue<>(0);
        OutValue<Integer> increment = new OutValue<>(0);

        // Take a snapshot of buckets, in case another thread resizes this HashTable.
        Bucket[] bucketsCopy = buckets;
        int hashCode = initHash(key, bucketsCopy.length, seed, increment);
        int ntry = 0;

        Bucket b;
        int bucketNumber = seed.getValue() % bucketsCopy.length;
        do
        {
            b = bucketsCopy[bucketNumber];
            if (b.key == null)
                return false;
            if (((b.hashCollision & 0x7FFFFFFF) == hashCode) && keyEquals(b.key, key))
                return true;
            bucketNumber = (bucketNumber + increment.getValue()) % bucketsCopy.length;
        }
        while ((b.hashCollision) < 0 && (++ntry < bucketsCopy.length));

        return false;
    }

    /**
     * Checks if this {@link HashTable} contains an entry with the given value. The values of the entries of the
     * {@link HashTable} are compared to the given value using the {@link Object#equals(Object)} method. This method
     * performs a linear search and is thus be substantially slower than the containsKey() method.
     *
     * @param value
     * @return
     */
    public boolean containsValue(Object value)
    {
        if (value == null)
        {
            for (int i = buckets.length; --i >= 0; )
            {
                if ((buckets[i].key != null) && (buckets[i].key != buckets) && (buckets[i].value == null))
                    return true;
            }
        }
        else
        {
            for (int i = buckets.length; --i >= 0; )
            {
                Object entryValue = buckets[i].value;
                if ((entryValue != null) && (entryValue.equals(value)))
                    return true;
            }
        }

        return false;
    }

    // TODO: Figure out the KeyCollection class in the following comment.
    /**
     * Copies the keys of this {@link HashTable} to a given array starting at a given index. This method is used by the
     * implementation of the copyTo() method in the KeyCollection class.
     * @param keys
     * @param startIndex
     */
    public void copyKeys(Object[] keys, int startIndex)
    {
        if (keys == null)
            throw new NullPointerException("Argument \"keys\" cannot be null.");
        if (startIndex < 0)
            throw new IndexOutOfBoundsException("The start index of an array must be greater than or equal to 0.");

        int copyLength = keys.length - startIndex;
        if (copyLength < count)
            throw new IllegalArgumentException("The length between the start index and the end of the given array is not enough for copying contents.");

        Bucket[] bucketsCopy = buckets;
        for (int i = bucketsCopy.length; --i >= 0;)
        {
            Object key = bucketsCopy[i].key;
            if ((key != null) && (key != buckets))
                keys[startIndex + i] = key;
        }
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
    public boolean remove(KeyValuePair<Object, Object> value)
    {
        return false;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<KeyValuePair<Object, Object>> iterator()
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
    public boolean contains(KeyValuePair<Object, Object> value)
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
    public void copyTo(KeyValuePair<Object, Object>[] array)
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
    public void copyTo(KeyValuePair<Object, Object>[] array, int startIndex)
    {

    }

    public static class HashHelpers
    {
        /**
         * This is the maximum prime smaller than Array.MaxArrayLength.
         */
        public static final int MAX_PRIME_ARRAY_LENGTH = 0x7FEFFFFD;

        /**
         * Table of prime numbers to use as hash table sizes.
         * A typical resize algorithm would pick the smallest prime number in this array
         * that is larger than twice the previous capacity.
         * Suppose our {@link HashTable} currently has capacity x and enough elements are added
         * such that a resize needs to occur. Resizing first computes 2x then finds the
         * first prime in the table greater than 2x, i.e. if primes are ordered
         * p_1, p_2, ..., p_i, ..., it finds p_n such that p_n-1 < 2x < p_n.
         * Doubling is important for preserving the asymptotic complexity of the
         * hashtable operations such as add.  Having a prime guarantees that double
         * hashing does not lead to infinite loops.  IE, your hash function will be
         * h1(key) + i*h2(key), 0 <= i < size.  h2 and the size must be relatively prime.
         */
        public static final int[] PRIMES = {3, 7, 11, 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919, 1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591, 17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437, 187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263, 1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369};

        public static int getPrime(int min)
        {
            if (min < 0)
                throw new IllegalArgumentException("The parameter \"min\" cannot be less than 0.");

            for (int i = 0; i < PRIMES.length; i++)
            {
                int prime = PRIMES[i];
                if (prime >= min)
                    return prime;
            }

            // Outside of our pre-defined table. Compute the hard way.
            for (int i = (min | 1); i < Integer.MAX_VALUE; i += 2)
            {
                if (isPrime(i) && ((i - 1) % HashTable.HASH_PRIME != 0))
                    return i;
            }

            return min;
        }

        public static int getMinPrime()
        {
            return PRIMES[0];
        }


        public static boolean isPrime(int candidate)
        {
            if ((candidate & 1) != 0)
            {
                int limit = (int) Math.sqrt(candidate);
                for (int divisor = 3; divisor <= limit; divisor += 2)
                {
                    if ((candidate % divisor) == 0)
                        return false;
                }
                return true;
            }

            return (candidate == 2);
        }

        public static int expandPrime(int oldSize)
        {
            int newSize = oldSize * 2;

            if (newSize > MAX_PRIME_ARRAY_LENGTH && MAX_PRIME_ARRAY_LENGTH > oldSize)
                return MAX_PRIME_ARRAY_LENGTH;

            return getPrime(newSize);
        }
    }
}
