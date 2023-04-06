package stark.dataworks.basic;

/**
 * The IComparer interface implements a method that compares 2 objects. It used in conjunction with the sort() and
 * binarySearch() methods on the array and list classes.
 */
public interface IComparer<T>
{
    /**
     * Compares 2 objects. An implementation of this method must return a value less than 0 if x is less than y, 0 if x
     * is equal to y, or a value greater than 0 if x is greater than y.
     *
     * @param x An object to compare.
     * @param y The other object to compare.
     * @return An int value, which is less than 0 if x is less than y, 0 if x is equal to y, or a value greater than 0 if x is
     * greater than y.
     */
    int compare(T x, T y);
}
