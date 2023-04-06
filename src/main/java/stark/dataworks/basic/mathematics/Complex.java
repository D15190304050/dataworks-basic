package stark.dataworks.basic.mathematics;

public class Complex
{
    private double angle;
    private double module;

    public Complex()
    {
        this(0, 0);
    }

    public Complex(double angle, double module)
    {
        this.angle = angle;
        this.module = module;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public double getModule()
    {
        return module;
    }

    public void setModule(double module)
    {
        this.module = module;
    }


}
