package stark.dataworks.basic.models.distances;

import stark.dataworks.basic.mathematics.Vector;

/**
 * The {@link SamplesDistanceBase} class provides an interface method to calculate the distance between 2 collections of samples.
 */
public abstract class SamplesDistanceBase
{
    /**
     * Returns the distance between 2 collections of samples.
     *
     * @param samples1 A collection of samples.
     * @param samples2 The other collection of samples.
     * @return The distance between 2 collections of samples.
     */
    public abstract double distanceBetween(Vector[] samples1, Vector[] samples2);

    protected void validateParams(Vector[] samples1, Vector[] samples2)
    {
        if (samples1 == null)
            throw new NullPointerException("Argument \"samples1\" cannot be null.");

        if (samples2 == null)
            throw new NullPointerException("Argument \"samples2\" cannot be null.");

        if (samples1.length == 0)
            throw new IllegalArgumentException("Length of \"samples1\" must be a positive integer.");

        if (samples2.length == 0)
            throw new IllegalArgumentException("Length of \"samples2\" must be a positive integer.");

        Vector x = samples1[0];
        for (Vector v : samples1)
        {
            if (v == null)
                throw new NullPointerException("\"null\" is not allowed in \"samples1\".");

            if (v.count() != x.count())
                throw new IllegalArgumentException("All vectors for this method must have the same number of components. (i.e. their count() method return the same value)");
        }

        for (Vector v : samples2)
        {
            if (v == null)
                throw new NullPointerException("\"null\" is not allowed in \"samples2\".");

            if (v.count() != x.count())
                throw new IllegalArgumentException("All vectors for this method must have the same number of components. (i.e. their count() method return the same value)");
        }
    }
}
