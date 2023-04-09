package stark.dataworks.basic.collections;

import stark.dataworks.basic.IComparer;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Collections
{
    private Collections()
    {
    }

    /**
     * Returns a {@link Map} mapping each unique element in the given
     * {@link ICollection} to an {@link Integer} representing the number
     * of occurrences of that element in the {@link ICollection}.
     * <p>
     * Only those elements present in the collection will appear as
     * keys in the map.
     *
     * @param collection the collection to get the cardinality map for, must not be null
     * @return the populated cardinality map
     */
    public static <T> Map<T, Integer> getCardinalityMap(Iterable<T> collection)
    {
        Map<T, Integer> cardinalityMap = new HashMap<>();
        if (collection != null)
        {
            for (T object : collection)
                cardinalityMap.merge(object, 1, Integer::sum);
        }
        return cardinalityMap;
    }

    public static <T> Dictionary<T, Integer> getCardinalityDictionary(Iterable<T> collection)
    {
        Dictionary<T, Integer> cardinalityDictionary = new Dictionary<>();
        if (collection != null)
        {
            for (T object : collection)
                cardinalityDictionary.merge(object, 1, Integer::sum);
        }
        return cardinalityDictionary;
    }

    private static <T> int getFrequency(T object, Map<T, Integer> frequencyMap)
    {
        Integer count = frequencyMap.get(object);
        return count != null ? count : 0;
    }

    private static <T> int getFrequency(T key, IDictionary<T, Integer> frequencyDictionary)
    {
        Integer count = frequencyDictionary.get(key);
        return count != null ? count : 0;
    }

    /**
     * Returns <tt>true</tt> iff the given {@link ICollection}s contain
     * exactly the same elements with exactly the same cardinalities.
     * <p>
     * That is, iff the cardinality of <i>e</i> in <i>a</i> is
     * equal to the cardinality of <i>e</i> in <i>b</i>,
     * for each element <i>e</i> in <i>a</i> or <i>b</i>.
     *
     * @param a the first collection.
     * @param b the second collection.
     * @return <code>true</code> iff the collections contain the same elements with the same cardinalities.
     */
    public static <T> boolean areEqual(Iterable<T> a, Iterable<T> b)
    {
        if (a == null)
            return false;
        if (b == null)
            return false;

        Map<T, Integer> cardinalityMapA = getCardinalityMap(a);
        Map<T, Integer> cardinalityMapB = getCardinalityMap(b);
        if (cardinalityMapA.size() != cardinalityMapB.size())
            return false;
        else
        {
            for (T key : cardinalityMapA.keySet())
            {
                if (getFrequency(key, cardinalityMapA) != getFrequency(key, cardinalityMapB))
                    return false;
            }
            return true;
        }
    }

    public static <T> boolean areEqual(Iterable<T> a, Iterable<T> b, IComparer<T> comparer)
    {
        if (a == null)
            return false;
        if (b == null)
            return false;

        Dictionary<T, Integer> cardinalityDictionaryA = getCardinalityDictionary(a);
        Dictionary<T, Integer> cardinalityDictionaryB = getCardinalityDictionary(b);

        if (cardinalityDictionaryA.count() != cardinalityDictionaryB.count())
            return false;
        else
        {
            for (T key : cardinalityDictionaryA.keys())
            {
                if (getFrequency(key, cardinalityDictionaryA) != getFrequency(key, cardinalityDictionaryB))
                    return false;
            }
            return true;
        }
    }

    public static <T> boolean isEmpty(Iterable<T> collection)
    {
        return (collection != null) && (collection.iterator().hasNext());
    }

    public static <T> String join(Iterable<T> collection, String delimiter)
    {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (T value : collection)
            stringJoiner.add(String.valueOf(value));

        return stringJoiner.toString();
    }
}
