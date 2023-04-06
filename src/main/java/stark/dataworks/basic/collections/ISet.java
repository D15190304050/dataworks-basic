package stark.dataworks.basic.collections;

/**
 * Provides the base interface for the abstraction of sets.
 * @param <T> The type of elements in the set.
 * */
public interface ISet<T> extends ICollection<T>
{
    /**
     * Adds an element to the current set and returns a value to indicate if the element was successfully added.
     * @param item The element to add to the set.
     * @return {@code true} if the element is added to the set; {@code false} if the element is already in the set.
     * */
    boolean add(T item);

    /**
     * Modifies the current set so that it contains all elements that are present in the current set, in the specified
     * collection, or in both.
     * @param other The collection of items to remove from the set.
     * @exception NullPointerException {@code other} is null.
     * */
    void unionWith(Iterable<T> other);

    /**
     * Modifies the current set so that it contains only elements that are also in a specified collection.
     * @param other The collection to compare to the current set.
     * @exception NullPointerException {@code other} is null.
     * */
    void intersectWith(Iterable<T> other);

    /**
     * Removes all elements in the specified collection from the current set.
     * @param other The collection of items to remove from the set.
     * @exception NullPointerException {@code other} is null.
     * */
    void exceptWith(Iterable<T> other);

    /**
     * Modifies the current set so that it contains only elements that are present either in the current set or in the
     * specified collection, but not both.
     * @param other The collection to compare to the current set.
     * @exception NullPointerException {@code other} is null.
     * */
    void symmetricExceptWith(Iterable<T> other);

    /**
     * Determines whether a set is a subset of a specified collection.
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is a proper subset of other; otherwise, {@code false}.
     * @exception NullPointerException {@code other} is null.
     * */
    boolean isSubsetOf(Iterable<T> other);

    /**
     * Determines whether a set is a superset of a specified collection.
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is a proper superset of other; otherwise, {@code false}.
     * @exception NullPointerException {@code other} is null.
     * */
    boolean isSupersetOf(Iterable<T> other);

    /**
     * Determines whether the current set is a proper (strict) superset of a specified collection.
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is a proper superset of other; otherwise, {@code false}.
     * @exception NullPointerException {@code other} is null.
     * */
    boolean isProperSupersetOf(Iterable<T> other);

    /**
     * Determines whether the current set is a proper (strict) subset of a specified collection.
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is a proper subset of other; otherwise, {@code false}.
     * @exception NullPointerException {@code other} is null.
     * */
    boolean isProperSubsetOf(Iterable<T> other);

    /**
     * Determines whether the current set overlaps with the specified collection.
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set and other share at least one common element; otherwise, {@code false}.
     * @exception NullPointerException {@code other} is null.
     * */
    boolean overlaps(Iterable<T> other);

    /**
     * Determines whether the current set and the specified collection contain the same elements.
     * @param other The collection to compare to the current set.
     * @return {@code true} if the current set is equal to other; otherwise, {@code false}.
     * @exception NullPointerException {@code other} is null.
     * */
    boolean setEquals(Iterable<T> other);
}
