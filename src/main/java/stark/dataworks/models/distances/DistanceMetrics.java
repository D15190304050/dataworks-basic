package stark.dataworks.models.distances;

public class DistanceMetrics
{
    private DistanceMetrics(){}

    public final static EuclideanDistance EUCLIDEAN_DISTANCE = new EuclideanDistance();
    public final static ManhattanDistance MANHATTAN_DISTANCE = new ManhattanDistance();
    public final static ChebyshevDistance CHEBYSHEV_DISTANCE = new ChebyshevDistance();
}
