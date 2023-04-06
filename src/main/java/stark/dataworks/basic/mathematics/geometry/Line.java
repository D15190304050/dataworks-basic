package stark.dataworks.basic.mathematics.geometry;

import stark.dataworks.basic.mathematics.Mathematics;

import java.util.Objects;

/**
 * The {@link Line} class represents a straight line in a 2-D surface whose equation is Ax + By + C = 0.
 */
public class Line implements IShape2D
{
    private double a;
    private double b;
    private double c;


    public Line()
    {
        this(1, 0, 0);
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @implSpec If the equation of the line is "0x + 0y + C = 0", then it is not a line.
     */

    public Line(double a, double b, double c)
    {
        validateArguments(a, b);

        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Initializes a new instance of the {@link Line} class across 2 given points.
     *
     * @param point1 A point that this line cross.
     * @param point2 Another point that this line crosses.
     * @implSpec If |point1.getX() - point2.getX()| < {@link Mathematics#getEpsilon()}, then the program assumes the line is perpendicular to the x-axis.
     */

    public Line(Point point1, Point point2)
    {
        this(point1, point2, Mathematics.getEpsilon());
    }

    /**
     * Initializes a new instance of the {@link Line} class across 2 given points.
     *
     * @param point1  A point that this line cross.
     * @param point2  Another point that this line crosses.
     * @param epsilon Assuming x and y are 2 double values. If |x - y| < `epsilon`, then x and y are equal in the context.
     * @implSpec If |point1.getX() - point2.getX()| < `epsilon`, then the program assumes the line is perpendicular to the x-axis.
     */
    public Line(Point point1, Point point2, double epsilon)
    {
        if (point1 == null)
            throw new NullPointerException("Argument \"point1\" cannot be null.");
        if (point2 == null)
            throw new NullPointerException("Argument \"point2\" cannot be null.");
        Mathematics.validateEpsilon(epsilon);

        // If point1 and point2 have the same y (or there difference are close to 0), than we can just have line equation as if-branch.
        // Otherwise, we can obtain the line equation using code in else-branch.
        if (Math.abs(point1.getX() - point2.getX()) < epsilon)
        {
            this.a = 1;
            this.b = 0;
            this.c = -point1.getX();
        }
        else
        {
            this.a = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
            this.b = -1;
            this.c = point1.getY() - this.a * point1.getX();
        }
    }

    public static void validateArguments(double a, double b)
    {
        if ((a == 0) && (b == 0))
            throw new IllegalArgumentException("Arguments \"a\" and \"b\" can not both be 0.");
    }

    public double getA()
    {
        return a;
    }

    public void setA(double a)
    {
        validateArguments(a, b);
        this.a = a;
    }

    public double getB()
    {
        return b;
    }

    public void setB(double b)
    {
        validateArguments(a, b);
        this.b = b;
    }

    public double getC()
    {
        return c;
    }

    public void setC(double c)
    {
        this.c = c;
    }

    /**
     * Returns {@code true} if the given {@link Line} (or {@link LineSegment}) instance is parallel to this line; otherwise, {@code false}.
     *
     * @param line2 The other line (or line segment).
     * @return {@code true} if the given {@link Line} (or {@link LineSegment}) instance is parallel to this line; otherwise, {@code false}.
     * @implSpec Assuming 2 lines are parallel if the difference of their slope is less than {@link Mathematics#getEpsilon()}.
     */
    public final boolean isParallelTo(Line line2)
    {
        return isParallelTo(line2, Mathematics.getEpsilon());
    }

    /**
     * Returns {@code true} if the given {@link Line} (or {@link LineSegment}) instance is parallel to this line; otherwise, {@code false}.
     *
     * @param line2   The other line (or line segment).
     * @param epsilon Assuming x and y are 2 double values. If |x - y| < `epsilon`, then x and y are equal in the context.
     * @return {@code true} if the given {@link Line} (or {@link LineSegment}) instance is parallel to this line; otherwise, {@code false}.
     * @implSpec Assuming 2 lines are parallel if the difference of their slope is less than `epsilon`.
     */
    public final boolean isParallelTo(Line line2, double epsilon)
    {
        if (line2 == null)
            throw new NullPointerException("Argument \"line2\" cannot be null.");
        Mathematics.validateEpsilon(epsilon);

        // Return false if they are the same line.
        if (this.equals(line2))
            return false;

        // Return true if they are both perpendicular to the x-axis.
        if ((this.b == 0) && (line2.b == 0))
            return true;

        // If the program reaches here, then at least one of the 2 lines is not perpendicular to the x-axis.
        // Thus, if there is a line perpendicular to the x-axis, then they are not parallel.
        if ((this.b == 0) && (line2.b != 0))
            return false;
        if ((this.b != 0) && (line2.b == 0))
            return false;

        // Otherwise, if the absolute value of the difference of their slope is less than `epsilon`, they are parallel to each other.
        double slope1 = -this.a / this.b;
        double slope2 = -line2.a / line2.b;
        return Math.abs(slope1 - slope2) < epsilon;
    }

    /**
     * Returns {@code true} if the given {@link Line} or {@link LineSegment} instance is perpendicular to this line; otherwise, {@code false};
     *
     * @param line2 The other line (or line segment).
     * @return {@code true} if the given {@link Line} or {@link LineSegment} instance is perpendicular to this line; otherwise, {@code false};
     * @implSpec Assuming 2 lines are perpendicular if the result of<p>
     * {@code A1A2 + B1B2 <} {@link Mathematics#getEpsilon()}.
     */
    public final boolean isPerpendicularTo(Line line2)
    {
        return isPerpendicularTo(line2, Mathematics.getEpsilon());
    }

    /**
     * Returns {@code true} if the given {@link Line} or {@link LineSegment} instance is perpendicular to this line; otherwise, {@code false};
     *
     * @param line2   The other line (or line segment).
     * @param epsilon Assuming x and y are 2 double values. If |x - y| < `epsilon`, then x and y are equal in the context.
     * @return {@code true} if the given {@link Line} or {@link LineSegment} instance is perpendicular to this line; otherwise, {@code false};
     * @implSpec Assuming 2 lines are perpendicular if the result of<p>
     * {@code A1A2 + B1B2 <} 'epsilon'.
     */
    public final boolean isPerpendicularTo(Line line2, double epsilon)
    {
        if (line2 == null)
            throw new NullPointerException("Argument \"line2\" cannot be null.");
        Mathematics.validateEpsilon(epsilon);

        // Line1 is perpendicular to line2 => A1A2 + B1B2 = 0.
        return Math.abs(this.a * line2.a + this.b * line2.b) < epsilon;
    }

    /**
     * Returns {@code true} if this line contains the given point; otherwise, {@code false}.
     *
     * @param x X-coordinate of the given point.
     * @param y Y-coordinate of the given point.
     * @return {@code true} if this line contains the given point; otherwise, {@code false}.
     * @implSpec This implementation assumes the line contains the point if<p>
     * {@code A * x + B * y + C < }{@link Mathematics#getEpsilon()}.
     */
    public boolean contains(double x, double y)
    {
        return contains(x, y, Mathematics.getEpsilon());
    }

    /**
     * Returns {@code true} if this line contains the given point; otherwise, {@code false}.
     *
     * @param x       X-coordinate of the given point.
     * @param y       Y-coordinate of the given point.
     * @param epsilon Assuming x and y are 2 double values. If |x - y| < `epsilon`, then x and y are equal in the context.
     * @return {@code true} if this line contains the given point; otherwise, {@code false}.
     * @implSpec This implementation assumes the line contains the point if<p>
     * {@code A * x + B * y + C < } `epsilon`.
     */
    public boolean contains(double x, double y, double epsilon)
    {
        // Here we calculate the result of the line equation.
        // And we think they are equal if the result is less than epsilon.
        Mathematics.validateEpsilon(epsilon);
        double delta = Math.abs(this.a * x + this.b * y + this.c);
        return delta < epsilon;
    }

    /**
     * Returns the distance between the specified point and this line.
     *
     * @param x X-coordinate of the specified point.
     * @param y Y-coordinate of the specified point.
     * @return The distance between the specified point and this line.
     */
    public double distanceTo(double x, double y)
    {
        return Math.abs((a * x + b * y + c) / Math.sqrt(a * a + b * b));
    }

    /**
     * Returns the intersection between this line and the given line, null if they have the same slope (or their slope are close).
     *
     * @param line2 Another line.
     * @return The intersection between this line and the given line, null if they have the same slope (or their slope are close).
     */
    public Point getIntersectionWith(Line line2)
    {
        if (line2 == null)
            throw new NullPointerException("Argument \"line2\" cannot be null.");

        // This implementation uses linear algebra formulas.
        // denominator here is just the determinant of the coefficient matrix.

        double a = this.a;
        double b = this.b;
        double c = line2.a;
        double d = line2.b;
        double e = -this.c;
        double f = -line2.c;

        double denominator = a * d - b * c;
        if (Math.abs(denominator) < Double.MIN_VALUE)
            return null;

        double x = (e * d - b * f) / denominator;
        double y = (a * f - e * c) / denominator;
        return new Point(x, y);
    }

    /**
     * Returns the string representation of this line, i.e. the standard form equation of this line.
     *
     * @return The string representation of this line, i.e. the standard form equation of this line.
     */
    @Override
    public String toString()
    {
        return String.format("%f * x + %f * y + %f = 0", a, b, c);
    }

    // TODO: Rewrite the equals() method and the hashCode() method.
    // The default implementation is not a good one.

    public boolean equals(Line line2)
    {
        return equals(line2, Mathematics.getEpsilon());
    }

    public boolean equals(Line line2, double epsilon)
    {
        if (line2 == null)
            return false;

        if (this == line2)
            return true;

        if ((this.b != 0) && (line2.b == 0))
            return false;

        if ((this.b == 0) && (line2.b != 0))
            return false;

        if ((this.b == 0) && (line2.b == 0))
        {
//            double a1 = 1;
            double c1 = this.c / this.a;
//            double a2 = 1;
            double c2 = line2.c / line2.a;
            return Math.abs(c1 - c2) < epsilon;
        }
        else
        {
            double a1 = this.a / this.b;
//            double b1 = 1;
            double c1 = this.c / this.b;

            double a2 = line2.a / line2.b;
//            double b2 = 1;
            double c2 = line2.c / line2.b;

            return (Math.abs(a1 - a2) < epsilon) && (Math.abs(c1 - c2) < epsilon);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Line))
            return false;
        Line line = (Line) o;
        return equals(line);
    }

    @Override
    public int hashCode()
    {
        if (this.b == 0)
            return Objects.hash(1, 0, c / a);
        else
            return Objects.hash(a / b, 1, c / b);
    }
}
