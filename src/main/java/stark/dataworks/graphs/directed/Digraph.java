package stark.dataworks.graphs.directed;

import stark.dataworks.ArgumentOutOfRangeException;
import stark.dataworks.collections.LinkedList;
import stark.dataworks.collections.Stack;
import stark.dataworks.io.File;

import java.io.IOException;
import java.io.Serializable;

public class Digraph implements Serializable
{
    /**
     * Number of vertices in this digraph.
     */
    private int vertexCount;

    /**
     * Number of edges in this digraph.
     */
    private int edgeCount;

    private LinkedList<Integer>[] adjacent;

    private int[] inDegree;

    public int vCount()
    {
        return vertexCount;
    }

    public int eCount()
    {
        return edgeCount;
    }

    /**
     * Initialize an empty digraph with vCount vertices.
     *
     * @param vertexCount
     */
    public Digraph(int vertexCount)
    {
        if (vertexCount < 0)
            throw new IllegalArgumentException("Number of vertices in a digraph must be non-negative.");

        this.vertexCount = vertexCount;
        this.edgeCount = 0;
        inDegree = new int[vertexCount];
        adjacent = new LinkedList[vertexCount];
        for (int v = 0; v < vertexCount; v++)
            adjacent[v] = new LinkedList<>();
    }

    /**
     * Initialize a digraph from the specified file.
     *
     * @param filePath
     * @throws IOException
     */
    public Digraph(String filePath) throws IOException
    {
        // Read complete content from file.
        String contents = File.readAllText(filePath);

        // Split content into individual strings.
        String[] numberString = contents.split("\\s+");

        // Convert number string into numbers.
        int[] numbers = new int[numberString.length];
        for (int i = 0; i < numberString.length; i++)
            numbers[i] = Integer.parseInt(numberString[i]);

        // Get V and E from numbers[].
        vertexCount = numbers[0];
        edgeCount = numbers[1];

        // Validate vCount and eCount.
        if ((vertexCount < 0) || (edgeCount < 0))
            throw new IllegalArgumentException("Number of vertices or edges must be non-negative.");

        // Create arrays of inDegree and adjacency lists.
        inDegree = new int[vertexCount];
        adjacent = new LinkedList[vertexCount];

        // Initialize all adjacency lists to empty.
        for (int v = 0; v < vertexCount; v++)
            adjacent[v] = new LinkedList<>();

        // Add edges from numbers[].
        for (int e = 1; e <= edgeCount; e++)
            adjacent[numbers[2 * e]].addFirst(numbers[2 * e + 1]);
    }

    /**
     * Initialize a new digraph that is a deep copy of the specified digraph.
     *
     * @param g The digraph to copy.
     */
    public Digraph(Digraph g)
    {
        this(g.vertexCount);

        edgeCount = g.edgeCount;
        for (int v = 0; v < g.vertexCount; v++)
            inDegree[v] = g.inDegree[v];

        for (int v = 0; v < g.vertexCount; v++)
        {
            Stack<Integer> reverse = new Stack<>();
            for (int w : g.adjacent(v))
                reverse.push(w);
            for (int w : reverse)
                adjacent[v].addFirst(w);
        }
    }

    /**
     * Returns vertices adjacent from vertex v in this digraph.
     *
     * @param v The vertex.
     * @return Vertices adjacent from vertex v in this digraph.
     */
    public Iterable<Integer> adjacent(int v)
    {
        validateVertex(v);
        return adjacent[v];
    }

    /**
     * Throw an ArgumentOutOfRangeException unless 0 &lt;= v &lt; vCount.
     *
     * @param v The vertex.
     */
    private void validateVertex(int v)
    {
        if (v < 0 || v >= vertexCount)
            throw new ArgumentOutOfRangeException(String.format("Vertex %d is not between 0 and %d.", v, vertexCount));
    }

    /**
     * Adds the directed edge v->w to this digraph.
     * @param v The source vertex.
     * @param w The target vertex.
     */
    public void addEdge(int v, int w)
    {
        validateVertex(v);
        validateVertex(w);
        adjacent[v].addFirst(w);
        inDegree[w]++;
        edgeCount++;
    }

    /**
     * Returns the number of directed edges incident from vertex v.
     * @param v The vertex.
     * @return The number of directed edges incident from vertex v.
     */
    public int outDegree(int v)
    {
        validateVertex(v);
        return adjacent[v].count();
    }

    /**
     * Returns the number of directed edges incident to vertex v.
     * @param v The vertex.
     * @return The number of directed edges incident to vertex v.
     */
    public int inDegree(int v)
    {
        validateVertex(v);
        return inDegree[v];
    }

    /**
     * Returns the reversion of this digraph.
     * @return The reversion of this digraph.
     */
    public Digraph reverse()
    {
        Digraph reverse = new Digraph(vertexCount);
        for (int v = 0; v < vertexCount; v++)
        {
            for (int w : adjacent[v])
                reverse.addEdge(w, v);
        }
        return reverse;
    }

    /**
     * Returns a string representation of the digraph.
     * @return The number of vertices V, followed by the number of edges E, followed by the V adjacency lists.
     */
    @Override
    public String toString()
    {
        StringBuilder digraph = new StringBuilder();
        digraph.append(vertexCount);
        digraph.append(" vertices ");
        digraph.append(edgeCount);
        digraph.append(" edges");
        digraph.append(System.lineSeparator());
        for (int v = 0; v < vertexCount; v++)
        {
            digraph.append(v);
            digraph.append(": ");
            for (int w : adjacent[v])
            {
                digraph.append(w);
                digraph.append(" ");
            }
            digraph.append(System.lineSeparator());
        }

        return digraph.toString();
    }
}
