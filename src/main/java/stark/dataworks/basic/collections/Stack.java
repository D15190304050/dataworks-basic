package stark.dataworks.basic.collections;

import stark.dataworks.basic.DefaultComparer;
import stark.dataworks.basic.InvalidOperationException;

import java.io.Serializable;

public class Stack<T> extends QueueStackBase<T> implements Serializable
{
    public Stack()
    {
        first = null;
        count = 0;
        version = 0;
        comparator = new DefaultComparer<>();
    }

    public Stack(Iterable<T> collection)
    {
        this();

        for (T value : collection)
            push(value);
    }

    public void push(T value)
    {
        Node newFirst = new Node(value);
        newFirst.next = first;
        first = newFirst;
        count++;
        version++;
    }

    public T pop()
    {
        if (first == null)
            throw new InvalidOperationException("This method shouldn't be called on empty Stack.");

        Node oldFirst = first;
        first = first.next;
        T value = oldFirst.value;
        oldFirst.invalidate();

        count--;
        version++;
        return value;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public T peek()
    {
        return first.value;
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
}
