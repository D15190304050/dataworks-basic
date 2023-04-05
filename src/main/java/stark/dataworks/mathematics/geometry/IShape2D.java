package stark.dataworks.mathematics.geometry;

public interface IShape2D
{
    /**
     * For {@link Point}, it tests if they are the same point. For other shape, it tests if the shape contains the
     * specified point.
     *
     * @param point The point to test.
     * @return For {@link Point} {@code true} if they are the same point; otherwise, false. For other shape,
     * {@code true} if the shape contains the specified point; otherwise, false.
     * @implNote If the point is on the boundary of a non-point shape, this method returns true.
     */
    default boolean contains(Point point)
    {
        return contains(point.getX(), point.getY());
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
    boolean contains(double x, double y);

    /**
     * For {@link Point}, it returns the distance between them. For line-like shape, it returns the minimum distance
     * between the shape and the point. For closed 2-D shape, it returns the distance between the center of the shape
     * and the point.
     *
     * @param point The point to calculate the distance.
     * @return For {@link Point}, it returns the distance between them. For line-like shape, it returns the minimum
     * distance between the shape and the point. For closed 2-D shape, it returns the distance between the center of the
     * shape and the point.
     */
    default double distanceTo(Point point)
    {
        return distanceTo(point.getX(), point.getY());
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
    double distanceTo(double x, double y);
}
