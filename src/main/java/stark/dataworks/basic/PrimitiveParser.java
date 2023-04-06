package stark.dataworks.basic;

import stark.dataworks.basic.params.OutValue;

public class PrimitiveParser
{
    private PrimitiveParser(){}

    public static final int BASE_RADIX = 10;

    public static boolean tryParseInt(String rawText, OutValue<Integer> result)
    {
        return tryParseInt(rawText, BASE_RADIX, result);
    }

    public static boolean tryParseInt(String rawText, int radix, OutValue<Integer> result)
    {
        try
        {
            int i = Integer.parseInt(rawText, radix);
            result.setValue(i);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    public static boolean tryParseLong(String rawText, OutValue<Long> result)
    {
        return tryParseLong(rawText, BASE_RADIX, result);
    }

    public static boolean tryParseLong(String rawText, int radix, OutValue<Long> result)
    {
        try
        {
            long i = Long.parseLong(rawText, radix);
            result.setValue(i);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    public static boolean tryParseDouble(String rawText, OutValue<Double> result)
    {
        try
        {
            double i = Double.parseDouble(rawText);
            result.setValue(i);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
