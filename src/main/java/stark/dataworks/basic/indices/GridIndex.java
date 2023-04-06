package stark.dataworks.basic.indices;

import stark.dataworks.basic.ArgumentOutOfRangeException;
import stark.dataworks.basic.collections.LinkedList;

/**
 * The {@link GridIndex} class represents a 2-D grid index for quick data filtering and access.
 *
 * @param <T> The data type for elements stored in this index.
 */
public class GridIndex<T>
{
    /**
     * The minimum on x direction.
     */
    private double minX;

    /**
     * The maximum on x direction.
     */
    private double maxX;

    /**
     * The minimum on y direction.
     */
    private double minY;

    /**
     * The maximum on y direction.
     */
    private double maxY;

    /**
     * Interval of 2 cell center on x direction.
     */
    private double xInterval;

    /**
     * Interval of 2 cell center on y direction.
     */
    private double yInterval;

    /**
     * Number of rows in this grid.
     */
    private int rowCount;

    /**
     * Number of columns in this grid.
     */
    private int columnCount;

    /**
     * The collection that stores all the data.
     */
    private LinkedList<T>[][] data;

    /**
     * Initializes a grid index with specified arguments.
     *
     * @param minX        The maximum on x direction.
     * @param maxX        The maximum on x direction.
     * @param minY        The minimum on y direction.
     * @param maxY        The maximum on y direction.
     * @param rowCount    Number of rows in this grid.
     * @param columnCount Number of columns in this grid.
     */
    public GridIndex(double minX, double maxX, double minY, double maxY, int rowCount, int columnCount)
    {
        reset(minX, maxX, minY, maxY, rowCount, columnCount);
    }

    /**
     * Gets the minimum of x.
     *
     * @return The minimum of x.
     */
    public double getMinX()
    {
        return minX;
    }

    /**
     * Gets the minimum of y.
     *
     * @return The minimum of y.
     */
    public double getMinY()
    {
        return minY;
    }

    /**
     * Gets the maximum of x.
     *
     * @return The maximum of x.
     */
    public double getMaxX()
    {
        return maxX;
    }

    /**
     * Gets the maximum of y.
     *
     * @return The maximum of y.
     */
    public double getMaxY()
    {
        return maxY;
    }

    /**
     * Gets the interval of 2 cell center on x direction.
     *
     * @return The interval of 2 cell center on x direction.
     */
    public double getXInterval()
    {
        return xInterval;
    }

    /**
     * Gets the interval of 2 cell center on y direction.
     *
     * @return The interval of 2 cell center on y direction.
     */
    public double getYInterval()
    {
        return yInterval;
    }

    /**
     * Gets the number of rows in this grid.
     *
     * @return The number of rows in this grid.
     */
    public double getRowCount()
    {
        return rowCount;
    }

    /**
     * Gets the number of columns in this grid.
     *
     * @return The number of columns in this grid.
     */
    public double getColumnCount()
    {
        return columnCount;
    }

    /**
     * Adds an element with specified coordinate to this grid.
     *
     * @param x       X-coordinate of this element.
     * @param y       Y-coordinate of this element
     * @param element The element to add.
     */
    public void add(double x, double y, T element)
    {
        validateXY(x, y);
        data[(int) (x / xInterval)][(int) (y / yInterval)].addLast(element);
    }

    /**
     * Adds an element with specified coordinate to this grid.
     *
     * @param x       X-coordinate of this element.
     * @param y       Y-coordinate of this element
     * @param element The element to add.
     */
    public void add(int x, int y, T element)
    {
        validateXY(x, y);
        data[(int) (x / xInterval)][(int) (y / yInterval)].addLast(element);
    }

    /**
     * Tries to remove the element in the specified cell. If the cell doesn't contain the element, this is a no-op.
     *
     * @param x       X-index of the cell.
     * @param y       Y-index of the cell.
     * @param element The element to remove.
     */
    public void remove(int x, int y, T element)
    {
        validateXY(x, y);
        data[x][y].remove(element);
    }

    /**
     * Gets all the elements in the specified cell.
     *
     * @param x X-index of the cell.
     * @param y Y-index of the cell.
     * @return All the elements in the specified cell.
     */
    public Iterable<T> getElements(int x, int y)
    {
        return data[x][y];
    }

    /**
     * Clears all the elements in the specified cell.
     *
     * @param x X-index of the cell.
     * @param y Y-index of the cell.
     */
    public void clear(int x, int y)
    {
        validateXY(x, y);
        data[x][y].clear();
    }

    /**
     * Clears all the elements in this grid.
     */
    public void clear()
    {
        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < columnCount; j++)
                data[i][j].clear();
        }
    }

    /**
     * Clears all the data in this grid and reset it with the specified arguments.
     *
     * @param minX        The maximum on x direction.
     * @param maxX        The maximum on x direction.
     * @param minY        The minimum on y direction.
     * @param maxY        The maximum on y direction.
     * @param rowCount    Number of rows in this grid.
     * @param columnCount Number of columns in this grid.
     */
    public void reset(double minX, double maxX, double minY, double maxY, int rowCount, int columnCount)
    {
        // Clear previous data so that their memory can be released by GC.
        if (data != null)
            clear();

        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        xInterval = (maxX - minX) / rowCount;
        yInterval = (maxY - minY) / columnCount;

        data = (LinkedList<T>[][]) new Object[rowCount][columnCount];
    }

    /**
     * Gets all the elements within the cells intersect with or inside the specified circle.
     *
     * @param x      X-coordinate of the circle.
     * @param y      Y-coordinate of the circle.
     * @param radius Radius of the circle.
     * @return All the elements within the cells that intersect with or inside the specified circle.
     */
    public Iterable<T> adjacent(double x, double y, double radius)
    {
        int xSpan = (int) (radius / xInterval) + 1;
        int ySpan = (int) (radius / yInterval) + 1;
        return adjacent(x, y, xSpan, ySpan);
    }

    /**
     * Gets all the elements within the cells centered at the cell that contains (x, y) and specified range.
     *
     * @param x
     * @param y
     * @param xSpan
     * @param ySpan
     * @return
     */
    public Iterable<T> adjacent(double x, double y, double xSpan, double ySpan)
    {
        int xSpan2 = (int) (xSpan / xInterval) + 1;
        int ySpan2 = (int) (ySpan / yInterval) + 1;
        return adjacent(x, y, xSpan2, ySpan2);
    }

    public Iterable<T> adjacent(double x, double y, int xSpan, int ySpan)
    {
        int centerX = (int) (x / xInterval);
        int centerY = (int) (y / yInterval);
        return adjacent(centerX, centerY, xSpan, ySpan);
    }

    public Iterable<T> adjacent(int x, int y, int xSpan, int ySpan)
    {
        validateXY(x, y);
        LinkedList<T> adjacentData = new LinkedList<>();
        for (int i = x - xSpan; i <= x + xSpan; i++)
        {
            for (int j = y - ySpan; j <= y + ySpan; j++)
            {
                if ((i >= 0) &&
                    (i < rowCount) &&
                    (j >= 0) &&
                    (j < columnCount))
                    adjacentData.addAll(getElements(i, j));
            }
        }

        return adjacentData;
    }

    public Iterable<T> getPatch(int minX, int maxX, int minY, int maxY)
    {
        validateRange(minX, maxX, minY, maxY);
        LinkedList<T> patchData = new LinkedList<>();
        for (int i = minX; i <= maxX; i++)
        {
            for (int j = minY; j <= maxY; j++)
                patchData.addAll(data[i][j]);
        }

        return patchData;
    }

    private void validateXY(double x, double y)
    {
        if ((x < minX) || (x > maxX))
            throw new ArgumentOutOfRangeException("Argument \"x\" must between minX (" + minX + ") and maxX (" + maxX + ").");
        if ((y < minY) || (y > maxY))
            throw new ArgumentOutOfRangeException("Argument \"y\" must between minY (" + minY + ") and maxY (" + maxY + ").");
    }

    private void validateXY(int x, int y)
    {
        if ((x < 0) || (x > rowCount))
            throw new ArgumentOutOfRangeException("Argument \"x\" must between 0 and rowCount (" + rowCount + ").");
        if ((y < 0) || (y > columnCount))
            throw new ArgumentOutOfRangeException("Argument \"y\" must between 0 and columnCount (" + columnCount + ").");
    }

    private void validateRange(double minX, double maxX, double minY, double maxY)
    {
        if (minX >= maxX)
            throw new IllegalArgumentException("Argument \"minX\" must be less than parameter \"maxX\".");
        if (minY >= maxY)
            throw new IllegalArgumentException("Argument \"minY\" must be less than parameter \"maxY\".");
    }

    private void validateRange(int minX, int maxX, int minY, int maxY)
    {
        if (minX > maxX)
            throw new IllegalArgumentException("Argument \"minX\" must be less than or equal to parameter \"maxX\".");
        if (minY > maxY)
            throw new IllegalArgumentException("Argument \"minY\" must be less than or equal to parameter \"maxY\".");
    }
}
