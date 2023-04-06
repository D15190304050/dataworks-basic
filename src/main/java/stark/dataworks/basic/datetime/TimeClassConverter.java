package stark.dataworks.basic.datetime;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TimeClassConverter
{
//    public Date toUtilDate(java.sql.Date date)
//    {
//        return new Date(date.getTime());
//    }

//    public Date toUtilDate(Timestamp timestamp)
//    {
//        return new Date(timestamp.getTime());
//    }

    public Date toUtilDate(Calendar calendar)
    {
        return calendar.getTime();
    }

    public java.sql.Date toSqlDate(Date date)
    {
        return new java.sql.Date(date.getTime());
    }

    public java.sql.Date toSqlDate(Timestamp timestamp)
    {
        return new java.sql.Date(timestamp.getTime());
    }

    public java.sql.Date toSqlDate(Calendar calendar)
    {
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    public Timestamp toTimestamp(Date date)
    {
        return new Timestamp(date.getTime());
    }

    public Timestamp toTimestamp(java.sql.Date date)
    {
        return new Timestamp(date.getTime());
    }

    public Timestamp toTimestamp(Calendar calendar)
    {
        return new Timestamp(calendar.getTimeInMillis());
    }

    public <T extends Date> Calendar toCalendar(T date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
