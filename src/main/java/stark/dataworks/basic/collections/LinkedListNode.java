package stark.dataworks.basic.collections;

/**
 * The {@link LinkedListNode} class represents a node of {@link LinkedList} that contains a value of T and links to
 * previous and next node.
 * */
public final class LinkedListNode<T>
{
    /**
     * Value contained in this {@link LinkedListNode}.
     * */
    private T value;

    /**
     * Next {@link LinkedListNode} of this {@link LinkedListNode}.
     * */
    LinkedListNode<T> next;

    /**
     * Previous {@link LinkedListNode} of this {@link LinkedListNode}.
     * */
    LinkedListNode<T> previous;

    /**
     * The {@link LinkedList} that this {@link LinkedListNode} belongs to.
     * */
    LinkedList<T> list;

    /**
     * Initializes a new instance of {@link LinkedListNode} with specified value.
     * @param value The specified value contained in this {@link LinkedListNode}.
     * */

    public LinkedListNode(T value)
    {
        this.value = value;
        this.list = null;
        this.next = null;
        this.previous = null;
    }

    /**
     * Initializes a new instance of {@link LinkedListNode} with specified value and associate it with the specified
     * {@link LinkedList} that contains this {@link LinkedListNode}.
     * @param list The specified {@link LinkedList} that contains this {@link LinkedListNode}.
     * @param value The specified value contained in this {@link LinkedListNode}.
     * */

    LinkedListNode(LinkedList<T> list, T value)
    {
        this.value = value;
        this.list = list;
        this.next = null;
        this.previous = null;
    }

    /**
     * Gets the {@link LinkedList} that contains this {@link LinkedListNode}.
     * @return The {@link LinkedList} that contains this {@link LinkedListNode}.
     * */

    public LinkedList<T> getLinkedList()
    {
        return list;
    }

    /**
     * Gets next {@link LinkedListNode} of this {@link LinkedListNode}.
     * @return Next {@link LinkedListNode} of this {@link LinkedListNode}.
     * */

    public LinkedListNode<T> getNext()
    {
        return next;
    }

    /**
     * Gets previous {@link LinkedListNode} of this {@link LinkedListNode}.
     * @return Previous {@link LinkedListNode} of this {@link LinkedListNode}.
     * */

    public LinkedListNode<T> getPrevious()
    {
        return previous;
    }

    /**
     * Gets the value contained in this {@link LinkedListNode}.
     * @return The value contained in this {@link LinkedListNode}.
     * */

    public T getValue()
    {
        return value;
    }

    /**
     * Invalidates this node so that this node can be destruct by GC.
     * */
    void invalidate()
    {
        this.list = null;
        this.next = null;
        this.previous = null;
    }
}
