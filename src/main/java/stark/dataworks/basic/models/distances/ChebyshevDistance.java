package stark.dataworks.basic.models.distances;

import stark.dataworks.basic.mathematics.Vector;

public class ChebyshevDistance extends SampleDistanceBase
{
    public ChebyshevDistance(){}

    /**
     * Returns the distance between 2 samples.
     *
     * @param sample1 A sample.
     * @param sample2 The other sample.
     * @return The distance between 2 samples.
     */
    @Override
    public double distanceBetween(Vector sample1, Vector sample2)
    {
        validateParams(sample1, sample2);

        double maxDelta = Double.MIN_VALUE;
        for (int i = 0; i < sample1.count(); i++)
        {
            double delta = sample1.get(i) - sample2.get(i);
            if (maxDelta < delta)
                maxDelta = delta;
        }

        return maxDelta;
    }
}
