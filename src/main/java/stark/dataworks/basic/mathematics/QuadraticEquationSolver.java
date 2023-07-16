package stark.dataworks.basic.mathematics;

/**
 * The {@link QuadraticEquationSolver} class represents a solver for a quadratic equation like "a*x^2 + b*x + c = 0".
 */
public class QuadraticEquationSolver
{
    /**
     * Represents that the equation has infinite roots.
     */
    private static final String ANY_VALUE = "Any value";

    /**
     * Represents that the equation has no root.
     */
    private static final String ILLEGAL_COEFFICIENTS = "Illegal coefficients";

    /**
     * Coefficient of x^2.
     */
    private final double a;

    /**
     * Coefficient of x.
     */
    private final double b;

    /**
     * Constant c in the equation.
     */
    private final double c;

    /**
     * Result of the equation.
     * Note: the result is a {@link String} so that it can represent various conditions, such as the equation has no
     * root, or have 2 complex root, which can not be represented by a <tt>double</tt>.
     */
    private String result;

    public QuadraticEquationSolver(double a, double b, double c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        solve();
    }

    /**
     * Gets coefficient of x^2.
     * @return Coefficient of x^2.
     */
    public double getA()
    {
        return a;
    }

    /**
     * Gets coefficient of x.
     * @return Coefficient of x.
     */
    public double getB()
    {
        return b;
    }

    /**
     * Gets constant c in the equation.
     * @return Constant c in the equation.
     */
    public double getC()
    {
        return c;
    }

    /**
     * Gets the result of the equation.
     * @return The result of the equation.
     */
    public String getResult()
    {
        return result;
    }

    /**
     * Solves the equation.
     * @implNote
     * If (a == 0) then it is a linear equation.<br/>
     * If ((a == 0) && (b == 0)), then we have the equation "0*x = c".<br/>
     * If ((a == 0) && (b == 0) && (c == 0)), then x can be any value.<br/>
     * If ((a == 0) && (b == 0) && (c != 0)), then the equation has no root.<br/>
     * If (a != 0), then we can use the formula to solve the quadratic equation.<br/>
     * If (delta < 0), the equation has 2 complex root.
     */
    private void solve()
    {
        if (a == 0)
        {
            if (b == 0)
            {
                if (c == 0)
                    result = ANY_VALUE;
                else
                    result = ILLEGAL_COEFFICIENTS;
            }
            else
            {
                double resultValue = -c / b;
                result = resultValue + "";
            }
        }
        else
        {
            double delta = b * b - 4 * a * c;

            if (delta < 0)
            {
                double deltaSquareRoot = Math.sqrt(-delta);
                double real = -b / (2 * a);
                double imaginaryAbs = deltaSquareRoot / (2 * a);
                String result1 = real + " + " + imaginaryAbs + "i";
                String result2 = real + " - " + imaginaryAbs + "i";
                result = "result1 = " + result1 + ", result2 = " + result2;
            }
            else
            {
                double deltaSquareRoot = Math.sqrt(delta);
                double result1 = (-b + deltaSquareRoot) / (2 * a);
                double result2 = (-b - deltaSquareRoot) / (2 * a);
                result = "result1 = " + result1 + ", result2 = " + result2;
            }
        }
    }

    /**
     * Gets the {@link String} representation of the equation.
     * @return The {@link String} representation of the equation.
     */
    public String getEquation()
    {
        return a + " * x^2 + " + b + " * x + " + c + " = 0";
    }

    /**
     * A unit test method to test all branches of solve().
     * @param args Nothing.
     */
    public static void main(String[] args)
    {
        double[][] testCoefficients = new double[6][3];
        testCoefficients[0] = new double[]{0, 0, 0};
        testCoefficients[1] = new double[]{0, 0, 1};
        testCoefficients[2] = new double[]{0, 1, 5};
        testCoefficients[3] = new double[]{4, 1, 4};
        testCoefficients[4] = new double[]{1, 2, 1};
        testCoefficients[5] = new double[]{1, 4, 1};

        for (double[] testCoefficient : testCoefficients)
        {
            QuadraticEquationSolver solver = new QuadraticEquationSolver(testCoefficient[0],
                                                                         testCoefficient[1],
                                                                         testCoefficient[2]);
            System.out.println(solver.getResult());
        }
    }
}
