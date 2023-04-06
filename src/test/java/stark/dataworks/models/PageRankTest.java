package stark.dataworks.models;

import stark.dataworks.basic.mathematics.Matrix;
import stark.dataworks.basic.mathematics.Vector;
import org.junit.Before;
import org.junit.Test;
import stark.dataworks.basic.models.PageRank;

public class PageRankTest
{
    private Matrix graph;

    @Before
    public void setUp() throws Exception
    {
        Vector[] vectors = new Vector[3];
        vectors[0] = new Vector(0.5, 0.5, 0);
        vectors[1] = new Vector(0.5, 0, 0);
        vectors[2] = new Vector(0, 0.5, 1);
        graph = new Matrix(vectors, true);

        System.out.println("Graph to test:");
        System.out.println(graph);
    }

    @Test
    public void getRank()
    {
        PageRank pageRank = new PageRank(graph, 0.8);
        Vector rank = pageRank.getRank();
        System.out.println(rank);
    }
}