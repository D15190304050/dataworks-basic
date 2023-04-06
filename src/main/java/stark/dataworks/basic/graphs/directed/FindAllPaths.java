package stark.dataworks.basic.graphs.directed;

import stark.dataworks.basic.collections.Stack;
import stark.dataworks.basic.collections.Queue;

import java.io.IOException;

public class FindAllPaths
{
    private int[] edgeTo;
    private boolean[] marked;
    private Queue<Iterable<Integer>> paths;
    private int source;
    private int destination;

    public FindAllPaths(Digraph g, int source, int destination)
    {
        paths = new Queue<>();
        edgeTo = new int[g.vCount()];
        marked = new boolean[g.vCount()];

        for (int v = 0; v < g.vCount(); v++)
            edgeTo[v] = -1;
        edgeTo[source] = source;
        this.source = source;
        this.destination = destination;
        dfs(g, source);
    }

    private void dfs(Digraph g, int v)
    {
        marked[v] = true;
        for (int w : g.adjacent(v))
        {
            edgeTo[w] = v;

            if (w == destination)
            {
                Stack<Integer> path = new Stack<>();
                for (int x = w; x != source; x = edgeTo[x])
                    path.push(x);

                path.push(source);
                paths.enqueue(path);
            }
            else if (!marked[w])
                dfs(g, w);
        }
        marked[v] = false;
    }

    public boolean pathExists()
    {
        return paths.count() > 0;
    }

    public Iterable<Iterable<Integer>> paths()
    {
        return paths;
    }

    public static void main(String[] args) throws IOException
    {
        Digraph g = new Digraph("testData/multipathTestGraph.txt");
        FindAllPaths paths = new FindAllPaths(g, 0, 3);

        if (paths.pathExists())
        {
            for (Iterable<Integer> path : paths.paths())
            {
                System.out.print("Path: ");
                for (int x : path)
                    System.out.print(x + " ");
                System.out.println();
            }
        }
    }
}
