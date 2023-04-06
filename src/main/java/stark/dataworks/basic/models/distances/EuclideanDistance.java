package stark.dataworks.basic.models.distances;

import stark.dataworks.basic.mathematics.Vector;

public class EuclideanDistance extends SampleDistanceBase
{
    public EuclideanDistance()
    {
    }

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

        double sum = 0;
        for (int i = 0; i < sample1.count(); i++)
        {
            double delta = sample1.get(i) - sample2.get(i);
            sum += delta * delta;
        }
        return Math.sqrt(sum);
    }
}
