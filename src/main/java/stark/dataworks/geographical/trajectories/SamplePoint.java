package stark.dataworks.geographical.trajectories;

import com.fasterxml.jackson.annotation.JsonFormat;
import stark.dataworks.mathematics.geometry.Point;

import java.util.Date;

public class SamplePoint extends Point
{
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date samplingTime;

    public SamplePoint(double x, double y, Date samplingTime)
    {
        super(x, y);

        this.samplingTime = samplingTime;
    }

    public Date getSamplingTime()
    {
        return samplingTime;
    }

    public void setSamplingTime(Date samplingTime)
    {
        this.samplingTime = samplingTime;
    }

    @Override
    public String toString()
    {
        return "[" + super.toString() + ", " + samplingTime + "]";
    }
}
