package stark.dataworks.models;

import stark.dataworks.mathematics.Vector;

import java.io.Serializable;

public class NumericSample implements Serializable
{
    public Vector data;
    public String[] attributes;

    public NumericSample()
    {
        data = null;
        attributes = null;
    }

    public NumericSample(Vector data, String[] attributes)
    {
        this.data = data;
        this.attributes = attributes;
    }

    public Vector getData()
    {
        return data;
    }

    public void setData(Vector data)
    {
        this.data = data;
    }

    public String[] getAttributes()
    {
        return attributes;
    }

    public void setAttributes(String[] attributes)
    {
        this.attributes = attributes;
    }

    public String getAttribute(int index)
    {
        return attributes[index];
    }

    public void setAttribute(int index, String value)
    {
        attributes[index] = value;
    }
}
