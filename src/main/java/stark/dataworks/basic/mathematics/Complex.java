package stark.dataworks.basic.mathematics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * The {@link Complex} class represents the complex (x + iy).
 * Reference: <a href="https://referencesource.microsoft.com/#System.Numerics/System/Numerics/Complex.cs,925edda71d70b5ad">link</a>.
 */
@Getter
@AllArgsConstructor
public class Complex
{
    private static final double LOG_10_INV = 0.43429448190325;

    private double real;
    private double imaginary;

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex TWO = new Complex(2, 0);
    public static final Complex IMAGINARY_ONE = new Complex(0, 1);

    public Complex()
    {
        this(0, 0);
    }

    public static double abs(Complex complex)
    {
        if (Double.isInfinite(complex.real) || Double.isInfinite(complex.imaginary))
            return Double.POSITIVE_INFINITY;

        double c = Math.abs(complex.real);
        double d = Math.abs(complex.imaginary);

        // |complex| = sqrt(a^2 + b^2)
        // sqrt(a^2 + b^2) = a * sqrt(a^2/a^2 + b^2/a^2)
        // Using the above formula we can factor out the larger component to dodge overflow.

        if (c > d)
        {
            double r = d / c;
            return c * Math.sqrt(1.0 + r * r);
        }
        else if (d == 0.0)
            return c;
        else
        {
            double r = c / d;
            return d * Math.sqrt(1.0 + r * r);
        }
    }

    public double getMagnitude()
    {
        return abs(this);
    }

    public double getPhase()
    {
        return Math.atan2(imaginary, real);
    }

    public static Complex fromPolarCoordinates(double magnitude, double phase)
    {
        return new Complex(Math.cos(phase) * magnitude, Math.sin(phase) * magnitude);
    }

    public Complex negate()
    {
        return new Complex(-real, -imaginary);
    }

    public Complex add(Complex that)
    {
        return new Complex(this.real + that.real, this.imaginary + that.imaginary);
    }

    public Complex add(double that)
    {
        return new Complex(real + that, imaginary);
    }

    public Complex subtract(Complex that)
    {
        return new Complex(this.real - that.real, this.imaginary - that.imaginary);
    }

    public Complex subtract(double that)
    {
        return new Complex(real - that, imaginary);
    }

    public Complex multiply(Complex that)
    {
        // Multiplication: (a + bi)(c + di) = (ac -bd) + (bc + ad)i
        double resultRealPart = this.real * that.real - this.imaginary * that.imaginary;
        double resultImaginaryPart = this.real * that.imaginary + this.imaginary * that.real;
        return new Complex(resultRealPart, resultImaginaryPart);
    }

    public Complex multiply(double that)
    {
        return new Complex(real * that, imaginary * that);
    }

    /**
     * The division of two complex numbers can be simplified by multiplying the numerator and denominator by the conjugate of the denominator.
     * @param that
     * @return
     */
    public Complex divide(Complex that)
    {
        double a = this.real;
        double b = this.imaginary;
        double c = that.real;
        double d = that.imaginary;

        if (c == 0 && d == 0)
            throw new ArithmeticException("Cannot divide by zero.");

        if (Math.abs(d) < Math.abs(c))
        {
            double doc = d / c;
            return new Complex((a + b * doc) / (c + d * doc), (b - a * doc) / (c + d * doc));
        }
        else
        {
            double cod = c / d;
            return new Complex((b + a * cod) / (d * c * cod), (-a + b * cod) / (d * c * cod));
        }
    }

    public Complex divide(double that)
    {
        return new Complex(real / that, imaginary / that);
    }

    public Complex conjugate()
    {
        return new Complex(this.real, -this.imaginary);
    }

    public Complex Reciprocal()
    {
        if (real == 0 && imaginary == 0)
            throw new ArithmeticException("0 does not have reciprocal.");

        return Complex.ONE.divide(this);
    }

    public boolean equals(Complex that)
    {
        return this.real == that.real && this.imaginary == that.imaginary;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Complex complex = (Complex) o;
        return this.equals(complex);
    }

    @Override
    public int hashCode()
    {
        int result = Double.hashCode(real);
        result = 31 * result + Double.hashCode(imaginary);
        return result;
    }

    public String toString()
    {
        return String.format("(%f, %fi)", real, imaginary);
    }

    public static Complex sin(Complex complex)
    {
        double a = complex.real;
        double b = complex.imaginary;

        return new Complex(Math.sin(a) * Math.cosh(b), Math.cos(a) * Math.sinh(b));
    }

    public static Complex sinh(Complex complex)
    {
        double a = complex.real;
        double b = complex.imaginary;

        return new Complex(Math.sinh(a) * Math.cos(b), Math.cosh(a) * Math.sin(b));
    }

    public static Complex scale(Complex complex, double factor)
    {
        return new Complex(complex.real * factor, complex.imaginary * factor);
    }

    public static Complex log(Complex complex)
    {
        return new Complex(Math.log(abs(complex)), Math.atan2(complex.imaginary, complex.real));
    }

    public static Complex log(Complex complex, double base)
    {
        return log(complex).divide(Math.log(base));
    }

    public static Complex log10(Complex complex)
    {
        return scale(log(complex), LOG_10_INV);
    }

    public static Complex sqrt(Complex complex)
    {
        return fromPolarCoordinates(complex.getMagnitude(), complex.getPhase() / 2.0);
    }

    public static Complex asin(Complex complex)
    {
        // (-IMAGINARY_ONE) * log(IMAGINARY_ONE * complex + sqrt(ONE - complex * complex));
        return IMAGINARY_ONE.negate().multiply(log(IMAGINARY_ONE.multiply(complex).add(sqrt(ONE.subtract(complex.multiply(complex))))));
    }

    public static Complex cos(Complex complex)
    {
        double a = complex.real;
        double b = complex.imaginary;

        return new Complex(Math.cos(a) * Math.cosh(b), - Math.sin(a) * Math.sinh(b));
    }

    public static Complex cosh(Complex complex)
    {
        double a = complex.real;
        double b = complex.imaginary;

        return new Complex(Math.cosh(a) * Math.cos(b), Math.sinh(a) * Math.sin(b));
    }

    public static Complex acos(Complex complex)
    {
        // (-ImaginaryOne) * Log(value + ImaginaryOne*Sqrt(One - (value * value)))
        return IMAGINARY_ONE.negate().multiply(log(complex.add(IMAGINARY_ONE.multiply(sqrt(ONE.subtract(complex.multiply(complex)))))));
    }

    public static Complex tan(Complex complex)
    {
        return sin(complex).divide(cos(complex));
    }

    public static Complex tanh(Complex complex)
    {
        return sinh(complex).divide(cosh(complex));
    }

    public static Complex atan(Complex complex)
    {
        // (ImaginaryOne / Two) * (Log(One - ImaginaryOne * value) - Log(One + ImaginaryOne * value))

        return IMAGINARY_ONE.divide(TWO).multiply(log(ONE.subtract(IMAGINARY_ONE.multiply(complex))).subtract(log(ONE.add(IMAGINARY_ONE.multiply(complex)))));
    }

    public static Complex exp(Complex complex)
    {
        double tempFactor = Math.exp(complex.real);
        double resultReal = tempFactor * Math.cos(complex.real);
        double resultImaginary = tempFactor * Math.sin(complex.imaginary);

        return new Complex(resultReal, resultImaginary);
    }

    public static Complex pow(Complex value, Complex power)
    {
        if (power.equals(ZERO))
            return ONE;

        if (value.equals(ZERO))
            return ZERO;

        double a = value.real;
        double b = value.imaginary;
        double c = power.real;
        double d = power.imaginary;

        double rho = abs(value);
        double theta = Math.atan2(b, a);
        double newRho = c * theta + d * Math.log(rho);

        double t = Math.pow(rho, c) * Math.pow(Math.E, -d * theta);
        return new Complex(Math.cos(newRho) * t, Math.sin(newRho) * t);
    }

    public static Complex pow(Complex value, double power)
    {
        return pow(value, new Complex(power, 0));
    }
}
