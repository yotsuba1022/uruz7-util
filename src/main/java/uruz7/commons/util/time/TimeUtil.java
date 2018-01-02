package uruz7.commons.util.time;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Emily Hsieh, Carl Lu
 */
public class TimeUtil {

    private static final long MILLISECOND = 1000L;
    private static final String TIME_ZONE_SUFFIX_IN_UTC_8 = "+08:00";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    /**
     * Constructor
     */
    private TimeUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get current unix timestamp.
     *
     * @return current unix timestamp
     */
    public static String getCurrentUnixTimestamp() {
        return String.valueOf(System.currentTimeMillis() / MILLISECOND);
    }

    /**
     * Compare two iso8601 strings.
     *
     * @param iso8601StringA input iso8601 string A
     * @param iso8601StringB input iso8601 string B
     * @return compared result in int format
     */
    public static int compare(final String iso8601StringA, final String iso8601StringB) {
        Instant instantA = Instant.ofEpochSecond(
                Long.valueOf(TimeFormat.convert(iso8601StringA, TimeFormat.ISO8601UTC, TimeFormat.UNIX_TIME)));
        Instant instantB = Instant.ofEpochSecond(
                Long.valueOf(TimeFormat.convert(iso8601StringB, TimeFormat.ISO8601UTC, TimeFormat.UNIX_TIME)));
        return instantA.compareTo(instantB);
    }

    /**
     * Compare two local iso8601 strings.
     *
     * @param localIso8601        input iso8601 string
     * @param anotherLocalIso8601 another input iso8601 string
     * @return compared result in int format
     */
    public static int compareLocalIso8601(final String localIso8601, final String anotherLocalIso8601) {
        DateTimeFormatter formatter = getDateTimeFormatter(DEFAULT_DATE_FORMAT);
        LocalDateTime localDateTime1 = LocalDateTime.parse(localIso8601, formatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse(anotherLocalIso8601, formatter);
        return localDateTime1.compareTo(localDateTime2);
    }

    /**
     * Distinguish the time zone is located in UTC+8 or not.
     *
     * @param localIso8601 input iso8601 string
     * @return is located in utc+8?
     */
    public static boolean isTimeZoneInUtc8(final String localIso8601) {
        return !StringUtils.isEmpty(localIso8601) && localIso8601.endsWith(TIME_ZONE_SUFFIX_IN_UTC_8);
    }

    private static DateTimeFormatter getDateTimeFormatter(String dateTimeFormat) {
        return DateTimeFormatter.ofPattern(dateTimeFormat);
    }

}
