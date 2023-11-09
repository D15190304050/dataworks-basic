package stark.dataworks.basic.datetime;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DateTimeCalculator
{
    private DateTimeCalculator()
    {}

    public static java.util.Calendar add(java.util.Calendar base, int timeSpan, int calendarUnit)
    {
        Calendar result = Calendar.getInstance();
        result.setTime(base.getTime());
        result.add(calendarUnit, timeSpan);
        return result;
    }
}
