package stark.dataworks.mathematics.geometry;

import stark.dataworks.mathematics.Random;

public class RandomPointGenerator
{
    private static Random random;

    static
    {
        random = new Random();
    }

    /**
     * This class should not be instantiated.
     */
    private RandomPointGenerator(){}

    public static Point[] circle(double centerX, double centerY, double radius, int numPoints)
    {
        validateRadius(radius);
        validateNumPoints(numPoints);

        Point[] pointsInTheCircle = new Point[numPoints];
        for (int i = 0; i < numPoints; i++)
        {
            double length = random.uniform(radius);
            pointsInTheCircle[i] = nextPoint(centerX, centerY, length);
        }

        return pointsInTheCircle;
    }

    public static Point[] ring(double centerX, double centerY, double minRadius, double maxRadius, int numPoints)
    {
        validateRadius(minRadius, maxRadius);
        validateNumPoints(numPoints);

        Point[] pointsOnTheRing = new Point[numPoints];
        for (int i = 0; i < numPoints; i++)
        {
            double length = random.uniform(minRadius, maxRadius);
            pointsOnTheRing[i] = nextPoint(centerX, centerY, length);
        }

        return pointsOnTheRing;
    }

    private static void validateRadius(double radius)
    {
        if (radius <= 0)
            throw new IllegalArgumentException("Argument \"radius\" must be positive.");
    }


    private static Point nextPoint(double centerX, double centerY, double length)
    {
        double angle = random.uniform(2 * Math.PI);
        double x = centerX + length * Math.cos(angle);
        double y = centerY + length * Math.sin(angle);
        return new Point(x, y);
    }

    private static void validateRadius(double minRadius, double maxRadius)
    {
        if (minRadius <= 0)
            throw new IllegalArgumentException("Argument \"minRadius\" must be positive.");
        if (maxRadius <= 0)
            throw new IllegalArgumentException("Argument \"maxRadius\" must be positive.");
        if (minRadius > maxRadius)
            throw new IllegalArgumentException("Argument \"maxRadius\" must be greater than or equal to parameter \"minRadius\".");
    }

    private static void validateNumPoints(int numPoints)
    {
        if (numPoints <= 0)
            throw new IllegalArgumentException("Argument \"numPoints\" must be positive.");
    }
}
