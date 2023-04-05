package stark.dataworks.indices;

import stark.dataworks.KeyNotFountException;
import stark.dataworks.collections.IDictionary;
import stark.dataworks.collections.KeyValuePair;
import stark.dataworks.collections.Queue;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * The {@link BTree} class represents an ordered dictionary of generic key-value pairs.
 *
 * @param <TKey>   The type of keys in the BTree.
 * @param <TValue> The type of values in the BTree.
 */
public class BTree<TKey extends Comparable<TKey>, TValue> implements IDictionary<TKey, TValue>
{
    /**
     * The {@link Entry} class represents an entry of the BTree. It contains a key, a value and a reference of its
     * sub-node. Internal nodes (non-leaf nodes) only use "key" and "next". External nodes (leaf nodes) only use "key"
     * and "value".
     */
    private class Entry
    {
        /**
         * Key of the {@link Entry}.
         */
        private TKey key;

        /**
         * Value of the {@link Entry}.
         */
        private TValue value;

        /**
         * Next node of the {@link Entry}.
         * Helper filed to iterate over array entries.
         */
        private Node next;

        /**
         * Initializes an {@link Entry} with specified key, value and next node.
         *
         * @param key   Key of the {@link Entry}.
         * @param value Value of the {@link Entry}.
         * @param next  Next node of the {@link Entry}.
         */

        public Entry(TKey key, TValue value, Node next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Invalidates the {@link Entry} so that the {@link Entry} can be destruct by GC.
         */
        public void invalidate()
        {
            key = null;
            value = null;
            next = null;
        }
    }

    /**
     * The {@link Node} class represents a Node of a {@link BTree}. Each node of a {@link BTree} is a collection of
     * {@link Entry}.
     */
    private final class Node
    {
        /**
         * Number of {@link Entry} in the {@link Node}.
         */
        private int childrenCount;

        /**
         * The collection of {@link Entry}.
         */
        private Entry[] children;

        /**
         * Initializes a {@link Node} with specified number of existing children and maximum number of children.
         *
         * @param childrenCount Initial number of {@link Entry} in the {@link Node}.
         * @param maxChildren   Maximum number of {@link Entry} in the {@link Node}.
         */

        private Node(int childrenCount, int maxChildren)
        {
            this.childrenCount = childrenCount;
            children = (Entry[]) Array.newInstance(Entry.class, maxChildren);
        }

        /**
         * Invalidates the {@link Node} so that the {@link Node} can be destruct by GC.
         */
        private void invalidate()
        {
            children = null;
            childrenCount = 0;
        }
    }

    /**
     * Maximum number of {@link Entry} in the {@link Node}.
     */
    private final int MAX_CHILDREN;

    /**
     * Root of the B-tree.
     */
    private Node root;

    /**
     * Height of the B-tree.
     */
    private int height;

    /**
     * Number of key-value pairs in the B-tree.
     */
    private int count;

    /**
     * Initializes a {@link BTree} with specified maximum number of entries in a node.
     *
     * @param maxChildren Maximum number of entries in a node. Note that this parameter must be an positive even number
     *                    and greater than 2. If it is 2, it is like a binary tree.
     */

    public BTree(int maxChildren)
    {
        if (maxChildren % 2 == 1)
            throw new IllegalArgumentException("Argument \"maxChildren\" must be an integer.");
        if (maxChildren <= 2)
            throw new IllegalArgumentException("Argument \"maxChildren\" must be greater than 2.");
        MAX_CHILDREN = maxChildren;
        root = new Node(0, maxChildren);
    }

    /**
     * Gets the element associated with the specified key.
     *
     * @param key The key of the element to get.
     * @return The value associated with the specified key.
     * @throws NullPointerException The specified key is null.
     * @throws KeyNotFountException The specified key is not in the {@link BTree}.
     */
    @Override
    public TValue get(TKey key)
    {
        if (key == null)
            throw new NullPointerException("The argument \"key\" cannot be null.");
        Entry searchResult = find(key);

        if (searchResult != null)
            return searchResult.value;
        else
            throw new KeyNotFountException("The specified key is not in the BTree.");
    }

    /**
     * Tries to find the leaf {@link Entry} that contains the specified key.
     *
     * @param key The specified key.
     * @return The leaf {@link Entry} that contains the specified key if the specified key exist in such a leaf
     * {@link Entry}, otherwise, null.
     */
    private Entry find(TKey key)
    {
        return find(root, key, height);
    }

    /**
     * Tries to find the leaf {@link Entry} that contains the specified key. The expected {@link Entry} is in the
     * sub-tree rooted at "node", or the key does not exist in the {@link BTree}.
     *
     * @param node   Root of the sub-tree that may contains the expected {@link Entry}.
     * @param key    The specified key.
     * @param height Height of the current {@link Node}.
     * @return The leaf {@link Entry} that contains the specified key if the specified key exist in such a leaf
     * {@link Entry}, otherwise, null.
     */
    private Entry find(Node node, TKey key, int height)
    {
        Entry[] children = node.children;

        // If height is 0, try to find the expected node in leaf-entries.
        // Otherwise, determine which branch to go.
        if (height == 0)
        {
            for (int i = 0; i < node.childrenCount; i++)
            {
                if (key.compareTo(children[i].key) == 0)
                    return children[i];
            }
        }
        else
        {
            for (int i = 0; i < node.childrenCount; i++)
            {
                if ((i + 1 == node.childrenCount) || (key.compareTo(children[i + 1].key) < 0))
                    return find(children[i].next, key, height - 1);
            }
        }

        return null;
    }

    /**
     * Sets the value associated with the specified key.
     * If the specified key does not exist in the {@link BTree}, the the key is added to the {@link BTree}.
     *
     * @param key   The key of the element to get.
     * @param value The new value associated with the specified key.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public void set(TKey key, TValue value)
    {
        insert(key, value, false);
    }

    /**
     * Inserts the specified key-value pair to the {@link BTree}.
     *
     * @param key   Key of the key-value pair to insert.
     * @param value Value of the key-value pair to insert.
     * @param add   A boolean flag that indicates if this method is called for adding a key-value pair or set one.
     * @throws NullPointerException     The given "key" or "value" is null.
     * @throws IllegalArgumentException If try to add a key-value pair to the {@link BTree}, while the specified key
     *                                  exists in the {@link BTree}.
     */

    private void insert(TKey key, TValue value, boolean add)
    {
        if (key == null)
            throw new NullPointerException("The argument \"key\" cannot be null.");
        if (value == null)
            throw new NullPointerException("The argument \"value\" cannot be null.");

        // Try to insert the given key-value pair.
        Node newBranch = insert(root, key, value, height, add);

        // Split the root if a new branch is created.
        if (newBranch != null)
        {
            // Create a new root and set its 2 children.
            Node newRoot = new Node(2, MAX_CHILDREN);
            newRoot.children[0] = new Entry(root.children[0].key, null, root);
            newRoot.children[1] = new Entry(newBranch.children[0].key, null, newBranch);

            // Update "root", let it point to the new created root.
            root = newRoot;

            // Increase "height" since the original root is a sub-tree of the new root.
            height++;
        }
    }

    /**
     * Inserts the specified key-value pair to the {@link BTree}. The entry that contains the given key-value pair is
     * rooted at "node" (before split).
     *
     * @param node   Root of the sub-tree that should contain the given key-value pair (before split).
     * @param key    Key of the key-value pair to insert.
     * @param value  Value of the key-value pair to insert.
     * @param height Height of the current {@link Node}.
     * @param add    A boolean flag that indicates if this method is called for adding a key-value pair or set one.
     * @return A {@link Node} that contains right half of "node" if "node.childrenCount" equals "MAX_CHILDREN" and is
     * split, otherwise, null.
     */
    private Node insert(Node node, TKey key, TValue value, int height, boolean add)
    {
        // i is the index of the entry whose key is less than or equal to "key".
        // After "break" statement, all entries in "node" with index less than i contain keys that are less than "key".
        // And all entries in "node" with index greater than i contain keys tha are greater than "key".
        // Thus, the given key-value pair should be a part of "node.children[i]".
        int i;
        Entry entry = new Entry(key, value, null);

        // External node.
        if (height == 0)
        {
            for (i = 0; i < node.childrenCount; i++)
            {
                // If the given key is an existing key in the BTree, 2 branches:
                // 1. If add is true, throw an IllegalArgumentException to indicate that the developer is trying to add duplicate key.
                // 2. If add is false, change the value of the entry. No change in the "count" field.
                if (key.compareTo(node.children[i].key) == 0)
                {
                    if (add)
                        throw new IllegalArgumentException("Try to add duplicate key.");
                    else
                    {
                        // Since the specified key is a key that exists in the BTree, split is not called, return directly is OK.
                        node.children[i].value = value;
                        return null;
                    }
                }

                // If "key" is less than "node.children[i].key" here, all keys in the entries with index less than i
                // are less than "key", and all keys in the entries with index greater than or equal to i are greater
                // than "key". Thus, i is the correct index for the given key-value pair. All entries with index greater
                // than or equal to i should be moved to right.
                if (key.compareTo(node.children[i].key) < 0)
                    break;
            }

            // If the program goes into this branch, previous keys of "node.children[i].key" are all less than "key",
            // and "key" is less than "node.children[i].key", thus, a new key-value pair should be added to this BTree.
            // On the other hand, if (i == node.childrenCount), then all existing keys in "node" are less than "key".
            // Thus, a new key-value pair is added to this node.
            if ((i == node.childrenCount) || (key.compareTo(node.children[i].key) < 0))
                count++;
        }

        // Internal node.
        else
        {
            for (i = 0; i < node.childrenCount; i++)
            {
                // If (i + 1) == node.childrenCount, then all existing keys in "node" are less than "key".
                // Otherwise, if "key" < node.children[i + 1].key, then all keys in the entries with index less than i
                // are less than "key", and all keys in the entries with index greater than or equal to i are greater
                // than "key". Thus, the given key-value pair should be inserted into node.children[i].
                // Note: all keys in a node are greater than or equal to node.children[i].key.
                if ((i + 1 == node.childrenCount) || (key.compareTo(node.children[i + 1].key) < 0))
                {
                    Node newBranch = insert(node.children[i++].next, key, value, height - 1, add);

                    // If a new branch is created, then a entry is set.
                    if (newBranch == null)
                        return null;
                    entry.key = newBranch.children[0].key;
                    entry.next = newBranch;
                    break;
                }
            }
        }

        // Move all the entries with index greater than i.
        for (int j = node.childrenCount; j > i; j--)
            node.children[j] = node.children[j - 1];
        node.children[i] = entry;
        node.childrenCount++;

        // Split "node" if "node" is full.
        if (node.childrenCount < MAX_CHILDREN)
            return null;
        else
            return split(node);
    }

    /**
     * Splits node in half.
     *
     * @param node The node to split.
     * @return A new node, which is the sibling node of the given node.
     */

    private Node split(Node node)
    {
        // The new branch has the right half of "node".
        int half = node.childrenCount / 2;
        Node newBranch = new Node(half, MAX_CHILDREN);
        node.childrenCount -= half;
        for (int i = 0; i < half; i++)
            newBranch.children[i] = node.children[half + i];

        return newBranch;
    }

    /**
     * Returns a {@link Queue} that contains all existing keys in the {@link BTree}.
     *
     * @return A {@link Queue} that contains all existing keys in the {@link BTree}.
     */

    private Queue<TKey> getAllKeys()
    {
        Queue<TKey> keys = new Queue<>();
        getAllKeys(root, keys, height);
        return keys;
    }

    /**
     * Iterates over all entries by depth-first search (or in-order traversal) and add all existing keys into the
     * {@link Queue} in increasing order.
     *
     * @param node   The {@link Node} to iterate over.
     * @param keys   The {@link Queue} to store all existing keys.
     * @param height Height of the current {@link Node}.
     */
    private void getAllKeys(Node node, Queue<TKey> keys, int height)
    {
        // If height == 0, get all keys of "node".
        // Otherwise, get all keys rooted at "node".
        if (height == 0)
        {
            for (int i = 0; i < node.childrenCount; i++)
                keys.enqueue(node.children[i].key);
        }
        else
        {
            for (int i = 0; i < node.childrenCount; i++)
                getAllKeys(node.children[i].next, keys, height - 1);
        }
    }

    /**
     * Gets an {@link Iterable} containing all the keys of the {@link BTree}.
     *
     * @return an {@link Iterable} containing all the keys of the {@link BTree}.
     */
    @Override
    public Iterable<TKey> keys()
    {
        return getAllKeys();
    }

    /**
     * Gets an {@link Iterable} containing all the values of the {@link BTree}.
     *
     * @return an {@link Iterable} containing all the values of the {@link BTree}.
     */

    private Queue<TValue> getAllValues()
    {
        Queue<TValue> values = new Queue<>();
        getAllValues(root, values, height);
        return values;
    }

    /**
     * Iterates over all entries by depth-first search (or in-order traversal) and add all existing values into the
     * {@link Queue} in increasing order.
     *
     * @param node   The {@link Node} to iterate over.
     * @param values The {@link Queue} to store all existing values.
     * @param height Height of the current {@link Node}.
     */
    private void getAllValues(Node node, Queue<TValue> values, int height)
    {
        // If height == 0, get all values of "node".
        // Otherwise, get all values rooted at "node".
        if (height == 0)
        {
            for (int i = 0; i < node.childrenCount; i++)
                values.enqueue(node.children[i].value);
        }
        else
        {
            for (int i = 0; i < node.childrenCount; i++)
                getAllValues(node.children[i].next, values, height - 1);
        }
    }

    /**
     * Gets an {@link Iterable} containing all the values of the {@link BTree}.
     *
     * @return an {@link Iterable} containing all the values of the {@link BTree}.
     */
    @Override
    public Iterable<TValue> values()
    {
        return getAllValues();
    }

    /**
     * Adds an element with the provided key and value to this {@link BTree}.
     *
     * @param key   The object to use as the key of the element to add.
     * @param value The object to use as the value of the element to add, which is associated with the key.
     * @throws NullPointerException     The specified key is null.
     * @throws IllegalArgumentException An element with the same key already exists in this {@link BTree}.
     */
    @Override
    public void add(TKey key, TValue value)
    {
        insert(key, value, true);
    }

    /**
     * Adds an element with the provided key and value to this {@link BTree}.
     *
     * @param keyValuePair The object to add with the key and the value.
     * @throws NullPointerException     The specified key value pair is null.
     * @throws IllegalArgumentException An element with the same key already exists in this {@link BTree}.
     */
    @Override
    public void add(KeyValuePair<TKey, TValue> keyValuePair)
    {
        add(keyValuePair.getKey(), keyValuePair.getValue());
    }

    /**
     * Determines whether this {@link BTree} contains an element with the specified key.
     *
     * @param key The key to locate in this {@link BTree}.
     * @return <code>true</code> if this {@link BTree} contains an element with the key; otherwise,
     * <code>false</code>.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean containsKey(TKey key)
    {
        return find(key) != null;
    }

    /**
     * Determines whether this {@link BTree} contains an element with the specified value.
     *
     * @param value The value to locate in this {@link BTree}.
     * @return <code>true</code> if this {@link BTree} contains an element with the value; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean containsValue(TValue value)

    {
        // There is no way to determine which entry contains the specified value.
        // Because values are not ordered.
        for (TValue v : values())
        {
            if (value.equals(v))
                return true;
        }
        return false;
    }

    /**
     * Determines whether this {@link BTree} contains an element with the specified key and value.
     *
     * @param key   The key to locate in this {@link BTree}.
     * @param value The value associated with the specified key.
     * @return <code>true</code> if this {@link BTree} contains an element with the key and value; otherwise,
     * <code>false</code>.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean containsKeyValue(TKey key, TValue value)
    {
        Entry searchResult = find(key);
        if (searchResult == null)
            return false;
        return searchResult.value.equals(value);
    }

    /**
     * Tries to remove a {@link KeyValuePair} with the specified key.
     * Java's generic programming mechanism makes this method have a long name instead just "remove".
     *
     * @param key The key of the element to remove.
     * @return <code>true</code> if the element is successfully removed; otherwise, <code>false</code>. This method
     * also returns <code>false</code> if key was not found in the original {@link BTree}.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean removeByKey(TKey key)
    {
        boolean removeResult = remove(root, key, null, false, height);

        // I don't know if clear() is required.
        if (count == 0)
            clear();

        return removeResult;
    }

    /**
     * Tries to remove a specified key-value pair rooted at the specified {@link Node}.
     *
     * @param node       The {@link Node} that the specified key-value pair may rooted at.
     * @param key        Key of the specified key-value pair.
     * @param value      Value of the specified key-value pair.
     * @param checkValue A boolean flag that indicates if this method should check value part of the found entry.
     *                   If it is true, only when the entry's value equals the specified value can the entry be removed.
     * @param height     Height of the current {@link Node}.
     * @return {@code true} if the specified key-value pair is removed from the sub-tree rooted at "node", otherwise,
     * false. If it returns false, then it means the specified key-value pair does not exist in the {@link BTree}.
     */
    public boolean remove(Node node, TKey key, TValue value, boolean checkValue, int height)
    {
        Entry[] children = node.children;

        if (height == 0)
        {
            // If found is set to true, then i is the index of the entry whose key equals "key".
            int i;
            boolean found = false;
            for (i = 0; i < node.childrenCount; i++)
            {
                if (key.compareTo(children[i].key) == 0)
                {
                    found = true;
                    break;
                }
            }

            if (found)
            {
                // Return false directly without modifying the BTree if try to remove a key-value pair while no such key-value pair in the BTree.
                if (checkValue)
                {
                    Entry entry = children[i];
                    if (!value.equals(entry.value))
                        return false;
                }

                // Remove the entry with index i.
                children[i].invalidate();
                for (int j = i; j < node.childrenCount; j++)
                    children[j] = children[j + 1];
                children[node.childrenCount - 1] = null;

                // Update 2 counters after removing the entry, and then return true to indicate that the entry is removed successfully.
                node.childrenCount--;
                count--;
                return true;
            }
        }
        else
        {
            for (int i = 0; i < node.childrenCount; i++)
            {
                if ((i + 1 == node.childrenCount) || (key.compareTo(children[i + 1].key) < 0))
                {
                    Node childNode = children[i].next;
                    boolean result = remove(childNode, key, value, checkValue, height - 1);

                    // Fix keys in non-leaf nodes.
                    if (result)
                    {
                        if (children[i].key.compareTo(childNode.children[0].key) != 0)
                            children[i].key = childNode.children[0].key;
                    }

                    // Invalidate and remove the child node if its children count is 0.
                    // Update node.childrenCount after it.
                    if (childNode.childrenCount == 0)
                    {
                        childNode.invalidate();
                        for (int j = i; j < node.childrenCount; j++)
                            children[j] = children[j + 1];
                        children[node.childrenCount - 1] = null;
                        node.childrenCount--;
                    }

                    return result;
                }
            }
        }

        return false;
    }

    /**
     * Removes all entries from this {@link BTree}.
     */
    @Override
    public void clear()
    {
        // Clear all existing key-value pairs.
        clear(root, height);

        // Set count to 0 after clearing the BTree.
        count = 0;
    }

    /**
     * Removes all entries rooted at the specified {@link Node}.
     *
     * @param node   The specified {@link Node} whose sub-tree is going to be cleared.
     * @param height Height of the current {@link Node}.
     */
    private void clear(Node node, int height)
    {
        // The annotation for parameter "Node node" is removed.
        // Because when height equals -1, node is null, that is where recursive ends.
        if (height >= 0)
        {
            Entry[] children = node.children;

            // Clear nodes in sub-trees.
            for (int i = 0; i < node.childrenCount; i++)
                clear(children[i].next, height - 1);

            // Clear the current node.
            for (int i = 0; i < node.childrenCount; i++)
                children[i].invalidate();
            node.invalidate();
        }
    }

    /**
     * Removes the first occurrence of a specific object from this {@link BTree}.
     *
     * @param keyValuePair The object to remove from this {@link BTree}.
     * @return <code>true</code> if item was successfully removed from the {@link BTree}; otherwise,
     * <code>false</code>. This method also returns <code>false</code> if item is not found in the original
     * {@link BTree}.
     */
    @Override
    public boolean remove(KeyValuePair<TKey, TValue> keyValuePair)
    {
        return remove(root, keyValuePair.getKey(), keyValuePair.getValue(), true, height);
    }

    /**
     * Gets an {@link Iterable} containing all key-value pairs of the {@link BTree}.
     *
     * @return an {@link Iterable} containing all key-value pairs of the {@link BTree}.
     */

    private Queue<KeyValuePair<TKey, TValue>> getKeyValuePairs()
    {
        Queue<KeyValuePair<TKey, TValue>> keyValuePairs = new Queue<>();
        getKeyValuePairs(root, keyValuePairs, height);
        return keyValuePairs;
    }

    /**
     * Iterates over all entries by depth-first search (or in-order traversal) and add all existing key-value pairs into the
     * {@link Queue} in increasing order.
     *
     * @param node          The {@link Node} to iterate over.
     * @param keyValuePairs The {@link Queue} to store all existing values.
     * @param height        Height of the current {@link Node}.
     */
    private void getKeyValuePairs(Node node, Queue<KeyValuePair<TKey, TValue>> keyValuePairs, int height)
    {
        if (height == 0)
        {
            for (int i = 0; i < node.childrenCount; i++)
                keyValuePairs.enqueue(new KeyValuePair<>(node.children[i].key, node.children[i].value));
        }
        else
        {
            for (int i = 0; i < node.childrenCount; i++)
                getKeyValuePairs(node.children[i].next, keyValuePairs, height - 1);
        }
    }

    /**
     * Returns an iterator over all key-value pairs of the {@link BTree}.
     *
     * @return an iterator over all key-value pairs of the {@link BTree}.
     */
    @Override
    public Iterator<KeyValuePair<TKey, TValue>> iterator()
    {
        return getKeyValuePairs().iterator();
    }

    /**
     * Gets the number of elements contained in this {@link BTree}.
     *
     * @return The number of elements contained in this {@link BTree}.
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
     * Returns the height of this B-tree.
     *
     * @return The height of this B-tree.
     */
    public int height()
    {
        return height;
    }

    /**
     * Returns the string representation of the {@link BTree}. All existing non-leaf entry keys are wrapped with
     * parenthesis on the left. All entries (existing key-value pairs) are on the right. Indent of each key is
     * {@code (this.height - heightOfNode) * 4} spaces.
     *
     * @return The string representation of the {@link BTree}. All existing non-leaf entry keys are wrapped with
     * parenthesis on the left. All entries (existing key-value pairs) are on the right. Indent of each key is
     * {@code (this.height - heightOfNode) * 4} spaces.
     */
    @Override
    public String toString()
    {
        return toString(root, height, "");
    }

    /**
     * Returns the string representation of the given {@link Node}.
     *
     * @param node   The {@link Node}.
     * @param height Height of the current {@link Node}.
     * @param indent Indent (spaces) of the node, it equals {@code (this.height - heightOfNode) * 4} spaces.
     * @return The string representation of the given {@link Node}.
     */

    private String toString(Node node, int height, String indent)
    {
        StringBuilder nodeInfo = new StringBuilder();
        Entry[] children = node.children;

        if (height == 0)
        {
            // Each entry is in a single line. Thus, a line separator is appended at the end.
            // Each key-value pair is represented as "{key} {value}".
            for (int i = 0; i < node.childrenCount; i++)
                nodeInfo.append(indent).append(children[i].key).append(" ").append(children[i].value).append(System.lineSeparator());
        }
        else
        {
            for (int i = 0; i < node.childrenCount; i++)
            {
                // If i > 0, then the key in the entry is a non-leaf entry key, which should be wrapped by a parenthesis.
                if (i > 0)
                    nodeInfo.append(indent).append("(").append(children[i].key).append(")").append(System.lineSeparator());
                nodeInfo.append(toString(children[i].next, height - 1, indent + "    "));
            }
        }

        return nodeInfo.toString();
    }

    /**
     * Determines whether this {@link BTree} contains a specific value.
     *
     * @param keyValuePair The value to locate in this {@link BTree}.
     * @return <code>true</code> if the specified value is found in this {@link BTree}; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean contains(KeyValuePair<TKey, TValue> keyValuePair)
    {
        if (keyValuePair == null)
            throw new NullPointerException("Argument \"keyValuePair\" cannot be null.");

        return containsKeyValue(keyValuePair.getKey(), keyValuePair.getValue());
    }

    /**
     * Copies the elements of this {@link BTree} to an array, starting at index 0.
     *
     * @param array The one-dimensional array that is the destination of the elements copied from this
     *              {@link BTree}. The array must have zero-based indexing.
     * @throws NullPointerException           The given array is null.
     * @throws ArrayIndexOutOfBoundsException Array index is greater than or equal to the length of the array.
     * @throws IllegalArgumentException       The number of elements in the source {@link BTree} is greater than the
     *                                        available space from 0 to the end of the destination array, i.e. the capacity of the given array.
     */
    @Override
    public void copyTo(KeyValuePair<TKey, TValue>[] array)
    {
        copyTo(array, 0);
    }

    /**
     * Copies the elements of this {@link BTree} to an array, starting at a particular array index.
     *
     * @param array      The one-dimensional array that is the destination of the elements copied from this
     *                   {@link BTree}. The array must have zero-based indexing.
     * @param startIndex The zero-based index in array at which copying begins.
     * @throws NullPointerException           The given array is null.
     * @throws ArrayIndexOutOfBoundsException Array index is less than 0 or greater than or equal to the length of
     *                                        the array.
     * @throws IllegalArgumentException       The number of elements in the source {@link BTree} is greater than the
     *                                        available space from <code>startIndex</code> to the end of the destination array.
     */
    @Override
    public void copyTo(KeyValuePair<TKey, TValue>[] array, int startIndex)
    {
        if (array == null)
            throw new NullPointerException("Argument \"array\" cannot be null.");
        if (startIndex < 0)
            throw new ArrayIndexOutOfBoundsException("The start index of an array must be greater than or equal to 0.");
        if (array.length < startIndex + count)
            throw new IllegalArgumentException("The length between the start index and the end of the given array is not enough for copying contents.");

        int i = startIndex;
        for (KeyValuePair<TKey, TValue> kvp : this)
            array[i++] = new KeyValuePair<>(kvp.getKey(), kvp.getValue());
    }
}
