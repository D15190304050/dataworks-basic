// This implementation random number generator is a copy of StdRandom written by Robert Sedgewick and Kevin Wayne.

package stark.dataworks.basic.mathematics;

// TODO: Figure out which one is better: static class or instance.
// Hint: try to have a quick look of implementations in other languages.

public class Random
{
    /**
     * Pseudo-random number generator
     */
    private java.util.Random random;

    /**
     * Pseudo-random number generator seed.
     */
    private long seed;

    /**
     * Initializes a new instance of the {@link Random} class.
     */
    public Random()
    {
        this(System.currentTimeMillis());
    }

    public Random(long seed)
    {
        this.seed = seed;
        random = new java.util.Random(seed);
    }

    /**
     * Sets the seed of the pseudo-random number generator.
     * This method enables you to produce the same sequence of "random"
     * number for each execution of the program.
     * Ordinarily, you should call this method at most once per program.
     *
     * @param seed the seed
     */
    public void setSeed(long seed)
    {
        this.seed = seed;
        random = new java.util.Random(seed);
    }

    /**
     * Returns the seed of the pseudorandom number generator.
     *
     * @return the seed
     */
    public long getSeed()
    {
        return seed;
    }

    /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     */
    public double uniform()
    {
        return random.nextDouble();
    }

    /**
     * Returns a random integer uniformly in [0, n).
     *
     * @param n Number of possible integers
     * @return A random integer uniformly between 0 (inclusive) and <tt>N</tt> (exclusive)
     * @throws IllegalArgumentException If <tt>n <= 0</tt>
     */
    public int uniform(int n)
    {
        if (n <= 0)
            throw new IllegalArgumentException("Argument N must be positive");
        return random.nextInt(n);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a random integer uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random integer uniformly in [a, b)
     * @throws IllegalArgumentException if <tt>b <= a</tt>
     * @throws IllegalArgumentException if <tt>b - a >= Integer.MAX_VALUE</tt>
     */
    public int uniform(int a, int b)
    {
        if (b <= a)
            throw new IllegalArgumentException("Invalid range");
        if ((long) b - a >= Integer.MAX_VALUE)
            throw new IllegalArgumentException("Invalid range");
        return a + uniform(b - a);
    }

    /**
     * Returns a random real number uniformly in [0, n).
     *
     * @param n the right endpoint
     * @return a random real number uniformly in [0, n).
     * @throws IllegalArgumentException unless <tt>n < 0</tt>.
     */
    public double uniform(double n)
    {
        if (n == 0)
            return 0;
        if (n < 0)
            throw new IllegalArgumentException("Argument \"n\" must be positive.");

        return uniform(0.0, n);
    }

    /**
     * Returns a random real number uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless <tt>a < b</tt>
     */
    public double uniform(double a, double b)
    {
        if (!(a < b))
            throw new IllegalArgumentException("Invalid range");
        return a + uniform() * (b - a);
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability <em>p</em>.
     *
     * @param p the probability of returning <tt>true</tt>
     * @return <tt>true</tt> with probability <tt>p</tt> and
     * <tt>false</tt> with probability <tt>p</tt>
     * @throws IllegalArgumentException unless <tt>p >= 0.0</tt> and <tt>p <= 1.0</tt>
     */
    public boolean bernoulli(double p)
    {
        if (!(p >= 0.0 && p <= 1.0))
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        return uniform() < p;
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability 1/2.
     *
     * @return <tt>true</tt> with probability 1/2 and
     * <tt>false</tt> with probability 1/2
     */
    public boolean bernoulli()
    {
        return bernoulli(0.5);
    }

    /**
     * Returns a random real number from a standard normal distribution.
     *
     * @return a random real number from a standard normal distribution
     * (mean 0 and standard deviation 1).
     */
    public double normal()
    {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        do
        {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, 1.0);
            r = x * x + y * y;
        }
        while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random normal
    }

    /**
     * Returns a random real number from a normal distribution with mean &mu;
     * and standard deviation &sigma;.
     *
     * @param mu    the mean
     * @param sigma the standard deviation
     * @return a real number distributed according to the normal distribution
     * with mean <tt>mu</tt> and standard deviation <tt>sigma</tt>
     */
    public double normal(double mu, double sigma)
    {
        return mu + sigma * normal();
    }

    /**
     * Returns a random integer from a geometric distribution with success
     * probability <em>p</em>.
     *
     * @param p the parameter of the geometric distribution
     * @return a random integer from a geometric distribution with success
     * probability <tt>p</tt>; or <tt>Integer.MAX_VALUE</tt> if
     * <tt>p</tt> is (nearly) equal to <tt>1.0</tt>.
     * @throws IllegalArgumentException unless <tt>p >= 0.0</tt> and <tt>p <= 1.0</tt>
     */
    public int geometric(double p)
    {
        if (!(p >= 0.0 && p <= 1.0))
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        // using algorithm given by Knuth
        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    /**
     * Returns a random integer from a Poisson distribution with mean &lambda;.
     *
     * @param lambda the mean of the Poisson distribution
     * @return a random integer from a Poisson distribution with mean <tt>lambda</tt>
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt> and not infinite
     */
    public int poisson(double lambda)
    {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("Argument lambda must be positive");
        if (Double.isInfinite(lambda))
            throw new IllegalArgumentException("Argument lambda must not be infinite");
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        double p = 1.0;
        double L = Math.exp(-lambda);
        do
        {
            k++;
            p *= uniform();
        }
        while (p >= L);
        return k - 1;
    }

    /**
     * Returns a random real number from the standard Pareto distribution.
     *
     * @return a random real number from the standard Pareto distribution
     */
    public double pareto()
    {
        return pareto(1.0);
    }

    /**
     * Returns a random real number from a Pareto distribution with
     * shape parameter &alpha;.
     *
     * @param alpha shape parameter
     * @return a random real number from a Pareto distribution with shape
     * parameter <tt>alpha</tt>
     * @throws IllegalArgumentException unless <tt>alpha > 0.0</tt>
     */
    public double pareto(double alpha)
    {
        if (!(alpha > 0.0))
            throw new IllegalArgumentException("Shape parameter alpha must be positive");
        return Math.pow(1 - uniform(), -1.0 / alpha) - 1.0;
    }

    /**
     * Returns a random real number from the Cauchy distribution.
     *
     * @return a random real number from the Cauchy distribution.
     */
    public double cauchy()
    {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param probabilities the probability of occurrence of each integer
     * @return a random integer from a discrete distribution:
     * <tt>i</tt> with probability <tt>probabilities[i]</tt>
     * @throws NullPointerException     if <tt>probabilities</tt> is <tt>null</tt>
     * @throws IllegalArgumentException if sum of array entries is not (very nearly) equal to <tt>1.0</tt>
     * @throws IllegalArgumentException unless <tt>probabilities[i] >= 0.0</tt> for each index <tt>i</tt>
     */
    public int discrete(double[] probabilities)
    {
        if (probabilities == null)
            throw new NullPointerException("argument array is null");
        double EPSILON = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; i++)
        {
            if (!(probabilities[i] >= 0.0))
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + probabilities[i]);
            sum += probabilities[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON)
            throw new IllegalArgumentException("sum of array entries does not approximately equal 1.0: " + sum);

        // the for loop may not return a value when both r is (nearly) 1.0 and when the
        // cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
        while (true)
        {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < probabilities.length; i++)
            {
                sum = sum + probabilities[i];
                if (sum > r)
                    return i;
            }
        }
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param frequencies the frequency of occurrence of each integer
     * @return a random integer from a discrete distribution:
     * <tt>i</tt> with probability proportional to <tt>frequencies[i]</tt>
     * @throws NullPointerException     if <tt>frequencies</tt> is <tt>null</tt>
     * @throws IllegalArgumentException if all array entries are <tt>0</tt>
     * @throws IllegalArgumentException if <tt>frequencies[i]</tt> is negative for any index <tt>i</tt>
     * @throws IllegalArgumentException if sum of frequencies exceeds <tt>Integer.MAX_VALUE</tt> (2<sup>31</sup> - 1)
     */
    public int discrete(int[] frequencies)
    {
        if (frequencies == null)
            throw new NullPointerException("argument array is null");
        long sum = 0;
        for (int i = 0; i < frequencies.length; i++)
        {
            if (frequencies[i] < 0)
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + frequencies[i]);
            sum += frequencies[i];
        }
        if (sum == 0)
            throw new IllegalArgumentException("at least one array entry must be positive");
        if (sum >= Integer.MAX_VALUE)
            throw new IllegalArgumentException("sum of frequencies overflows an int");

        // pick index i with probabilitity proportional to frequency
        double r = uniform((int) sum);
        sum = 0;
        for (int i = 0; i < frequencies.length; i++)
        {
            sum += frequencies[i];
            if (sum > r)
                return i;
        }

        // can't reach here
        assert false;
        return -1;
    }

    /**
     * Returns a random real number from an exponential distribution
     * with rate &lambda;.
     *
     * @param lambda the rate of the exponential distribution
     * @return a random real number from an exponential distribution with
     * rate <tt>lambda</tt>
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt>
     */
    public double exponential(double lambda)
    {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("Rate lambda must be positive");
        return -Math.log(1 - uniform()) / lambda;
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a   The array to shuffle
     * @param <T> Type of the array.
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */
    public static <T> void shuffle(T[] a)
    {
        Random random = new Random();

        if (a == null)
            throw new NullPointerException("argument array is null");
        int n = a.length;
        for (int i = 0; i < n; i++)
        {
            int r = i + random.uniform(n - i);     // between i and n-1
            T temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */

    public static void shuffle(double[] a)
    {
        Random random = new Random();

        if (a == null)
            throw new NullPointerException("argument array is null");
        int n = a.length;
        for (int i = 0; i < n; i++)
        {
            int r = i + random.uniform(n - i);     // between i and n-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */

    public static void shuffle(int[] a)
    {
        Random random = new Random();

        if (a == null)
            throw new NullPointerException("argument array is null");
        int n = a.length;
        for (int i = 0; i < n; i++)
        {
            int r = i + random.uniform(n - i);     // between i and n-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param a   The array to shuffle
     * @param lo  The left endpoint (inclusive)
     * @param hi  The right endpoint (inclusive)
     * @param <T> Type of the array.
     * @throws NullPointerException      if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) && (hi < a.length)</tt>
     */
    public static <T> void shuffle(T[] a, int lo, int hi)
    {
        Random random = new Random();

        if (a == null)
            throw new NullPointerException("argument array is null");
        if (lo < 0 || lo > hi || hi >= a.length)
        {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++)
        {
            int r = i + random.uniform(hi - i + 1);     // between i and hi
            T temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param a  the array to shuffle
     * @param lo the left endpoint (inclusive)
     * @param hi the right endpoint (inclusive)
     * @throws NullPointerException      if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) && (hi < a.length)</tt>
     */

    public static void shuffle(double[] a, int lo, int hi)
    {
        Random random = new Random();

        if (a == null)
            throw new NullPointerException("argument array is null");
        if (lo < 0 || lo > hi || hi >= a.length)
        {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++)
        {
            int r = i + random.uniform(hi - i + 1);     // between i and hi
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param a  the array to shuffle
     * @param lo the left endpoint (inclusive)
     * @param hi the right endpoint (inclusive)
     * @throws NullPointerException      if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) && (hi < a.length)</tt>
     */
    public static void shuffle(int[] a, int lo, int hi)
    {
        Random random = new Random();

        if (a == null)
            throw new NullPointerException("argument array is null");
        if (lo < 0 || lo > hi || hi >= a.length)
        {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++)
        {
            int r = i + random.uniform(hi - i + 1);     // between i and hi
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}
