package stark.dataworks.collections;

public interface IList<T> extends ICollection<T>
{
    T get(int index);
    void set(int index, T value);

    int indexOf(T value);
}
