package stark.dataworks.mathematics.geometry;

import stark.dataworks.mathematics.*;

/**
 * The {@link LineSegment} class represents a line segment with given end point in a 2-D surface.
 */
public class LineSegment extends Line
{
    /**
     * An end point of this line segment.
     */
    private Point endPoint1;

    /**
     * The other end point of this line segment.
     */
    private Point endPoint2;

    /**
     * The min value that x can take.
     */
    private double minX;

    /**
     * The min value that y can take.
     */
    private double minY;

    /**
     * The max value that x can take.
     */
    private double maxX;

    /**
     * The max value that y can take.
     */
    private double maxY;

    /**
     * Initializes a new instance of the LineSegment2D class with given end points.
     *
     * @param endPoint1 An end point of this line segment.
     * @param endPoint2 The other end point of this line segment.
     */
    public LineSegment(Point endPoint1, Point endPoint2)
    {
        super(endPoint1, endPoint2);

        // Save 2 end points.
        this.endPoint1 = endPoint1;
        this.endPoint2 = endPoint2;

        // Get the range of x on this line segment.
        resetXyRanges();
    }

    public Point getEndPoint1()
    {
        return endPoint1;
    }

    public void setEndPoint1(Point endPoint1)
    {
        this.endPoint1 = endPoint1;
        resetXyRanges();
    }

    public Point getEndPoint2()
    {
        return endPoint2;
    }

    public void setEndPoint2(Point endPoint2)
    {
        this.endPoint2 = endPoint2;
        resetXyRanges();
    }

    private void resetXyRanges()
    {
        // Get the range of x on this line segment.
        if (endPoint1.getX() < endPoint2.getX())
        {
            minX = endPoint1.getX();
            maxX = endPoint2.getX();
        }
        else
        {
            minX = endPoint2.getX();
            maxX = endPoint1.getX();
        }

        // Get the range of y on this line segment.
        if (endPoint1.getY() < endPoint2.getY())
        {
            minY = endPoint1.getY();
            maxY = endPoint2.getY();
        }
        else
        {
            minY = endPoint2.getY();
            maxY = endPoint1.getY();
        }
    }

    public Point other(Point point)
    {
        if (point == null)
            throw new NullPointerException("Argument \"point\" cannot be null.");

        if ((point == endPoint1) || point.equals(endPoint1))
            return endPoint2;
        else if ((point == endPoint2) || point.equals(endPoint2))
            return endPoint1;
        else
            throw new IllegalArgumentException("The given point " +
                                               point +
                                               " is not an end point of this line segment.");
    }

    /**
     * Gets the intersection between this line segment and the given line, null if they don't have one.
     *
     * @param line2 The line.
     * @return The intersection between this line and the given line, null if they don't have one.
     */
    @Override
    public Point getIntersectionWith(Line line2)
    {
        // Try to get the intersection between the line containing this line segment and the given line.
        Point intersection = super.getIntersectionWith(line2);

        // Return null if they don't have a intersection.
        if (intersection == null)
            return null;

        // Return the intersection if its x and y are in the range, otherwise, null.
        if ((minX <= intersection.getX()) &&
            (intersection.getX() <= maxX) &&
            (minY <= intersection.getY()) &&
            (intersection.getY() <= maxY))
            return intersection;
        else
            return null;
    }

    /**
     * Gets the intersection between this line segment and the given line segment, null if the don't have one.
     *
     * @param line2 The other line segment.
     * @return The intersection between this line segment and the given line segment, null if the don't have one.
     */
    public Point getIntersectionWith(LineSegment line2)
    {
        // Try to get the intersection between the line containing this line segment and the line containing the given line segment.
        Point intersection = super.getIntersectionWith(line2);

        // Return null if they don't have a intersection.
        if (intersection == null)
            return null;

        // Return the intersection if its x and y are in the range, otherwise, null.
        if ((this.minX <= intersection.getX()) &&
            (intersection.getX() <= this.maxX) &&
            (this.minY <= intersection.getY()) &&
            (intersection.getY() <= this.maxY) &&
            (line2.minX <= intersection.getX()) &&
            (intersection.getX() <= line2.maxX) &&
            (line2.minY <= intersection.getY()) &&
            (intersection.getY() <= line2.maxY))
            return intersection;
        else
            return null;
    }

    /**
     * Returns true if this line segment contains the given point; otherwise, false.
     *
     * @param x X-coordinate of the given point.
     * @param y Y-coordinate of the given point.
     * @return {@code true} if this line contains the given point; otherwise, {@code false}.
     */
    @Override
    public boolean contains(double x, double y)
    {
        // Check range first, this will save some time.
        if ((minX <= x) && (x <= maxX) && (minY <= y) && (y <= maxY))
            return super.contains(x, y);
        else
            return false;
    }

    @Override
    public double distanceTo(double x, double y)
    {
        double distanceToLine = super.distanceTo(x, y);
        double distanceToEndPoint1 = endPoint1.distanceTo(x, y);
        double distanceToEndPoint2 = endPoint2.distanceTo(x, y);

        return Mathematics.min(distanceToLine, distanceToEndPoint1, distanceToEndPoint2);
    }

    /**
     * Returns the string representation of this line segment, i.e. the standard form equation of the line containing this line segment and 2 end points.
     *
     * @return the string representation of this line segment, i.e. the standard form equation of the line containing this line segment and 2 end points.
     */
    @Override
    public String toString()
    {
        return super.toString() + " with end point " + endPoint1 + " and " + endPoint2;
    }
}
