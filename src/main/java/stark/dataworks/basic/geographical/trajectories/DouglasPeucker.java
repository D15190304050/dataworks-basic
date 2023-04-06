package stark.dataworks.basic.geographical.trajectories;

import stark.dataworks.basic.mathematics.geometry.Point;
import stark.dataworks.basic.mathematics.geometry.Line;

// TODO: Unify the API of DouglasPeucker algorithm and DbscanSegment.
public class DouglasPeucker
{
    private Point[] points;
    private double maxDistance;
    private int[] labels;
    private int directionCount;


    public DouglasPeucker(Iterable<Point> points, double maxDistance)
    {
        if (points == null)
            throw new NullPointerException("Argument \"points\" is null.");
        if (maxDistance <= 0)
            throw new IllegalArgumentException("Argument \"maxDistance\" must be a positive integer.");

        directionCount = 0;
        this.maxDistance = maxDistance;

        int pointCount = 0;
        for (Point ignored : points)
            pointCount++;

        this.points = new Point[pointCount];
        int i = 0;
        for (Point p : points)
            this.points[i++] = p;

        labels = new int[pointCount];

        compress(0, pointCount - 1);
    }

    private void compress(int start, int end)
    {
        Line line = new Line(points[start], points[end]);
        boolean canCompress = true;

        int i;
        for (i = start + 1; i < end; i++)
        {
            if (line.distanceTo(points[i]) > maxDistance)
            {
                canCompress = false;
                break;
            }
        }

        if (canCompress)
        {
            for (i = start; i <= end; i++)
                labels[i] = directionCount;
            directionCount++;
        }
        else
        {
            compress(start, i);
            compress(i + 1, end);
        }
    }
}
