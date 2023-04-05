package stark.dataworks.datetime;

import stark.dataworks.InvalidOperationException;

import java.io.Serializable;

/**
 * This abstract class ({@link Calendar}) represents a calendar. A calendar reckons time in divisions such as weeks,
 * months and years. The number, length, and start of the division vary in each calendar.
 * <p>
 * Any instant in time can be represented as an n-tuple of numeric values using a particular calendar. For example, the
 * next vernal equinox occurs at (0.0, 0, 46, 8, 20, 3, 1999) in the Gregorian calendar. An implementation of
 * {@link Calendar} can map any DateTime value to such an n-tuple and vice versa. The {@link DateTimeFormat} class can
 * map between such n-tuples and a textual representation such as "8:46 A.M. Microsoft  20th 1999 AD".
 * <p>
 * Most calendars identify a year which begins the current era. There may be any number of previous eras. The
 * {@link Calendar} class identifies the eras as enumerated integers where the current era (CURRENT_ERA) has the value
 * 0.
 * <p>
 * For consistency, the first unit in each interval, e.g. the first month, is assigned the value 1.
 * <p>
 * The calculation of hour/minute/second is in the {@link Calendar} class since most of the calendars (or all?) have
 * the same way of calculating hour/minute/second.
 */
public class Calendar implements Cloneable, Serializable
{
    // Number of 100ns (10E-7 seconds) ticks per time unit.
    static final long TICKS_PER_MILLISECOND = 10000;
    static final long TICKS_PER_SECOND = TICKS_PER_MILLISECOND * 1000;
    static final long TICKS_PER_MINUTE = TICKS_PER_SECOND * 60;
    static final long TICKS_PER_HOUR = TICKS_PER_MINUTE * 60;
    static final long TICKS_PER_DAY = TICKS_PER_HOUR * 24;

    // Number fo milliseconds per time unit.
    static final int MILLIS_PER_SECOND = 1000;
    static final int MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
    static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    static final int MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    /**
     * Number of days in a non-leap year.
     */
    static final int DAYS_PER_YEAR = 365;

    /**
     * Number of days in 4 years.
     */
    static final int DAYS_PER_4_YEARS = DAYS_PER_YEAR * 4 + 1;

    /**
     * Number of days in 100 years.
     */
    static final int DAYS_PER_100_YEARS = DAYS_PER_4_YEARS * 25 - 1;

    /**
     * Number of days in 400 years.
     */
    static final int DAYS_PER_400_YEARS = DAYS_PER_100_YEARS * 4 + 1;

    /**
     * Number of days from 0001-01-01 to 10000-01-01.
     */
    static final int DAYS_TO_10000 = DAYS_PER_400_YEARS * 25 - 366;

    static final long MAX_MILLIS = (long) DAYS_TO_10000 * MILLIS_PER_DAY;

    // Calendar ID Values. This is used to get data from calendar.
    // The order of calendar ID means the order of data items in the table.

    /**
     * Gregorian (localized) calendar.
     */
    static final int CAL_GREGORIAN = 1;

    /**
     * Gregorian (U.S.) calendar.
     */
    static final int CAL_GREGORIAN_US = 2;

    /**
     * Japanese Emperor Era calendar.
     */
    static final int CAL_JAPAN = 3;

    /**
     * Taiwan Era calendar.
     */
    static final int CAL_TAIWAN = 4;

    /**
     * Korean Tangun era calendar.
     */
    static final int CAL_KOREA = 5;

    /**
     * Hijri (Arabic Lunar) calendar.
     */
    static final int CAL_HIJRI = 6;

    /**
     * Thai calendar.
     */
    static final int CAL_THAI = 7;

    /**
     * Hebrew (Lunar) calendar.
     */
    static final int CAL_HEBREW = 8;

    /**
     * Gregorian Middle East French calendar.
     */
    static final int CAL_GREGORIAN_ME_FRENCH = 9;

    /**
     * Gregorian Arabic calendar.
     */
    static final int CAL_GREGORIAN_ARABIC = 10;

    /**
     * Gregorian Transliterated English calendar.
     */
    static final int CAL_GREGORIAN_XLIT_ENGLISH = 11;
    static final int CAL_GREGORIAN_XLIT_FRENCH = 12;
    static final int CAL_JULIAN = 13;
    static final int CAL_JAPANESELUNISOLAR = 14;
    static final int CAL_CHINESELUNISOLAR = 15;

    static final int CAL_SAKA = 16; // Reversed to match Office but not implemented in our code.
    static final int CAL_LUNAR_ETO_CHN = 17; // Reversed to match Office but not implemented in our code.
    static final int CAL_LUNAR_ETO_KOR = 18; // Reversed to match Office but not implemented in our code.
    static final int CAL_LUNAR_ETO_ROKUYOU = 19; // Reversed to match Office but not implemented in our code.
    static final int CAL_KOREANLUNISOLAR = 20;
    static final int CAL_TAIWANLUNISOLR = 21;
    static final int CAL_PERSIAN = 22;
    static final int CAL_UMALQURA = 23;

    int currentEraValue;
    private boolean isReadOnly;

    public DateTime getMinSupportedDateTime()
    {
        return DateTime.MIN_VALUE;
    }

    /**
     * Do-nothing constructor.
     */
    protected Calendar()
    {
    }

    /**
     * This cannot be abstract, otherwise no one can create a derived class of {@link Calendar}.
     *
     * @return
     */
    int id()
    {
        return -1;
    }

    /**
     * Returns the base {@link Calendar} ID for calendars that didn't have defined data in calendarData.
     *
     * @return
     */
    int baseCalendarId()
    {
        return id();
    }

    public CalendarAlgorithmType getCalendarAlgorithmType()
    {
        return CalendarAlgorithmType.UNKNOWN;
    }

    // Detect if the object is read-only.
    public boolean isReadOnly()
    {
        return isReadOnly;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        // TODO: Implement this method.
        return null;
    }

    public Object memberwiseClone()
    {
        // TODO: Implement this method.
        return null;
    }

    void setReadOnlyState(boolean readOnly)
    {
        isReadOnly = readOnly;
    }



    public static Calendar readOnly(Calendar calendar)
    {
        if (calendar == null)
            throw new NullPointerException("Argument \"calendar\" cannot be null.");
        if (calendar.isReadOnly)
            return calendar;
        Calendar clonedCalendar = (Calendar) calendar.memberwiseClone();
        clonedCalendar.setReadOnlyState(true);
        return clonedCalendar;
    }

    void verifyWritable()
    {
        if (isReadOnly)
            throw new InvalidOperationException("Invalid operation, read only.");
    }

    int getCurrentEraValue()
    {
        // TODO: Implement this method.

        // The following code assumes that the current era value can not be -1.
        if (currentEraValue == -1)
            ;
        return 0;
    }
}
