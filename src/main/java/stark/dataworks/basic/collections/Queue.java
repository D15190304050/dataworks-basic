package stark.dataworks.basic.collections;

import stark.dataworks.basic.InvalidOperationException;

import java.io.Serializable;

public class Queue<T> extends QueueStackBase<T> implements Serializable
{
    private Node last;

    public Queue()
    {
        super();
        last = null;
    }

    public Queue(Iterable<T> collection)
    {
        this();

        for (T value : collection)
            enqueue(value);
    }

    public void enqueue(T value)
    {
        if (first == null)
        {
            first = new Node(value);
            last = first;
        }
        else
        {
            Node newLast = new Node(value);
            last.next = newLast;
            last = newLast;
        }

        count++;
        version++;
    }

    public T dequeue()
    {
        if (first == null)
            throw new InvalidOperationException("This method shouldn't be called on empty Queue.");

        T value = first.value;
        if (first == last)
        {
            first = null;
            last = null;
        }
        else
            first = first.next;

        count--;
        version++;
        return value;
    }

    /**
     * Returns {@code true} if the {@link Queue} is read only; otherwise, {@code false}.
     *
     * @return {@code true} if the {@link Queue} is read only; otherwise, {@code false}.
     */
    @Override
    public boolean isReadOnly()
    {
        return false;
    }
}
