package stark.dataworks.basic.collections;

import stark.dataworks.basic.*;
import stark.dataworks.basic.tests.DictionaryTest;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A red-black tree based dictionary.
 */
public class SortedDictionary<TKey extends Comparable<TKey>, TValue> implements IDictionary<TKey, TValue>
{
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node
    {
        public TKey key;
        public TValue value;
        public Node left;
        public Node right;
        public boolean color;
        public int subtreeNodeCount;

        public Node(TKey key, TValue value, boolean color, int subtreeNodeCount)
        {
            this.key = key;
            this.value = value;
            this.color = color;
            this.subtreeNodeCount = subtreeNodeCount;
        }
    }

    /**
     * Root of the red-black tree.
     */
    private Node root;

    private final IComparer<TValue> valueComparator;

    private int count;
    private int version;

    public SortedDictionary()
    {
        root = null;
        count = 0;
        version = 0;
        valueComparator = new DefaultComparer<>();
    }

    /* Node helper methods. */

    /**
     * Gets a value indicating whether the specified node is a red node.
     *
     * @param node The node to test.
     * @return {@code true} if the specified node is red; otherwise, false. It also returns false if the specified node
     * is null.
     */

    private boolean isRed(Node node)
    {
        if (node == null)
            return false;
        return node.color == RED;
    }

    /**
     * Gets the number of subtree nodes rooted at the specified node.
     *
     * @param node The node to calculate.
     * @return Number of node in subtree rooted at the specified node; 0 if the specified is null.
     */
    private int size(Node node)
    {
        if (node == null)
            return 0;
        return node.subtreeNodeCount;
    }

    /**
     * Gets the element associated with the specified key.
     *
     * @param key The key of the element to get.
     * @return The value associated with the specified key.
     * @throws NullPointerException The specified key is null.
     * @throws KeyNotFountException The specified key is not in the {@link IDictionary}.
     */
    @Override
    public TValue get(TKey key)
    {
        Node target = findNode(key);
        if (target != null)
            return target.value;
        else
            throw new KeyNotFountException("The specified key is not in the SortedDictionary.");
    }

    /**
     * Sets the element associated with the specified key.
     *
     * @param key   The key of the element to get.
     * @param value The new value associated with the specified key.
     * @throws NullPointerException The specified key is null.
     * @throws KeyNotFountException The specified key is not in the {@link IDictionary}.
     */
    @Override
    public void set(TKey key, TValue value)
    {
        validateKey(key);
        Node target = findNode(key);
        if (target == null)
            throw new KeyNotFountException("The specified key is not found in the SortedDictionary.");

        target.value = value;
        version++;
    }

    public int height()
    {
        return heightOf(root);
    }

    private int heightOf(Node node)
    {
        if (node == null)
            return 0;
        return 1 + Math.max(heightOf(node.left), heightOf(node.right));
    }

    public TKey minKey()
    {
        return minAt(root).key;
    }

    public TKey maxKey()
    {
        return maxAt(root).key;
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
     * Adds an element with the provided key and value to this {@link IDictionary}.
     *
     * @param key   The object to use as the key of the element to add.
     * @param value The object to use as the value of the element to add, which is associated with the key.
     * @throws NullPointerException     The specified key is null.
     * @throws IllegalArgumentException An element with the same key already exists in this {@link IDictionary}.
     */
    @Override
    public void add(TKey key, TValue value)
    {
        validateKey(key);
        root = add(root, key, value);
        root.color = BLACK;

        count++;
        version++;
    }

    /**
     * Adds an element with the provided key and value to this {@link IDictionary}.
     *
     * @param keyValuePair The object to add with the key and the value.
     * @throws NullPointerException     The specified key value pair or the key contained in the key value pair is null.
     * @throws IllegalArgumentException An element with the same key already exists in this {@link IDictionary}.
     */
    @Override
    public void add(KeyValuePair<TKey, TValue> keyValuePair)
    {
        validateKeyValuePair(keyValuePair);
        add(keyValuePair.getKey(), keyValuePair.getValue());
    }

    /**
     * Determines whether this {@link IDictionary} contains an element with the specified key.
     *
     * @param key The key to locate in this {@link IDictionary}.
     * @return <code>true</code> if this {@link IDictionary} contains an element with the key; otherwise,
     * <code>false</code>.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean containsKey(TKey key)
    {
        return findNode(key) != null;
    }

    /**
     * Determines whether this {@link IDictionary} contains an element with the specified value.
     *
     * @param value The value to locate in this {@link IDictionary}.
     * @return <code>true</code> if this {@link IDictionary} contains an element with the value; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean containsValue(TValue value)
    {
        // Walk through the tree in-order, compare the value in the node with the specified value one-by-one.
        // Using inOrderTreeWalk to implement this.

        AtomicBoolean found = new AtomicBoolean(false);

        if (value == null)
        {
            inOrderTreeWalk(node ->
            {
                if (node.value == null)
                {
                    found.set(true);
                    return false;
                }
                return true;
            }, false);
        }
        else
        {
            inOrderTreeWalk(node ->
            {
                if (valueComparator.compare(node.value, value) == 0)
                {
                    found.set(true);
                    return false;
                }
                return true;
            }, false);
        }

        return found.get();
    }

    /**
     * Determines whether this {@link IDictionary} contains an element with the specified key and value.
     *
     * @param key   The key to locate in this {@link IDictionary}.
     * @param value The value associated with the specified key.
     * @return <code>true</code> if this {@link IDictionary} contains an element with the key and value; otherwise,
     * <code>false</code>.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean containsKeyValue(TKey key, TValue value)
    {
        validateKey(key);

        Node target = findNode(key);
        if (target == null)
            return false;
        return valueComparator.compare(target.value, value) == 0;
    }

    /**
     * Tries to remove a {@link KeyValuePair} with the specified key.
     * Java's generic programming mechanism makes this method have a long name instead just "remove".
     *
     * @param key The key of the element to remove.
     * @return <code>true</code> if the element is successfully removed; otherwise, <code>false</code>. This method
     * also returns <code>false</code> if key was not found in the original {@link IDictionary}.
     * @throws NullPointerException The specified key is null.
     */
    @Override
    public boolean removeByKey(TKey key)
    {
        if (!containsKey(key))
            return false;

        // If both children of root are black, set root to red.
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = remove(root, key);
        if (root != null)
            root.color = BLACK;

        count--;
        version++;
        return true;
    }

    /**
     * Removes the key value pair with the minimum key.
     */
    public void removeMin()
    {
        if (root == null)
            throw new InvalidOperationException("Try to remove element from an empty SortedDictionary.");

        // If both children of root are black, set root to red.
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = removeMin(root);
        if (root != null)
            root.color = BLACK;

        count--;
        version++;
    }

    public KeyValuePair<TKey, TValue> min()
    {
        if (root == null)
            return null;
        Node minNode = minAt(root);
        return new KeyValuePair<>(minNode.key, minNode.value);
    }

    public KeyValuePair<TKey, TValue> max()
    {
        if (root == null)
            return null;
        Node maxNode = maxAt(root);
        return new KeyValuePair<>(maxNode.key, maxNode.value);
    }

    /**
     * Removes the key value pair with the minimum key rooted at {@code node}.
     */

    private Node removeMin(Node node)
    {
        if (node.left == null)
            return null;

        if (!isRed(node.left) && !isRed(node.left.left))
            node = moveRedLeft(node);

        node.left = removeMin(node.left);
        return balance(node);
    }

    /**
     * Removes the key value pair with the maximum key.
     */
    public void removeMax()
    {
        if (root == null)
            throw new InvalidOperationException("Try to remove element from an empty SortedDictionary.");

        // If both children of root are black, set root to red.
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = removeMax(root);
        if (root != null)
            root.color = BLACK;

        count--;
        version++;
    }

    /**
     *
     * */
    private Node removeMax(Node node)
    {
        if (isRed(node.left))
            node = rotateRight(node);

        if (node.right == null)
            return null;

        if (!isRed(node.right) && !isRed(node.right.left))
            node = moveRedRight(node);

        node.right = removeMax(node.right);
        return balance(node);
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
        root = null;
        count = 0;
        version++;
    }

    /**
     * Determines whether this {@link ICollection} contains a specific value.
     *
     * @param keyValuePair The keyValuePair to locate in this {@link ICollection}.
     * @return <code>true</code> if the specified value is found in this {@link ICollection}; otherwise,
     * <code>false</code>.
     */
    @Override
    public boolean contains(KeyValuePair<TKey, TValue> keyValuePair)
    {
        validateKeyValuePair(keyValuePair);
        return containsKeyValue(keyValuePair.getKey(), keyValuePair.getValue());
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
        if (array == null)
            throw new NullPointerException("Argument \"array\" cannot be null.");
        if (startIndex < 0)
            throw new ArrayIndexOutOfBoundsException("The index of an array cannot be less than 0.");
        if ((startIndex > array.length) || (startIndex + count >= array.length))
            throw new ArrayIndexOutOfBoundsException("Not enough capacity for copying items from the SortedDictionary to" +
                    " the given array.");

        AtomicInteger i = new AtomicInteger(startIndex);
        inOrderTreeWalk(node ->
        {
            array[i.get()] = new KeyValuePair<>(node.key, node.value);
            i.set(i.get() + 1);
            return true;
        }, false);
    }

    /**
     * Removes the first occurrence of a specific object from this {@link ICollection}.
     *
     * @param item The object to remove from this {@link ICollection}.
     * @return <code>true</code> if item was successfully removed from the {@link ICollection}; otherwise,
     * <code>false</code>. This method also returns <code>false</code> if item is not found in the original
     * {@link ICollection}.
     */
    @Override
    public boolean remove(KeyValuePair<TKey, TValue> item)
    {
        if (!containsKeyValue(item.getKey(), item.getValue()))
            return false;

        removeByKey(item.getKey());
        count--;
        version++;
        return true;
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

    /**
     * Do an in order walk on tree and calls the delegate for each node.
     * If the action delegate returns false, stop the walk.
     * <p>
     * Returns true if the entire tree has been walked; otherwise, false.
     */
    private boolean inOrderTreeWalk(IPredicate<Node> action, boolean reverse)
    {
        if (root == null)
            return true;

        Stack<Node> stack = new Stack<>();
        Node current = root;
        while (current != null)
        {
            stack.push(current);
            current = (reverse ? current.right : current.left);
        }

        while (stack.count() != 0)
        {
            current = stack.pop();

            if (!action.predicate(current))
                return false;

            Node node = (reverse ? current.left : current.right);
            while (node != null)
            {
                stack.push(node);
                node = (reverse ? node.right : node.left);
            }
        }

        return true;
    }

    /**
     * Finds the node whose key equals the specified key.
     *
     * @return The node whose key equals the specified key; null if not found.
     */
    private Node findNode(TKey key)
    {
        Node current = root;
        while (current != null)
        {
            int compare = key.compareTo(current.key);
            if (compare == 0)
                return current;
            else
                current = (compare < 0) ? current.left : current.right;
        }

        return null;
    }

    /**
     * Inserts the key value pair in the subtree rooted at {@code current}.
     */
    private Node add(Node h, TKey key, TValue value)
    {
        if (h == null)
            return new Node(key, value, RED, 1);

        int compare = key.compareTo(h.key);
        if (compare < 0)
            h.left = add(h.left, key, value);
        else if (compare > 0)
            h.right = add(h.right, key, value);
        else
            throw new IllegalArgumentException("The specified key is already in the SortedDictionary.");

        // Fix-up any right-leaning links.
        if (isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))
            flipColors(h);
        h.subtreeNodeCount = size(h.left) + size(h.right) + 1;

        return h;
    }

    /**
     * Removes the key value pair with the given key rooted at {@code h}.
     */
    private Node remove(Node h, TKey key)
    {
        if (key.compareTo(h.key) < 0)
        {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = remove(h.left, key);
        }
        else
        {
            if (isRed(h.left))
                h = rotateRight(h);

            if ((key.compareTo(h.key) == 0) && (h.right == null))
                return null;

            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);

            if (key.compareTo(h.key) == 0)
            {
                Node minRootedAtNode = minAt(h);
                h.value = minRootedAtNode.value;
                h.key = minRootedAtNode.key;
                h.right = removeMin(h.right);
            }
            else
                h.right = remove(h.right, key);
        }

        return balance(h);
    }


    private Node rotateLeft(Node h)
    {
        assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.subtreeNodeCount = h.subtreeNodeCount;
        h.subtreeNodeCount = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node rotateRight(Node h)
    {
        assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.subtreeNodeCount = h.subtreeNodeCount;
        h.subtreeNodeCount = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * Flips the colors of a node and its 2 children.
     */
    private void flipColors(Node parent)
    {
        // parent must have opposite color of its 2 children.
        assert (parent != null) && (parent.left != null) && (parent.right != null);
        assert (!isRed(parent) && isRed(parent.left) && isRed(parent.right)) || (isRed(parent) && !isRed(parent.left) && !isRed(parent.right));

        parent.color = !parent.color;
        parent.left.color = !parent.left.color;
        parent.right.color = !parent.right.color;
    }

    /**
     * Assuming that {@code node} is red and both {@code node.left} and {@code node.left.left} are black, make
     * {@code node.left} or one of its children red.
     */

    private Node moveRedLeft(Node h)
    {
        assert (h != null);
        assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left))
        {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }

        return h;
    }

    /**
     * Assuming that {@code node} is red and both {@code node.right} and {@code node.right.left} are black, make
     * {@code node.right} or one of its children red.
     */

    private Node moveRedRight(Node h)
    {
        assert (h != null);
        assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);

        flipColors(h);
        if (isRed(h.left.left))
            h = rotateRight(h);

        return h;
    }

    /**
     * Restores red-black tree invariant.
     */
    private Node balance(Node node)
    {
        assert (node != null);

        if (isRed(node.right))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);

        node.subtreeNodeCount = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * Finds and returns the node with the minimum key rooted at the specified node; return null if the specified is
     * null.
     */

    private Node minAt(Node root)
    {
        if (root == null)
            return null;

        Node min = root;
        while (min.left != null)
            min = min.left;

        return min;
    }

    /**
     * Finds and returns the node with the maximum key rooted at the specified node; return null if the specified is
     * null.
     */

    private Node maxAt(Node root)
    {
        if (root == null)
            return null;

        Node max = root;
        while (max.right != null)
            max = max.right;

        return max;
    }


    private void validateKey(TKey key)
    {
        if (key == null)
            throw new NullPointerException("Argument \"key\" cannot be null.");
    }


    private void validateKeyValuePair(KeyValuePair<TKey, TValue> keyValuePair)
    {
        if (keyValuePair == null)
            throw new NullPointerException("Argument \"keyValuePair\" cannot be null.");
    }

    private abstract class CollectionIterator<T> implements Iterator<T>
    {

        private int version;
        private SortedDictionary<TKey, TValue> dictionary;

        protected abstract T getItem(Node node);

        private Iterator<T> iterator;

        private CollectionIterator(SortedDictionary<TKey, TValue> dictionary)
        {
            this.version = dictionary.version;
            this.dictionary = dictionary;
            java.util.LinkedList<T> collection = new LinkedList<>();
            inOrderTreeWalk(node ->
            {
                collection.addLast(getItem(node));
                return true;
            }, false);

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

        public DictionaryIterator(SortedDictionary<TKey, TValue> dictionary)
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
        public KeyIterator(SortedDictionary<TKey, TValue> dictionary)
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
        public ValueIterator(SortedDictionary<TKey, TValue> dictionary)
        {
            super(dictionary);
        }

        @Override
        protected TValue getItem(Node node)
        {
            return node.value;
        }
    }

    public static void main(String[] args) throws IOException
    {
        SortedDictionary<String, Integer> dictionary = new SortedDictionary<>();
        DictionaryTest.runTest(dictionary);
    }
}
