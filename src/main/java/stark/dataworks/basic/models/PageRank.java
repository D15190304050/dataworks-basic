package stark.dataworks.basic.models;

import stark.dataworks.basic.mathematics.Matrix;
import stark.dataworks.basic.mathematics.Vector;
import stark.dataworks.basic.models.distances.DistanceMetrics;
import stark.dataworks.basic.mathematics.Mathematics;

import java.util.Objects;

public class PageRank
{
    private Vector rank;
    private final double beta;
    private final Matrix graph;
    private int pageCount;
    private int[] outDegrees;
    private int iterationCount;

    public PageRank(Matrix graph, double beta)
    {
        this.graph = graph;
        this.beta = beta;

        validateBeta();
        validateEntries();
        validateGraph();

        initializePageCountAndOutDegrees();
        run();
    }

    private void validateBeta()
    {
        if (beta < 0 || beta > 1)
            throw new IllegalArgumentException("Argument \"beta\" must be in range [0, 1].");
    }

    private void validateGraph()
    {
        Objects.requireNonNull(graph, "The input graph can not be null.");

        if (!graph.isSquareMatrix())
            throw new IllegalArgumentException("The input graph must be a square matrix.");

        validateColumns();
    }

    private void validateEntries()
    {
        for (int i = 0; i < graph.getRowCount(); i++)
        {
            for (int j = 0; j < graph.getColumnCount(); j++)
            {
                if (!Mathematics.inRange(graph.get(i, j), 0, 1))
                    throw new IllegalArgumentException("Each entry of the web page graph should be in range [0, 1], error position: (" + i + ", " + j + ")");
            }
        }
    }

    private void validateColumns()
    {
        for (int j = 0; j < graph.getColumnCount(); j++)
        {
            double sum = 0;
            for (int i = 0; i < graph.getRowCount(); i++)
                sum += graph.get(i, j);

            if (!Mathematics.equals(1, sum, 0.001))
                throw new IllegalArgumentException("Sum of each column of a web page graph must be 1, error on column: " + j + " (0-based index).");
        }
    }

    private void initializePageCountAndOutDegrees()
    {
        pageCount = graph.getRowCount();
        outDegrees = new int[pageCount];

        for (int j = 0; j < graph.getColumnCount(); j++)
        {
            int outDegree = 0;
            for (int i = 0; i < graph.getRowCount(); i++)
            {
                if (!Mathematics.equals(0, graph.get(i, j)))
                    outDegree++;
            }
            outDegrees[j] = outDegree;
        }
    }

    private void run()
    {
        Vector rank = new Vector(pageCount);
        for (int i = 0; i < pageCount; i++)
            rank.set(i, (double) 1 / pageCount);
        Vector previousRank;

        iterationCount = 0;
        do
        {
            iterationCount++;

            previousRank = rank;
            rank = new Vector(pageCount);

            for (int j = 0; j < pageCount; j++)
            {
                for (int i = 0; i < pageCount; i++)
                {
                    if (graph.get(j, i) > 0)
                        rank.set(j, rank.get(j) + previousRank.get(i) / outDegrees[i]);
                }
            }

            rank = rank.multiply(beta).add((1 - beta) / pageCount);

            // We don't need this following implementation, because:
            // 1. for now, rank.sum() is always 1
            // 2. then 1 - rank.sum() * beta is always 1 - beta
            // 3. thus we only need 1 - beta.
//            rank = rank.multiply(beta);
//            double rankSum = rank.sum();
//            rank = rank.add((1 - rankSum) / pageCount);

            System.out.println("Iteration count: " + iterationCount + ", rank vector: " + rank);
        }
        while (DistanceMetrics.EUCLIDEAN_DISTANCE.distanceBetween(previousRank, rank) > Mathematics.getEpsilon());

        this.rank = rank;
    }

    public Vector getRank()
    {
        return rank;
    }

    public double getBeta()
    {
        return beta;
    }

    public int getIterationCount()
    {
        return iterationCount;
    }
}
