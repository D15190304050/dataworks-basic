package stark.dataworks.datetime;

public final class TimeUnitRelations
{
    private TimeUnitRelations(){}

    public static final int MILLISECONDS_PER_SECOND = 1000;

    public static final int SECONDS_PER_MINUTE = 60;
    public static final int MINUTES_PER_HOUR = 60;
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    public static final int HOURS_PER_DAY = 24;
    public static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;
    public static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;

    public static final int DAYS_PER_YEAR = 365;
    public static final int DAYS_PER_LEAP_YEAR = 366;


}
