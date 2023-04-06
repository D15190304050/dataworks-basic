package stark.dataworks.basic.mathematics.geometry;

public class Circle implements IShape2D
{
    private double centerX;
    private double centerY;
    private double radius;


    public Circle()
    {
        this(0, 0, 1);
    }


    public Circle(double centerX, double centerY, double radius)
    {
        validateRadius(radius);

        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public double getCenterX()
    {
        return centerX;
    }

    public void setCenterX(double centerX)
    {
        this.centerX = centerX;
    }

    public double getCenterY()
    {
        return centerY;
    }

    public void setCenterY(double centerY)
    {
        this.centerY = centerY;
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(double radius)
    {
        validateRadius(radius);
        this.radius = radius;
    }

    public boolean contains(double x, double y)
    {
        double distanceToCenterSquare = distanceToCenterSquare(x, y);
        return distanceToCenterSquare <= radius * radius;
    }


    private double distanceToCenterSquare(double x, double y)
    {
        double deltaX = x - centerX;
        double deltaY = y - centerY;
        return deltaX * deltaX + deltaY * deltaY;
    }

    private static void validateRadius(double radius)
    {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius of a circle must be a positive value.");
    }

    /**
     * Returns the distance between the specified point and the center of this circle.
     * @param x X-coordinate of the specified point.
     * @param y Y-coordinate of the specified point.
     * @return The distance between the specified point and the center of this circle.
     */
    @Override
    public double distanceTo(double x, double y)
    {
        return Math.sqrt(distanceToCenterSquare(x, y));
    }
}
