package stark.dataworks.models.distances;

import stark.dataworks.mathematics.Vector;

/**
 * The {@link SampleDistanceBase} interface provides an interface method to calculate the distance between 2 samples.
 */
public abstract class SampleDistanceBase
{
    /**
     * Returns the distance between 2 samples.
     * @param sample1 A sample.
     * @param sample2 The other sample.
     * @return The distance between 2 samples.
     */
    public abstract double distanceBetween(Vector sample1, Vector sample2);

    protected static void validateParams(Vector sample1, Vector sample2)
    {
        Vector.validateVector(sample1);
        Vector.validateVector(sample2);

        if (sample1.count() != sample2.count())
            throw new IllegalArgumentException("All vectors for this method must have the same number of components. (i.e. their count() method return the same value)");
    }
}
