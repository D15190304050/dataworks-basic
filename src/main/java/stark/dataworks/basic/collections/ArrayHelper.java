package stark.dataworks.basic.collections;

import stark.dataworks.basic.IComparer;

public class ArrayHelper
{
    public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE;


    private ArrayHelper()
    {
    }


    public static <T> void copy(T[] source, int sourceStartIndex, T[] destination, int destinationStartIndex, int count)
    {
        System.arraycopy(source, sourceStartIndex, destination, destinationStartIndex, count);
    }


    public static <T> void copy(ICollection<T> source, T[] destination, int destinationStartIndex, int count)
    {
        if (source == null)
            throw new NullPointerException("Argument \"source\" is null.");

        if (destination == null)
            throw new NullPointerException("Argument \"destination\" is null.");

        if (count < 0)
            throw new IllegalArgumentException("Argument \"count\" must be a non-negative integer.");

        if (source.count() < count)
            throw new IllegalArgumentException("Argument \"source\" does not contain enough elements. " + "(Got: " + source.count() + ", expected: " + count + ")");

        if (destination.length - destinationStartIndex < count)
            throw new IllegalArgumentException("Argument \"destination\" does not have enough room. (destination" +
                                               ".length - destinationStartIndex < count)");

        doCopy(source, destination, destinationStartIndex, count);
    }

    // TODO: try to combine this copy() and the copy() above.
    public static <T> void copy(Iterable<T> source, T[] destination, int destinationStartIndex, int count)
    {
        if (source == null)
            throw new NullPointerException("source is null.");

        if (destination == null)
            throw new NullPointerException("destination is null.");

        if (count < 0)
            throw new IllegalArgumentException("Argument \"count\" must be a non-negative integer.");

        int sourceCount = 0;
        for (T ignored : source)
            sourceCount++;

        if (sourceCount < count)
            throw new IllegalArgumentException("source does not contain enough elements. " + "(Got: " + sourceCount + ", expected: " + count + ")");

        if (destination.length - destinationStartIndex < count)
            throw new IllegalArgumentException("Argument \"destination\" does not have enough room. (destination" +
                                               ".length - destinationStartIndex < count)");

        doCopy(source, destination, destinationStartIndex, count);
    }

    private static <T> void doCopy(Iterable<T> source, T[] destination, int destinationStartIndex, int count)
    {
        int i = 0;
        for (T value : source)
        {
            destination[destinationStartIndex + i] = value;
            i++;

            if (i == count)
                break;
        }
    }

    /**
     * Searches a section of an array for a given element using a binary search algorithm. Elements of the array are
     * compared to the search value using the given IComparer interface. If comparer is null, elements of the array are
     * compared to the search value using the IComparable interface, which in that case must be implemented by all
     * elements of the array and the given search value. This method assumes that the array is already sorted; if this
     * is not the case, the result will be incorrect.
     * <p/>
     * The method returns the index of the given value in the array. If the array does not contain the given value, the
     * method returns a negative integer. The bitwise complement operator (~) can be applied to a negative result to
     * produce the index of the first element (if any) that is larger than the given search value.
     *
     * @param array
     * @param startIndex
     * @param length
     * @param value
     * @param comparer
     * @param <T>
     * @return
     */
    public static <T> int binarySearch(T[] array, int startIndex, int length, Object value, IComparer comparer)
    {
        if (array == null)
            throw new NullPointerException("array is null.");

        int low = startIndex;
        int high = startIndex + length - 1;

        while (low <= high)
        {

        }

        return 0;
    }
}
