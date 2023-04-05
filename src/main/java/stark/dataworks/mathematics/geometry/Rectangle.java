package stark.dataworks.mathematics.geometry;

import stark.dataworks.mathematics.Mathematics;
import stark.dataworks.mathematics.Vector;

public class Rectangle implements IShape2D
{
    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;

    private LineSegment lineSegment14;
    private LineSegment lineSegment23;
    private LineSegment lineSegment12;
    private LineSegment lineSegment34;

    public Rectangle(Point point1, Point point2, Point point3, Point point4)
    {
        if (point1 == null)
            throw new NullPointerException("Argument \"point1\" cannot be null.");
        if (point2 == null)
            throw new NullPointerException("Argument \"point2\" cannot be null.");
        if (point3 == null)
            throw new NullPointerException("Argument \"point3\" cannot be null.");
        if (point4 == null)
            throw new NullPointerException("Argument \"point4\" cannot be null.");

        // Make a deep copy of 4 points to ensure that the Rectangle class is immutable.
        // If we use a reference assignment here, the caller can still change the coordinates of points.
        this.point1 = new Point(point1);
        this.point2 = new Point(point2);
        this.point3 = new Point(point3);
        this.point4 = new Point(point4);

        // Calibrate point orders, make them counterclockwise.
        calibratePointOrders();

        // Initialize 4 line segments after calibrating point orders.
        initializeLineSegments();

        // Determine if the given points can form a rectangle.
        if (!isRectangle())
            throw new IllegalArgumentException("The given 4 points can not form a rectangle.");
    }

    // Getters of 4 points call the Point(Point) method to ensure the caller doesn't change coordinates of these points.

    public Point getPoint1()
    {
        return new Point(point1);
    }

    public Point getPoint2()
    {
        return new Point(point2);
    }

    public Point getPoint3()
    {
        return new Point(point3);
    }

    public Point getPoint4()
    {
        return new Point(point4);
    }

    private void calibratePointOrders()
    {
        double distanceP0ToP1 = point1.distanceTo(point2);
        double distanceP0ToP2 = point1.distanceTo(point3);
        double distanceP0ToP3 = point1.distanceTo(point4);

        // Swap point references, make sure that point3 is the furthest point to point1.
        if ((distanceP0ToP1 > distanceP0ToP2) && (distanceP0ToP1 > distanceP0ToP3))
        {
            Point swap = point2;
            point2 = point3;
            point3 = swap;
        }
        else if ((distanceP0ToP3 > distanceP0ToP1) && (distanceP0ToP3 > distanceP0ToP2))
        {
            Point swap = point4;
            point4 = point3;
            point3 = swap;
        }

        // Swap point references, make sure that point1->point2->point3->point4 is counterclockwise (ccw).
        // At this moment, we know that point3 is the furthest point to point1.
        // We determine references of point2 and point4 by a vector cross product.
        // See doc for more information.
        Vector p1ToP2 = new Vector(point2.getX() - point1.getX(), point2.getY() - point1.getY(), 0);
        Vector p1ToP4 = new Vector(point4.getX() - point1.getX(), point4.getY() - point1.getY(), 0);
        Vector crossProduct = p1ToP2.cross(p1ToP4);
        double z = crossProduct.get(2);
        if (z < 0)
        {
            Point swap = point2;
            point2 = point4;
            point4 = swap;
        }

        // When the program reaches here, point1->point2->point3->point4 is counterclockwise (ccw).
    }

    private void initializeLineSegments()
    {
        lineSegment14 = new LineSegment(point1, point4);
        lineSegment23 = new LineSegment(point2, point3);

        lineSegment12 = new LineSegment(point1, point2);
        lineSegment34 = new LineSegment(point3, point4);
    }

    private boolean isRectangle()
    {
//        boolean parallel1 = lineSegment14.isParallelTo(lineSegment23);
//        boolean parallel2 = lineSegment12.isParallelTo(lineSegment34);
//        boolean perpendicular = lineSegment14.isPerpendicularTo(lineSegment12);
//        return parallel1 && parallel2 && perpendicular;
        return lineSegment14.isParallelTo(lineSegment23) &&
               lineSegment12.isParallelTo(lineSegment34) &&
               lineSegment14.isPerpendicularTo(lineSegment12);
    }

    private static boolean boundingBoxContains(double x, double y, double x1, double x2, double y1, double y2)
    {
        double minX = Math.min(x1, x2);
        double maxX = Math.max(x1, x2);
        double minY = Math.min(y1, y2);
        double maxY = Math.max(y1, y2);

        return (minX <= x) && (x <= maxX) && (minY <= y) && (y <= maxY);
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
        // Case 1: This is a bounding box.
        //     Case 1-1 (if):
        //         lineSegment14 and lineSegment23 are perpendicular to the x-axis => B = 0
        //         lineSegment12 and lineSegment34 are parallel to the x-axis => A = 0.
        //     Case 1-2 (else if):
        //         lineSegment12 and lineSegment34 are perpendicular to the x-axis => B = 0.
        //         lineSegment14 and lineSegment23 are parallel to the x-axis => A = 0
        //     Case 2 (else): This is not a bounding box.
        //         See doc for more details.
        // TODO: Complement doc for 3 cases listed here.

        if ((Math.abs(lineSegment14.getB()) < Mathematics.getEpsilon()) &&
            (Math.abs(lineSegment23.getB()) < Mathematics.getEpsilon()) &&
            (Math.abs(lineSegment12.getA()) < Mathematics.getEpsilon()) &&
            (Math.abs(lineSegment34.getA()) < Mathematics.getEpsilon()))
        {
            // Since lineSegment14 and lineSegment 23 are perpendicular to the x-axis, we can assume all points of lineSegment14 have the same x, which equals point1.getX().
            // For the same reason, we have the following equations.
            // For more details, see doc.
            // TODO: Complement doc for these equations.

            double x1 = point1.getX();
            double x2 = point2.getX();
            double y1 = point1.getY();
            double y2 = point4.getY();

            return boundingBoxContains(x, y, x1, x2, y1, y2);
        }
        else if ((Math.abs(lineSegment12.getB()) < Mathematics.getEpsilon()) &&
                 (Math.abs(lineSegment34.getB()) < Mathematics.getEpsilon()) &&
                 (Math.abs(lineSegment14.getA()) < Mathematics.getEpsilon()) &&
                 (Math.abs(lineSegment23.getA()) < Mathematics.getEpsilon()))
        {
            double x1 = point1.getX();
            double x2 = point4.getX();
            double y1 = point1.getY();
            double y2 = point2.getY();

            return boundingBoxContains(x, y, x1, x2, y1, y2);
        }
        else
        {
            // For more details, see doc.
            // TODO: Complement doc for these equations.

            double y1 = -(lineSegment14.getA() * x + lineSegment14.getC()) / lineSegment14.getB();
            double y2 = -(lineSegment23.getA() * x + lineSegment23.getC()) / lineSegment23.getB();
            double y3 = -(lineSegment12.getA() * x + lineSegment12.getC()) / lineSegment12.getB();
            double y4 = -(lineSegment34.getA() * x + lineSegment34.getC()) / lineSegment34.getB();

            double minY1 = Math.min(y1, y2);
            double maxY1 = Math.max(y1, y2);
            double minY2 = Math.min(y3, y4);
            double maxY2 = Math.max(y3, y4);

            return (minY1 <= y) && (y <= maxY1) && (minY2 <= y) && (y <= maxY2);
        }
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

    @Override
    public String toString()
    {
        return "Rectangle{ " +
               "point1=" + point1 +
               ", point2=" + point2 +
               ", point3=" + point3 +
               ", point4=" + point4 +
               ", lineSegment14=" + lineSegment14 +
               ", lineSegment23=" + lineSegment23 +
               ", lineSegment12=" + lineSegment12 +
               ", lineSegment34=" + lineSegment34 +
               " }";
    }
}
