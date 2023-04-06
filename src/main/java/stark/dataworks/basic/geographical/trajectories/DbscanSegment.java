package stark.dataworks.basic.geographical.trajectories;

import stark.dataworks.basic.ArgumentOutOfRangeException;
import stark.dataworks.basic.mathematics.Vector;
import stark.dataworks.basic.models.clustering.Dbscan;
import stark.dataworks.basic.collections.ArrayHelper;
import stark.dataworks.basic.collections.LinkedList;
import stark.dataworks.basic.mathematics.geometry.Point;
import stark.dataworks.basic.models.distances.DistanceMetrics;

// Segments the trajectory to stop and move episodes using DBSCAN.
public class DbscanSegment
{

    private DbscanSegment(){}



    public static Iterable<TrajectorySegment> segment(Trajectory trajectory, int minPoints, double minDistance)
    {
        if (trajectory == null)
            throw new NullPointerException("Argument \"trajectory\" cannot be null.");
        if (minPoints <= 0)
            throw new ArgumentOutOfRangeException("Argument \"minPoints\" cannot be non-positive.");
        if (minDistance <= 0)
            throw new ArgumentOutOfRangeException("Argument \"minDistance\" cannot be non-positive.");

        LinkedList<TrajectorySegment> segments = new LinkedList<>();

        Dbscan dbscan = new Dbscan(minPoints, minDistance);
        dbscan.setDistanceMetric(DistanceMetrics.EUCLIDEAN_DISTANCE);

        SamplePoint[] points = new SamplePoint[trajectory.count()];
        ArrayHelper.copy(trajectory.getPoints(), points, 0, trajectory.count());
        int[] labels = dbscan.fitPredict(Vector.pointsToVectors(points));

        int i = 0;
        while (i < labels.length)
        {
            boolean moving = labels[i] < 0;
            LinkedList<Point> trajectorySegmentPoints = new LinkedList<>();
            trajectorySegmentPoints.addLast(points[i]);
            int j = i + 1;
            while ((j < labels.length) && (labels[j] == labels[i]))
                trajectorySegmentPoints.addLast(points[j++]);
            TrajectorySegment segment = new TrajectorySegment(trajectorySegmentPoints, moving);
            segments.addLast(segment);
            i = j;
        }

        return segments;
    }
}
