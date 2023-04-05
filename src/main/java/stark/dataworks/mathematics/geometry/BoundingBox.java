package stark.dataworks.mathematics.geometry;

import stark.dataworks.mathematics.Mathematics;

/**
 * The {@link BoundingBox} class represents a rectangle whose boundaries align the axes of the 2-D surface.
 */
public class BoundingBox implements IShape2D
{
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;


    public BoundingBox(double minX, double minY, double maxX, double maxY)
    {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }


    public BoundingBox(Point[] points)
    {
        if (points == null)
            throw new NullPointerException("Argument \"points\" cannot be null.");

        if (points.length == 0)
            throw new IllegalArgumentException("Argument \"points\" has no elements (i.e., its length is 0).");

        minX = minY = Double.MAX_VALUE;
        maxX = maxY = -Double.MAX_VALUE;
        for (Point point : points)
        {
            double x = point.getX();
            double y = point.getY();

            if (x < minX)
                minX = x;
            else if (x > maxX)
                maxX = x;

            if (y < minY)
                minY = y;
            else if (y > maxY)
                maxY = y;
        }
    }

    /**
     * Initializes an instance of the {@link BoundingBox} class that represents the minimum bounding box that can contain all the given points.
     *
     * @param points A collection of points.
     */

    public BoundingBox(Iterable<Point> points)
    {
        if (points == null)
            throw new NullPointerException("Argument \"points\" cannot be null.");

        minX = minY = Double.MAX_VALUE;
        maxX = maxY = -Double.MAX_VALUE;
        for (Point point : points)
        {
            double x = point.getX();
            double y = point.getY();

            if (x < minX)
                minX = x;
            else if (x > maxX)
                maxX = x;

            if (y < minY)
                minY = y;
            else if (y > maxY)
                maxY = y;
        }
    }

    public double getMinX()
    {
        return minX;
    }

    public void setMinX(double minX)
    {
        this.minX = minX;
    }

    public double getMinY()
    {
        return minY;
    }

    public void setMinY(double minY)
    {
        this.minY = minY;
    }

    public double getMaxX()
    {
        return maxX;
    }

    public void setMaxX(double maxX)
    {
        this.maxX = maxX;
    }

    public double getMaxY()
    {
        return maxY;
    }

    public void setMaxY(double maxY)
    {
        this.maxY = maxY;
    }

    public double getWidth()
    {
        return maxX - minX;
    }

    public double getHeight()
    {
        return maxY - minY;
    }

    public Point getTopLeft()
    {
        return new Point(minX, minY);
    }

    public Point getTopRight()
    {
        return new Point(maxX, minY);
    }

    public Point getBottomLeft()
    {
        return new Point(minX, maxY);
    }

    public Point getBottomRight()
    {
        return new Point(maxX, maxY);
    }

    public boolean contains(double x, double y)
    {
        return minX <= x && x <= maxX && minY <= y && y <= maxY;
    }

    @Override
    public double distanceTo(double x, double y)
    {
        double centerX = (minX + maxX) / 2;
        double centerY = (minY + maxY) / 2;
        double deltaX = x - centerX;
        double deltaY = y - centerY;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }


    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof BoundingBox))
            return false;

        return equals(((BoundingBox) obj));
    }

    public boolean equals(BoundingBox box)
    {
        return equals(box, Mathematics.getEpsilon());
    }

    public boolean equals(BoundingBox box, double epsilon)
    {
        if (box == null)
            throw new NullPointerException("Argument \"box\" is null.");
        Mathematics.validateEpsilon(epsilon);

        return (Math.abs(this.minX - box.minX) < epsilon) &&
               (Math.abs(this.maxX - box.maxX) < epsilon) &&
               (Math.abs(this.minY - box.minY) < epsilon) &&
               (Math.abs(this.maxY - box.maxY) < epsilon);
    }
}