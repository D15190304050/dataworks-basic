package stark.dataworks.basic.indices;

import stark.dataworks.basic.collections.KeyValuePair;

public class BinarySearch
{

    private BinarySearch()
    {
    }

    /**
     * Searches a section of an array for a given element using a binary search
     * algorithm. Elements of the array are compared to the search value using
     * the compareTo() method implemented by the type <code>TKey</code>. This method
     * assumes that the array is already sorted; if this is not the case, the
     * result will be incorrect.
     *
     * @param keys   The given array that contains all the candidate keys.
     * @param target The key to locate in the given array.
     * @param <TKey> The type of keys, which implements the {@link Comparable} interface.
     * @return This method returns the index of the given value in the array. If the
     * array does not contain the given value, the method returns a negative
     * integer. The bitwise complement operator (~) can be applied to a
     * negative result to produce the index of the first element (if any) that
     * is larger than the given search value.
     */

    public static <TKey extends Comparable<TKey>> int search(TKey[] keys, TKey target)
    {
        if (keys == null)
            throw new NullPointerException("Argument \"keys\" cannot be null.");
        if (target == null)
            throw new NullPointerException("Argument \"target\" cannot be null.");
        if (keys.length <= 0)
            throw new IllegalArgumentException("Length of the given array \"keys\" must be a positive integer.");

        int low = 0;
        int high = keys.length - 1;

        while (low <= high)
        {
            int middle = (low + high) / 2;
            int comparisonResult = target.compareTo(keys[middle]);

            if (comparisonResult < 0)
                high = middle - 1;
            else if (comparisonResult > 0)
                low = middle + 1;
            else
                return middle;
        }

        return ~low;
    }

    /**
     * Searches a section of an array for a given element using a binary search
     * algorithm. Elements of the array are compared to the search value using
     * the compareTo() method implemented by the type <code>TKey</code>. This method
     * assumes that the array is already sorted; if this is not the case, the
     * result will be incorrect.
     *
     * @param values The given array that contains all the candidate values.
     * @param target The element to locate in the given array.
     * @return This method returns the index of the given value in the array. If the
     * array does not contain the given value, the method returns a negative
     * integer. The bitwise complement operator (~) can be applied to a
     * negative result to produce the index of the first element (if any) that
     * is larger than the given search value.
     */

    public static int search(int[] values, int target)
    {
        if (values == null)
            throw new NullPointerException("Argument \"values\" cannot be null.");
        if (values.length <= 0)
            throw new IllegalArgumentException("Length of the given array \"values\" must be a positive integer.");

        int low = 0;
        int high = values.length - 1;

        while (low <= high)
        {
            int middle = (low + high) / 2;

            if (target < values[middle])
                high = middle - 1;
            else if (target > values[middle])
                low = middle + 1;
            else
                return middle;
        }

        return ~low;
    }

    /**
     * Searches a section of an array for a given element using a binary search
     * algorithm. Elements of the array are compared to the search value using
     * the compareTo() method implemented by the type <code>TKey</code>. This method
     * assumes that the array is already sorted; if this is not the case, the
     * result will be incorrect.
     *
     * @param values The given array that contains all the candidate values.
     * @param target The element to locate in the given array.
     * @return This method returns the index of the given value in the array. If the
     * array does not contain the given value, the method returns a negative
     * integer. The bitwise complement operator (~) can be applied to a
     * negative result to produce the index of the first element (if any) that
     * is larger than the given search value.
     */

    public static int search(double[] values, double target)
    {
        if (values == null)
            throw new NullPointerException("Argument \"values\" cannot be null.");
        if (values.length <= 0)
            throw new IllegalArgumentException("Length of the given array \"values\" must be a positive integer.");

        int low = 0;
        int high = values.length - 1;

        while (low <= high)
        {
            int middle = (low + high) / 2;

            if (target < values[middle])
                high = middle - 1;
            else if (target > values[middle])
                low = middle + 1;
            else
                return middle;
        }

        return ~low;
    }

    /**
     * Searches a section of an array for a given element using a binary search
     * algorithm. Elements of the array are compared to the search value using
     * the compareTo() method implemented by the type <code>TKey</code>. This method
     * assumes that the array is already sorted; if this is not the case, the
     * result will be incorrect.
     *
     * @param values The given array that contains all the candidate values.
     * @param target The element to locate in the given array.
     * @return This method returns the index of the given value in the array. If the
     * array does not contain the given value, the method returns a negative
     * integer. The bitwise complement operator (~) can be applied to a
     * negative result to produce the index of the first element (if any) that
     * is larger than the given search value.
     */

    public static int search(long[] values, long target)
    {
        if (values == null)
            throw new NullPointerException("Argument \"values\" cannot be null.");
        if (values.length <= 0)
            throw new IllegalArgumentException("Length of the given array \"values\" must be a positive integer.");

        int low = 0;
        int high = values.length - 1;

        while (low <= high)
        {
            int middle = (low + high) / 2;

            if (target < values[middle])
                high = middle - 1;
            else if (target > values[middle])
                low = middle + 1;
            else
                return middle;
        }

        return ~low;
    }

    /**
     * Searches a section of an array for a given element using a binary search
     * algorithm. Elements of the array are compared to the search value using
     * the compareTo() method implemented by the type <code>TKey</code>. This method
     * assumes that the array is already sorted; if this is not the case, the
     * result will be incorrect.
     *
     * @param values The given array that contains all the candidate values.
     * @param target The element to locate in the given array.
     * @return This method returns the index of the given value in the array. If the
     * array does not contain the given value, the method returns a negative
     * integer. The bitwise complement operator (~) can be applied to a
     * negative result to produce the index of the first element (if any) that
     * is larger than the given search value.
     */

    public static int search(short[] values, short target)
    {
        if (values == null)
            throw new NullPointerException("Argument \"values\" cannot be null.");
        if (values.length <= 0)
            throw new IllegalArgumentException("Length of the given array \"values\" must be a positive integer.");

        int low = 0;
        int high = values.length - 1;

        while (low <= high)
        {
            int middle = (low + high) / 2;

            if (target < values[middle])
                high = middle - 1;
            else if (target > values[middle])
                low = middle + 1;
            else
                return middle;
        }

        return ~low;
    }

    /**
     * Searches a section of an array for a given element using a binary search
     * algorithm. Elements of the array are compared to the search value using
     * the compareTo() method implemented by the type <code>TKey</code>. This method
     * assumes that the array is already sorted; if this is not the case, the
     * result will be incorrect.
     *
     * @param values The given array that contains all the candidate values.
     * @param target The element to locate in the given array.
     * @return This method returns the index of the given value in the array. If the
     * array does not contain the given value, the method returns a negative
     * integer. The bitwise complement operator (~) can be applied to a
     * negative result to produce the index of the first element (if any) that
     * is larger than the given search value.
     */

    public static int search(float[] values, float target)
    {
        if (values == null)
            throw new NullPointerException("Argument \"values\" cannot be null.");
        if (values.length <= 0)
            throw new IllegalArgumentException("Length of the given array \"values\" must be a positive integer.");

        int low = 0;
        int high = values.length - 1;

        while (low <= high)
        {
            int middle = (low + high) / 2;

            if (target < values[middle])
                high = middle - 1;
            else if (target > values[middle])
                low = middle + 1;
            else
                return middle;
        }

        return ~low;
    }

    /**
     * Searches a section of an array for a given element using a binary search
     * algorithm. Elements of the array are compared to the search value using
     * the compareTo() method implemented by the type <code>TKey</code>. This method
     * assumes that the array is already sorted; if this is not the case, the
     * result will be incorrect.
     *
     * @param values The given array that contains all the candidate values.
     * @param target The element to locate in the given array.
     * @return This method returns the index of the given value in the array. If the
     * array does not contain the given value, the method returns a negative
     * integer. The bitwise complement operator (~) can be applied to a
     * negative result to produce the index of the first element (if any) that
     * is larger than the given search value.
     */

    public static int search(char[] values, char target)
    {
        if (values == null)
            throw new NullPointerException("Argument \"values\" cannot be null.");
        if (values.length <= 0)
            throw new IllegalArgumentException("Length of the given array \"values\" must be a positive integer.");

        int low = 0;
        int high = values.length - 1;

        while (low <= high)
        {
            int middle = (low + high) / 2;

            if (target < values[middle])
                high = middle - 1;
            else if (target > values[middle])
                low = middle + 1;
            else
                return middle;
        }

        return ~low;
    }

    /**
     * Searches a section of an array for a given element using a binary search
     * algorithm. Elements of the array are compared to the search value using
     * the compareTo() method implemented by the type <code>TKey</code>. This method
     * assumes that the array is already sorted; if this is not the case, the
     * result will be incorrect.
     *
     * @param keyValuePairs The given array that contains all the candidate key-value pairs.
     * @param target The element to locate in the given array.
     * @return This method returns the index of the given target in the array. If the
     * array does not contain the given target, the method returns a negative
     * integer. The bitwise complement operator (~) can be applied to a
     * negative result to produce the index of the first element (if any) that
     * is larger than the given search value.
     */

    public static <TKey extends Comparable<TKey>, TValue> int searchForValue(KeyValuePair<TKey, TValue>[] keyValuePairs, TKey target)
    {
        if (keyValuePairs == null)
            throw new NullPointerException("Argument \"keyValuePairs\" cannot be null.");
        if (target == null)
            throw new NullPointerException("Argument \"target\" cannot be null.");
        if (keyValuePairs.length <= 0)
            throw new IllegalArgumentException("Length of the given array \"keyValuePairs\" must be a positive integer.");

        int low = 0;
        int high = keyValuePairs.length - 1;


        while (low <= high)
        {
            int middle = (low + high) / 2;
            int comparisonResult = target.compareTo(keyValuePairs[middle].getKey());

            if (comparisonResult < 0)
                high = middle - 1;
            else if (comparisonResult > 0)
                low = middle + 1;
            else
                return middle;
        }

        return ~low;
    }
}
