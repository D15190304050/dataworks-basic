package stark.dataworks.datetime;

public enum CalendarAlgorithmType
{
    /**
     * This is the default value to return in the {@link Calendar} base class.
     */
    UNKNOWN,

    /**
     * Solar-base calendar, such as GregorianCalendar, JuliaCalendar, etc.
     * Solar calendars are based on the solar year and seasons.
     */
    SOLAR_CALENDAR,

    /**
     * Lunar-based calendar, such as Hijri and UmAlQuraCalendar.
     * Lunar calendars are based on the path of the moon. The seasons are not accurately represented.
     */
    LUNAR_CALENDAR,

    /**
     * Lunisolar-based calendar which use leap month rule, such as HebrewCalendar and Asian Lunisolar calendars.
     * Lunisolar calendars are based on the cycle of the moon, but consider the seasons as a secondary consideration, so
     * they align with the seasons as well as lunar events.
     */
    LUNISOLAR_CALENDAR
}
