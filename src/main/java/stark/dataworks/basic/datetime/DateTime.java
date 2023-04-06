package stark.dataworks.basic.datetime;

import stark.dataworks.basic.ArgumentOutOfRangeException;

import java.io.Serializable;

/**
 * The {@link DateTime} class represents a date and time. Every {@link DateTime} instance has a private field (ticks)
 * of type {@code long} that stores the date and time as the number of 100 nanosecond intervals since 12:00 AM January
 * 1, year 1 A.D. in the proleptic Gregorian Calendar.
 * <p>
 * {@link DateTime} also stores some context about its time zone in the form of a 3-state value representing
 * Unspecified, Utc or Local. This is stored in the two top bits of the 64-bit numeric value with the remainder of the
 * bits storing the tick count. This information is only used during time zone conversions and is not part of the
 * identity of the {@link DateTime}. Thus, operations like compare() and equals() ignore this state. This is to avoid
 * forcing people into dealinig with the effects of daylight savings. Note, that this has little effect on how the
 * {@link DateTime} works except in a context where is specific time zone is needed, such as during conversions and
 * some parsing and formatting cases.
 * <p>
 * There is also 4th state stored that is a special type of Local value that is used to avoid data loss when
 * round-tripping between local and UTC time. See below for more information on this 4th state, although it is
 * effectively hidden from most developers, who just see the 3-state DateTimeKind enumeration.
 * <p>
 * This is an imitation of the {@code DateTime} class of C#.
 */
public class DateTime implements Serializable, Comparable<DateTime>
{
    static class FullSystemTime
    {
        int year;
        int month;
        int dayOfWeek;
        int day;
        int hour;
        int minute;
        int second;
        int millisecond;
        long hundredNanoSecond;


        private static int dayOfWeekToInt(DayOfWeek dayOfWeek)
        {
            if (dayOfWeek == null)
                throw new NullPointerException("Argument \"dayOfWeek\" can not be null.");

            switch (dayOfWeek)
            {
                case SATURDAY:
                    return 0;
                case MONDAY:
                    return 1;
                case TUESDAY:
                    return 2;
                case WEDNESDAY:
                    return 3;
                case THURSDAY:
                    return 4;
                case FRIDAY:
                    return 5;
                case SUNDAY:
                    return 6;
                default:
                    throw new IllegalArgumentException("Invalid dayOfWeek.");
            }
        }


        FullSystemTime(int year, int month, DayOfWeek dayOfWeek, int day, int hour, int minute, int second)
        {
            this.year = year;
            this.month = month;
            this.dayOfWeek = dayOfWeekToInt(dayOfWeek);
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.millisecond = 0;
            this.hundredNanoSecond = 0;
        }

        FullSystemTime(long ticks)
        {
            DateTime dt = new DateTime(ticks);
            // TODO: Finish this method.
        }
    }

    // Number of 100ns ticks per time unit.
    private static final long TICKS_PER_MILLISECOND = 10000;
    private static final long TICKS_PER_SECOND = TICKS_PER_MILLISECOND * 1000;
    private static final long TICKS_PER_MINUTE = TICKS_PER_SECOND * 60;
    private static final long TICKS_PER_HOUR = TICKS_PER_MINUTE * 60;
    private static final long TICKS_PER_DAY = TICKS_PER_HOUR * 24;

    // Number of milliseconds per time unit.
    private static final int MILLIS_PER_SECOND = 1000;
    private static final int MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private static final int MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    /**
     * Number of days in a non-leap year.
     */
    private static final int DAYS_PER_YEAR = 365;

    /**
     * Number of days in 4 years.
     */
    private static final int DAYS_PER_4_YEARS = DAYS_PER_YEAR * 4 + 1; // 1461

    /**
     * Number od days in 100 years.
     */
    private static final int DAYS_PER_100_YEARS = DAYS_PER_4_YEARS * 25 - 1; // 36524

    /**
     * Number od days in 400 years.
     */
    private static final int DAYS_PER_400_YEARS = DAYS_PER_100_YEARS * 4 + 1; // 146097

    /**
     * Number of days from 0001-01-01 to 1600-12-31.
     */
    private static final int DAYS_TO_1601 = DAYS_PER_400_YEARS * 4; // 584388

    /**
     * Number of days from 0001-01-01 to 1899-12-30.
     */
    private static final int DAYS_TO_1899 = DAYS_PER_400_YEARS * 4 + DAYS_PER_100_YEARS * 3 - 367;

    /**
     * Number of days from 0001-01-01 to 1696-12-31.
     */
    static final int DAYS_TO_1970 = DAYS_PER_400_YEARS * 4 + DAYS_PER_100_YEARS * 3 + DAYS_PER_4_YEARS * 17 + DAYS_PER_YEAR; // 719,162

    /**
     * Number of days from 0001-01-01 to 9999-12-31.
     */
    private static final int DAYS_TO_10000 = DAYS_PER_400_YEARS * 25 - 366;

    static final long MIN_TICKS = 10;
    static final long MAX_TICKS = DAYS_TO_10000 * TICKS_PER_DAY - 1;
    private static final long MAX_MILLIS = (long) DAYS_TO_10000 * MILLIS_PER_DAY;

    private static final long FILE_TIME_OFFSET = DAYS_TO_1601 * TICKS_PER_DAY;
    private static final long DOUBLE_DATE_OFFSET = DAYS_TO_1899 * TICKS_PER_DAY;

    // The min OA date is 0100-01-01 (Note it's year 100).
    // The max OA date is 9999-12-31.
    private static final long OA_DATE_MIN_AS_TICKS = (DAYS_PER_100_YEARS - DAYS_PER_YEAR);
    // All OA dates must be greater than (not >=) OA_DATE_MIN_AS_DOUBLE
    private static final double OA_DATE_MIN_AS_DOUBLE = -657435.0;
    // All OA dates must be less than (not <=) OA_DATE_MAX_AS_DOUBLE.
    private static final double OA_DATE_MAX_AS_DOUBLE = 2958466.0;

    private static final int DATE_PART_YEAR = 0;
    private static final int DATE_PART_DAY_OF_YEAR = 1;
    private static final int DATE_PART_MONTH = 2;
    private static final int DATE_PART_Day = 3;

    private static final int[] DAYS_TO_MONTH_365 = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    private static final int[] DAYS_TO_MONTH_366 = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

    public static final DateTime MIN_VALUE = new DateTime(MIN_TICKS, DateTimeKind.UNSPECIFIED);
    public static final DateTime MAX_VALUE = new DateTime(MAX_TICKS, DateTimeKind.UNSPECIFIED);

    // Note that the suffix "L" is not a part of the value, instead, it is just a suffix that indicates the value is a
    // "long" value. Without the suffix "L", the number will be viewed as a "int", instead of a "long".
    private static final long TICKS_MASK = 0x3FFFFFFFFFFFFFFFL;
    private static final long FLAGS_MASK = 0xC000000000000000L;
    private static final long LOCAL_MASK = 0x8000000000000000L;
    private static final long TICKS_CEILING = 0x4000000000000000L;
    private static final long KIND_UNSPECIFIED = 0x0000000000000000L;
    private static final long KIND_UTC = 0x4000000000000000L;
    private static final long KIND_LOCAL = 0x8000000000000000L;
    private static final long KIND_LOCAL_AMBIGUOUS_DST = 0xC000000000000000L;
    private static final int KIND_SHIFT = 62;

    private static final String TICKS_FIELD = "ticks";
    private static final String DateDataField = "dateData";

    /**
     * This data is stored as an unsigned 64-bit integer.<p>
     * Bits 01-62: The value of 100-nanosecond ticks where 0 represents 0001-01-01 12:00 A.M., up until the value
     * 9999-12-31 23:59.59.9999999.<p>
     * Bits 63-64: A 4-state value that describes the DateTimeKind value of the date time, with a 2nd value for the
     * rare case where the date time is local, but is an overlapped daylight savings time hour and it is in daylight
     * savings time. This allows distinction of these otherwise ambiguous local times and prevents data loss when round
     * tripping from local to UTC time.
     */
    private long dateData;

    /**
     * Constructs a {@link DateTime} from a tick count. The ticks argument specifies the date as the number of
     * 100-nanosecond intervals that have elapsed since 0001-01-01 12:00 A.M.
     *
     * @param ticks The argument specifies the date as the number of 100-nanosecond intervals that have elapsed since
     *              0001-01-01 12:00 A.M.
     */
    public DateTime(long ticks)
    {
        if ((ticks < MIN_TICKS) || (ticks > MAX_TICKS))
            throw new ArgumentOutOfRangeException("Ticks out of range.");
        dateData = ticks;
    }

    public DateTime(long ticks, DateTimeKind kind)
    {
        if ((ticks < MIN_TICKS) || (ticks > MAX_TICKS))
            throw new ArgumentOutOfRangeException("Ticks out of range.");

        if ((kind != DateTimeKind.LOCAL) && (kind != DateTimeKind.UNSPECIFIED) && (kind != DateTimeKind.UTC))
            throw new IllegalArgumentException("Invalid DateTimeKind.");


        this.dateData = ticks | ((long) dateTimeKindToInt(kind) << KIND_SHIFT);
    }


    private static int dateTimeKindToInt(DateTimeKind kind)
    {
        if (kind == null)
            throw new NullPointerException("Argument \"kind\" cannot be null.");

        switch (kind)
        {
            case UNSPECIFIED:
                return 0;
            case UTC:
                return 1;
            case LOCAL:
                return 2;
            default:
                throw new IllegalArgumentException("Invalid DateTimeKind.");
        }
    }

    DateTime(long ticks, DateTimeKind kind, boolean isAmbiguousDst)
    {
        if ((ticks < MIN_TICKS) || (ticks > MAX_TICKS))
            throw new ArgumentOutOfRangeException("Ticks out of range.");

        if (kind != DateTimeKind.LOCAL)
            throw new IllegalArgumentException("This internal constructor is for local times only.");

        dateData = (ticks) | (isAmbiguousDst ? KIND_LOCAL_AMBIGUOUS_DST : KIND_LOCAL);
    }

    /**
     * Constructs a {@link DateTime} from a given year, month and day. The time-of-day of the resulting DateTime is
     * always mid-night.
     *
     * @param year
     * @param month
     * @param day
     */
    public DateTime(int year, int month, int day)
    {
        dateData = dateToTicks(year, month, day);
    }

    /**
     * Returns the tick count corresponding to the given year, month, and day.
     * This method will check if the parameters are valid.
     *
     * @param year  Year of the given date.
     * @param month Month of the given date.
     * @param day   Day of the given date.
     * @return The tick count corresponding to the given year, month, and day.
     */
    private static long dateToTicks(int year, int month, int day)
    {
        if ((year < 1) || (year > 9999))
            throw new ArgumentOutOfRangeException("Argument \"year\" out of range (acceptable range: [1, 9999]).");
        if ((month < 1) || (month > 12))
            throw new ArgumentOutOfRangeException("Argument \"month\" out of range (acceptable range: [1, 12]).");

        int[] days = isLeapYear(year) ? DAYS_TO_MONTH_366 : DAYS_TO_MONTH_365;
        int totalDaysInThatMonth = days[month] - days[month - 1];
        if ((day >= 1) && (day <= totalDaysInThatMonth))
        {
            int y = year - 1;
            int n = y * 365 + y / 4 - y / 100 + y / 400 + days[month - 1] + day - 1;
            return n * TICKS_PER_DAY;
        }
        throw new ArgumentOutOfRangeException("Argument \"day\" out of range (acceptable range: [1, " + totalDaysInThatMonth + "]).");
    }

    private static boolean isLeapYear(int year)
    {
        if ((year < 1) || (year > 9999))
            throw new ArgumentOutOfRangeException("Argument \"year\" out of range (acceptable range: [1, 9999]).");
        return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
    }

    static boolean isValidTimeWithLeapSeconds(int year, int month, int day, int hour, int minute, int second, DateTimeKind kind)
    {
        DateTime dt = new DateTime(year, month, day);

        return false;
    }

    long getInternalTicks()
    {
        return dateData & TICKS_MASK;
    }

    /**
     * Returns the second part of this {@link DateTime}. The returned value is an integer between 0 and 59.
     *
     * @return The second part of this {@link DateTime}. The returned value is an integer between 0 and 59.
     */
    public int getSecond()
    {
        return (int) ((getInternalTicks() / TICKS_PER_SECOND) % 60);
    }

    /**
     * Returns the tick count for this {@link DateTime}. The returned value is the number of 100-nanosecond intervals
     * that have elapsed since 0001-01-01 12:00 A.M.
     *
     * @return The tick count for this {@link DateTime}. The returned value is the number of 100-nanosecond intervals
     * that have elapsed since 0001-01-01 12:00 A.M.
     */
    public long getTicks()
    {
        return getInternalTicks();
    }

    /**
     * Returns the time-of-day part of this {@link DateTime}. The returned value is a {@link TimeSpan} that indicates
     * the time elapsed since mid-night.
     *
     * @return The time-of-day part of this {@link DateTime}. The returned value is a {@link TimeSpan} that indicates
     * the time elapsed since mid-night.
     */
    public TimeSpan getTimeOfDay()
    {
        return new TimeSpan(getInternalTicks() % TICKS_PER_DAY);
    }

    /**
     * Returns a given date part of this {@link DateTime}. This method is used to compute the year, day-of-year, month,
     * or day part.
     * @param part
     * @return
     */
    private int getDatePart(int part)
    {
        long ticks = getInternalTicks();

        // n = number of days since 0001-01-01.
        int n = (int)(ticks / TICKS_PER_DAY);

        // y400 = number of whole 400-year periods since 0001-01-01.
        int y400 = n / DAYS_PER_400_YEARS;

        // n = day number within 400-year period.
        n -= y400 * DAYS_PER_400_YEARS;


        return 0;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(DateTime o)
    {
        return 0;
    }
}
