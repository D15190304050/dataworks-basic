package stark.dataworks.basic.mathematics.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import stark.dataworks.basic.mathematics.Mathematics;

import java.util.Objects;

/**
 * The {@link Point} class represents a point in a 2-D surface.
 */
public class Point implements IShape2D
{
    @JsonProperty(value = "X")
    private double x;

    @JsonProperty(value = "Y")
    private double y;

    public Point()
    {
        this(0, 0);
    }

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Point(Point point)
    {
        if (point == null)
            throw new NullPointerException("Argument \"point\" cannot be null.");
        this.x = point.x;
        this.y = point.y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public boolean contains(double x, double y)
    {
        return contains(x, y, Mathematics.getEpsilon());
    }

    public boolean contains(double x, double y, double epsilon)
    {
        Mathematics.validateEpsilon(epsilon);
        return (Math.abs(this.x - x) < epsilon) && (Math.abs(this.y - y) < epsilon);
    }

    /**
     * Parses the {@link String} to an instance of the {@link Point} class.
     * @param pointString A {@link String} like "(x, y)".
     * @return An instance of {@link Point} that equals the given {@link String}.
     */
    public static Point parsePoint(String pointString)
    {
        Objects.requireNonNull(pointString, "Argument \"pointString\" can not be null.");

        // Remove all spaces so that the string should be like "(x,y)".
        pointString = pointString.replace(" ", "");

        // Validate data format.
        // TODO: validate this string by regex.
        if (!pointString.startsWith("("))
            throw new IllegalArgumentException("Error format, \"pointString\" should start with \"(\".");
        if (!pointString.endsWith(")"))
            throw new IllegalArgumentException("Error format, \"pointString\" should end with \")\".");
        if (pointString.split(",").length != 2)
            throw new IllegalArgumentException("Error format, \"pointString\" should contains only 1 \",\".");

        int indexOfLeftParenthesis = pointString.indexOf('(');
        int indexOfComma = pointString.indexOf(',');
        String xString = pointString.substring(indexOfLeftParenthesis + 1, indexOfComma);
        double x = Double.parseDouble(xString);

        int indexOfRightParenthesis = pointString.indexOf(')');
        String yString = pointString.substring(indexOfComma + 1, indexOfRightParenthesis);
        double y = Double.parseDouble(yString);

        return new Point(x, y);
    }

    public double distanceTo(double x, double y)
    {
        double deltaX = this.x - x;
        double deltaY = this.y - y;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Point))
            return false;

        return equals(((Point)obj));
    }

    public boolean equals(Point point2)
    {
        return equals(point2, Mathematics.getEpsilon());
    }

    public boolean equals(Point point2, double epsilon)
    {
        if (point2 == null)
            throw new NullPointerException("Argument \"point2\" cannot be null.");
        Mathematics.validateEpsilon(epsilon);

        return contains(point2.x, point2.y, epsilon);
    }

    @Override
    public String toString()
    {
        return String.format("(%f, %f)", x, y);
    }
}
