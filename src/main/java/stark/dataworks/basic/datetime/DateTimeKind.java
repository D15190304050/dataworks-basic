package stark.dataworks.basic.datetime;

import java.io.Serializable;

/**
 * This enum is used to identify {@link DateTime} instances in cases when they are known to be local time, UTC time or
 * if this information has not been specified or is not applicable.
 */
public enum DateTimeKind implements Serializable
{
    UNSPECIFIED,
    UTC,
    LOCAL
}
