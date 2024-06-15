package stark.dataworks.basic.mathematics;

/**
 * The {@link Mathematics} class provides common mathematical functions.
 */
public class Mathematics
{
    private Mathematics(){}

    private static double epsilon;

    static
    {
        epsilon = 1e-5;
    }

    public static int max(int... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int max = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (max < array[i])
                max = array[i];
        }

        return max;
    }

    public static double max(double... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        double max = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (max < array[i])
                max = array[i];
        }

        return max;
    }

    public static long max(long... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        long max = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (max < array[i])
                max = array[i];
        }

        return max;
    }

    public static short max(short... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        short max = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (max < array[i])
                max = array[i];
        }

        return max;
    }

    public static float max(float... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        float max = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (max < array[i])
                max = array[i];
        }

        return max;
    }

    public static int min(int... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int min = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (min > array[i])
                min = array[i];
        }

        return min;
    }

    public static double min(double... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        double min = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (min > array[i])
                min = array[i];
        }

        return min;
    }

    public static long min(long... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        long min = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (min > array[i])
                min = array[i];
        }

        return min;
    }

    public static short min(short... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        short min = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (min > array[i])
                min = array[i];
        }

        return min;
    }

    public static float min(float... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        float min = array[0];
        for (int i = 1; i < array.length; i++)
        {
            if (min > array[i])
                min = array[i];
        }

        return min;
    }

    public static int indexOfMax(int... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMax = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMax] < array[i])
                indexOfMax = i;
        }

        return indexOfMax;
    }

    public static int indexOfMax(double... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMax = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMax] < array[i])
                indexOfMax = i;
        }

        return indexOfMax;
    }

    public static int indexOfMax(long... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMax = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMax] < array[i])
                indexOfMax = i;
        }

        return indexOfMax;
    }

    public static int indexOfMax(short... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMax = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMax] < array[i])
                indexOfMax = i;
        }

        return indexOfMax;
    }

    public static int indexOfMax(float... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMax = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMax] < array[i])
                indexOfMax = i;
        }

        return indexOfMax;
    }

    public static int indexOfMin(int... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMin = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMin] > array[i])
                indexOfMin = i;
        }

        return indexOfMin;
    }

    public static int indexOfMin(double... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMin = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMin] > array[i])
                indexOfMin = i;
        }

        return indexOfMin;
    }

    public static int indexOfMin(long... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMin = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMin] > array[i])
                indexOfMin = i;
        }

        return indexOfMin;
    }

    public static int indexOfMin(short... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMin = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMin] > array[i])
                indexOfMin = i;
        }

        return indexOfMin;
    }

    public static int indexOfMin(float... array)
    {
        if (array.length == 0)
            throw new IllegalArgumentException("The number of arguments for this method should be at least 1.");

        int indexOfMin = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[indexOfMin] > array[i])
                indexOfMin = i;
        }

        return indexOfMin;
    }

    /**
     * Gets the maximum value of `epsilon` that satisfies following statement: 2 double values are assumed equal iff the absolute value of their difference is less than `epsilon`.
     * <p>
     * Assuming x and y are 2 double values. If |x - y| < `epsilon`, then x and y are equal in the context.
     * @return the maximum value of `epsilon` that satisfies following statement: 2 double values are assumed equal iff the absolute value of their difference is less than `epsilon`.
     */
    public static double getEpsilon()
    {
        return epsilon;
    }

    /**
     * Sets the maximum value of `epsilon` that satisfies following statement: 2 double values are assumed equal iff the absolute value of their difference is less than `epsilon`.
     * <p>
     * Assuming x and y are 2 double values. If |x - y| < `epsilon`, then x and y are equal in the context.
     * @param epsilon The maximum value of `epsilon` that satisfies following statement: 2 double values are assumed equal iff the absolute value of their difference is less than `epsilon`.
     */
    public static void setEpsilon(double epsilon)
    {
        validateEpsilon(epsilon);
        Mathematics.epsilon = epsilon;
    }

    public static void validateEpsilon(double epsilon)
    {
        if (epsilon <= 0)
            throw new IllegalArgumentException("Argument \"epsilon\" must be a positive value.");
    }

    public static boolean equals(double x, double y, double epsilon)
    {
        validateEpsilon(epsilon);
        return Math.abs(x - y) < epsilon;
    }

    public static boolean equals(double x, double y)
    {
        return equals(x, y, getEpsilon());
    }

    public static boolean inRange(double value, double min, double max)
    {
        if (max < min)
            throw new IllegalArgumentException("Argument \"min\" must be less than or equal to \"max\".");
        return min <= value && value <= max;
    }
}
