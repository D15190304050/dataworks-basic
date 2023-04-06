package stark.dataworks.basic.models.clustering;

import stark.dataworks.basic.ArgumentOutOfRangeException;
import stark.dataworks.basic.models.IDataScienceModel;
import stark.dataworks.basic.collections.LinkedList;
import stark.dataworks.basic.mathematics.Vector;
import stark.dataworks.basic.models.distances.SampleDistanceBase;

import java.io.Serializable;

public class Dbscan implements IDataScienceModel, Serializable
{
    private int minPoints;
    private double minDistance;
    private LinkedList<Vector> coreSamples;
    private int[] labels;
    private boolean[] marked;
    private int clusterCount;
    private SampleDistanceBase distanceMetric;

    // TODO: try to figure out a better design that combines constructor and necessary parameters.
    public Dbscan(int minPoints, double minDistance)
    {
        if (minPoints <= 0)
            throw new ArgumentOutOfRangeException("Argument \"minPoints\" cannot be non-positive.");
        if (minDistance <= 0)
            throw new ArgumentOutOfRangeException("Argument \"minDistance\" cannot be non-positive.");

        this.minPoints = minPoints;
        this.minDistance = minDistance;

        coreSamples = null;
        labels = null;
        clusterCount = 0;
    }

    public int getClusterCount()
    {
        return clusterCount;
    }

    public int getMinPoints()
    {
        return minPoints;
    }

    public void setMinPoints(int minPoints)
    {
        if (minPoints <= 0)
            throw new ArgumentOutOfRangeException("Argument \"minPoints\" cannot be non-positive.");

        this.minPoints = minPoints;
    }

    public SampleDistanceBase getDistanceMetric()
    {
        return distanceMetric;
    }

    public void setDistanceMetric(SampleDistanceBase distanceMetric)
    {
        this.distanceMetric = distanceMetric;
    }

    public double getMinDistance()
    {
        return minDistance;
    }

    public void setMinDistance(double minDistance)
    {
        if (minDistance <= 0)
            throw new ArgumentOutOfRangeException("Argument \"minDistance\" cannot be non-positive.");

        this.minDistance = minDistance;
    }

    public void fit(Vector[] samples)
    {

    }

    public int[] fitPredict(Vector[] samples)
    {
        validateParameters();
        validateSamples(samples);

        clusterCount = 0;

        // Initialize labels.
        int sampleCount = samples.length;
        labels = new int[samples.length];
        for (int i = 0; i < sampleCount; i++)
            labels[i] = -1;

        // Initialize core point markers.
        marked = new boolean[sampleCount];
        for (int i = 0; i < sampleCount; i++)
            marked[i] = false;

        // Initialize the collection of core samples.
        coreSamples = new LinkedList<>();

        for (int i = 0; i < sampleCount; i++)
        {
            if (labels[i] == -1)
            {
                LinkedList<Integer> neighborIndices = getNeighbors(samples, i);
                if (neighborIndices.count() >= minPoints)
                {
                    setNeighborId(samples, i, neighborIndices, clusterCount);
                    clusterCount++;
                }
            }
        }

        return labels;
    }

    private LinkedList<Integer> getNeighbors(Vector[] samples, int sampleIndex)
    {
        LinkedList<Integer> neighborIndices = new LinkedList<>();
        for (int j = 0; j < samples.length; j++)
        {
            if ((sampleIndex != j) && (distanceMetric.distanceBetween(samples[sampleIndex], samples[j]) < minDistance))
                neighborIndices.addLast(j);
        }

        return neighborIndices;
    }

    private void setNeighborId(Vector[] samples, int coreIndex, LinkedList<Integer> neighborIndices, int clusterId)
    {
        if (marked[coreIndex])
            return;

        marked[coreIndex] = true;
        coreSamples.addLast(samples[coreIndex]);
        setNeighborId(neighborIndices, coreIndex, clusterId);
        for (int j : neighborIndices)
        {
            LinkedList<Integer> neighborIndices2 = getNeighbors(samples, j);
            if (neighborIndices2.count() >= minPoints)
                setNeighborId(samples, j, neighborIndices2, clusterId);
        }
    }

    private void setNeighborId(LinkedList<Integer> sampleIndices, int coreIndex, int clusterId)
    {
        labels[coreIndex] = clusterId;
        for (int j : sampleIndices)
            labels[j] = clusterId;
    }

    @Override
    public void save(String modelPath)
    {

    }

    @Override
    public void restore(String modelPath)
    {

    }

    private static void validateSamples(Vector[] samples)
    {
        // Validate parameter "samples".
        if (samples == null)
            throw new NullPointerException("Argument \"samples\" cannot be null.");

        int sampleCount = samples.length;
        if (sampleCount == 0)
            throw new IllegalArgumentException("Argument \"samples\" cannot be an empty array (with length 0).");

        Vector sample1 = samples[0];
        for (int i = 1; i < sampleCount; i++)
        {
            Vector v = samples[i];
            if (v == null)
                throw new NullPointerException("Entry of \"samples\" with index " + i + " is null.");

            if (sample1.count() != v.count())
                throw new IllegalArgumentException("Every Vector in \"samples\" must have the same length.");
        }
    }

    private void validateParameters()
    {
        if (distanceMetric == null)
            throw new NullPointerException("Distance metric cannot be null.");
    }
}
