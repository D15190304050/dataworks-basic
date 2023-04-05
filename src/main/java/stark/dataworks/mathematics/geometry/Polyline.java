package stark.dataworks.mathematics.geometry;

import stark.dataworks.collections.*;

public class Polyline implements IShape2D
{
    private final LinkedList<LineSegment> polyline;
    private Point startPoint;
    private Point endPoint;

    public Polyline()
    {
        polyline = new LinkedList<>();
        startPoint = null;
        endPoint = null;
    }

    public Point getStartPoint()
    {
        return startPoint;
    }

    public void setStartPoint(Point startPoint)
    {
        addStart(startPoint);
    }

    public Point getEndPoint()
    {
        return endPoint;
    }

    public void setEndPoint(Point endPoint)
    {
        addEnd(endPoint);
    }

    /**
     * Returns the number of line segments in this poly line.
     * @return The number of line segments in this poly line.
     */
    public int count()
    {
        return polyline.count();
    }

    public void addEnd(double x, double y)
    {
        addEnd(new Point(x, y));
    }

    public void addEnd(Point point)
    {
        if (startPoint == null)
        {
            startPoint = point;
            endPoint = point;
        }
        else
        {
            LineSegment newLineSegment = new LineSegment(endPoint, point);
            polyline.addLast(newLineSegment);
            endPoint = point;
        }
    }

    public void addStart(double x, double y)
    {
        addStart(new Point(x, y));
    }

    public void addStart(Point point)
    {
        if (startPoint == null)
        {
            startPoint = point;
            endPoint = point;
        }
        else
        {
            LineSegment newLineSegment = new LineSegment(point, startPoint);
            polyline.addFirst(newLineSegment);
            startPoint = point;
        }
    }

    public void removeStart()
    {
        if (startPoint == null)
            return;

        startPoint = polyline.getFirst().getValue().other(startPoint);
        polyline.removeFirst();
    }

    public void removeEnd()
    {
        if (endPoint == null)
            return;

        endPoint = polyline.getLast().getValue().other(endPoint);
        polyline.removeLast();
    }

    public boolean contains(double x, double y)
    {
        for (LineSegment lineSegment : polyline)
        {
            if (lineSegment.contains(x, y))
                return true;
        }

        return false;
    }

    /**
     * Returns the minimum distance between the specified point and this poly line.
     * @param x X-coordinate of the specified point.
     * @param y Y-coordinate of the specified point.
     * @return The minimum distance between the specified point and this poly line.
     */
    @Override
    public double distanceTo(double x, double y)
    {
        double minDistance = Double.MAX_VALUE;
        for (LineSegment lineSegment : polyline)
        {
            double currentDistance = lineSegment.distanceTo(x, y);
            if (currentDistance < minDistance)
                minDistance = currentDistance;
        }

        return minDistance;
    }

    public Iterable<Point> getIntersectionsWith(Line line)
    {
        LinkedList<Point> intersections = new LinkedList<>();
        for (LineSegment lineSegment : polyline)
        {
            Point intersection = lineSegment.getIntersectionWith(line);
            if (intersection != null)
                intersections.addLast(intersection);
        }

        return intersections;
    }

    public Iterable<Point> getIntersectionsWith(LineSegment line)
    {
        if (line == null)
            throw new NullPointerException("Argument \"line\" cannot be null.");

        LinkedList<Point> intersections = new LinkedList<>();
        for (LineSegment lineSegment : polyline)
        {
            Point intersection = lineSegment.getIntersectionWith(line);
            if (intersection != null)
                intersections.addLast(intersection);
        }

        return intersections;
    }

    public Iterable<Point> getIntersectionsWith(BoundingBox box)
    {
        if (box == null)
            throw new NullPointerException("Argument \"box\" cannot be null.");

        LinkedList<Point> intersections = new LinkedList<>();

        Point topLeft = box.getTopLeft();
        Point topRight = box.getTopRight();
        Point bottomLeft = box.getBottomLeft();
        Point bottomRight = box.getBottomRight();

        LineSegment[] boxLineSegments = new LineSegment[4];
        boxLineSegments[0] = new LineSegment(topLeft, topRight);
        boxLineSegments[1] = new LineSegment(topLeft, bottomLeft);
        boxLineSegments[2] = new LineSegment(bottomLeft, bottomRight);
        boxLineSegments[3] = new LineSegment(topRight, bottomRight);

        for (LineSegment lineSegment1 : polyline)
        {
            for (LineSegment lineSegment2 : boxLineSegments)
            {
                Point intersection = lineSegment1.getIntersectionWith(lineSegment2);
                if (intersection != null)
                    intersections.addLast(intersection);
            }
        }

        return intersections;
    }

    public Iterable<LineSegment> getLineSegments()
    {
        return polyline;
    }

    public Iterable<Point> getPoints()
    {
        LinkedList<Point> points = new LinkedList<>();
        points.addLast(getStartPoint());
        for (LineSegment lineSegment : polyline)
            points.addLast(lineSegment.getEndPoint2());
        return points;
    }
}
