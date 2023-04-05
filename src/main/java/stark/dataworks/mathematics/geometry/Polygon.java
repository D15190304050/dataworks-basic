package stark.dataworks.mathematics.geometry;

import stark.dataworks.collections.LinkedList;

// TODO: Finish implementing this class.

public class Polygon implements IShape2D
{
    private LinkedList<LineSegment> polygon;
    private Point startPoint;

    public Polygon()
    {
        polygon = new LinkedList<>();
        startPoint = null;
    }

    public Polygon(Iterable<Point> points)
    {
        if (points == null)
            throw new NullPointerException("Argument \"points\" cannot be null.");

        polygon = new LinkedList<>();
        startPoint = null;
    }

    public void add(Point point)
    {

    }

    public void add(double x, double y)
    {
        add(new Point(x, y));
    }

    /**
     * For {@link Point}, it tests if they are the same point. For other shape, it tests if the shape contains the
     * specified point.
     *
     * @param x X-coordinate of the point to test.
     * @param y Y-coordinate of the point to test.
     * @return For {@link Point} {@code true} if they are the same point; otherwise, false. For other shape,
     * {@code true} if the shape contains the specified point; otherwise, false.
     * @implNote If the point is on the boundary of a non-point shape, this method returns true.
     */
    @Override
    public boolean contains(double x, double y)
    {
        return false;
    }

    /**
     * For {@link Point}, it returns the distance between them. For line-like shape, it returns the minimum distance
     * between the shape and the point. For closed 2-D shape, it returns the distance between the center of the shape
     * and the point.
     *
     * @param x X-coordinate of the point to calculate the distance.
     * @param y Y-coordinate of the point to calculate the distance.
     * @return For {@link Point}, it returns the distance between them. For line-like shape, it returns the minimum
     * distance between the shape and the point. For closed 2-D shape, it returns the distance between the center of the
     * shape and the point.
     */
    @Override
    public double distanceTo(double x, double y)
    {
        return 0;
    }
}
