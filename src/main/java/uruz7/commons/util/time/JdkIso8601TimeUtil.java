package uruz7.commons.util.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Carl Lu
 */
public class JdkIso8601TimeUtil {

    private static final long MILLISECOND = 1000L;
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Taipei");
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private static final String MYSQL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Constructor
     */
    private JdkIso8601TimeUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Convert ISO-8601 to Unix timestamp.
     *
     * @param iso8601 ISO-8601 time
     * @return unix timestamp string
     */
    public static String convertIso8601ToUnixTimestamp(final String iso8601) {
        Instant instant = Instant.parse(iso8601);
        return String.valueOf(instant.getEpochSecond());
    }

    /**
     * Optional input for converter.
     *
     * @param iso8601 ISO-8601 time
     * @return unix timestamp string
     */
    public static String optionalConvertIso8601ToUnixTimestamp(final String iso8601) {
        if (null == iso8601) {
            return null;
        } else if ("".equals(iso8601)) {
            return null;
        } else {
            return convertIso8601ToUnixTimestamp(iso8601);
        }
    }

    /**
     * Optional input for converter.
     *
     * @param unixTimestamp unix timestamp
     * @param zoneId        zone id
     * @return ISO-8601 time string
     */
    public static String optionalConvertUnixTimestampToIso8601(final String unixTimestamp, final ZoneId zoneId) {
        if (null == unixTimestamp) {
            return null;
        } else {
            Instant instant = Instant.ofEpochSecond(Long.valueOf(unixTimestamp));
            return getDateTimeFormatter(DEFAULT_DATE_FORMAT).format(instant.atZone(zoneId));
        }
    }

    /**
     * Convert unix time to ISO8601, the result will affected by the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @return ISO-8601 time string
     */
    public static String convertUnixTimestampToIso8601(final String unixTimestamp) {
        return convertUnixTimestampToIso8601(unixTimestamp, DEFAULT_ZONE_ID);
    }

    /**
     * Convert unix time to ISO8601 with specified zone id, the result will affected by the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @param zoneId        zone id
     * @return ISO-8601 time string
     */
    public static String convertUnixTimestampToIso8601(final String unixTimestamp, ZoneId zoneId) {
        Instant instant = Instant.ofEpochSecond(Long.valueOf(unixTimestamp));
        return getDateTimeFormatter(DEFAULT_DATE_FORMAT).format(instant.atZone(zoneId));
    }

    /**
     * Convert unix time to MySQL date time, the result will affected by the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @return MySQL date time string
     */
    public static String convertUnixTimestampToMySqlDateTime(final String unixTimestamp) {
        return convertUnixTimestampToMySqlDateTime(unixTimestamp, DEFAULT_ZONE_ID);
    }

    /**
     * Convert unix time to MySQL date time with specified zone id, the result will affected by the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @param zoneId        zone id
     * @return MySQL date time string
     */
    public static String convertUnixTimestampToMySqlDateTime(final String unixTimestamp, ZoneId zoneId) {
        Instant instant = Instant.ofEpochSecond(Long.valueOf(unixTimestamp));
        return getDateTimeFormatter(MYSQL_DATE_TIME_FORMAT).format(instant.atZone(zoneId));
    }

    /**
     * Shift the time zone of the timestamp to UTC+8
     *
     * @param iso8601 ISO-8601 time string
     * @return ISO-8601 time string
     */
    public static String shiftTimeZoneForIso8601Timestamp(final String iso8601) {
        return shiftTimeZoneForIso8601Timestamp(iso8601, DEFAULT_ZONE_ID);
    }

    /**
     * Shift the time zone of the timestamp to specified time zone
     *
     * @param iso8601 ISO-8601 time string
     * @param zoneId  zone id
     * @return ISO-8601 time string
     */
    public static String shiftTimeZoneForIso8601Timestamp(final String iso8601, final ZoneId zoneId) {
        return optionalConvertUnixTimestampToIso8601(optionalConvertIso8601ToUnixTimestamp(iso8601), zoneId);
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
        Instant instantA = Instant.ofEpochSecond(Long.valueOf(convertIso8601ToUnixTimestamp(iso8601StringA)));
        Instant instantB = Instant.ofEpochSecond(Long.valueOf(convertIso8601ToUnixTimestamp(iso8601StringB)));
        return instantA.compareTo(instantB);
    }

    /**
     * Compare two local iso8601 strings.
     *
     * @param localIso8601A input iso8601 string A
     * @param localIso8601B input iso8601 string B
     * @return compared result in int format
     */
    public static int compareLocalIso8601(final String localIso8601A, final String localIso8601B) {
        DateTimeFormatter formatter = getDateTimeFormatter(DEFAULT_DATE_FORMAT);
        LocalDateTime localDateTime1 = LocalDateTime.parse(localIso8601A, formatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse(localIso8601B, formatter);
        return localDateTime1.compareTo(localDateTime2);
    }

    private static DateTimeFormatter getDateTimeFormatter(String dateTimeFormat) {
        return DateTimeFormatter.ofPattern(dateTimeFormat);
    }

}
