package uruz7.commons.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Carl Lu
 * <p>
 * To get available time zone ids, please invoke TimeZone.getAvailableIDs().
 * <p>
 * TODO: Wrap methods so that users can pass in specified [date format]/[locale]/[time zone]
 */
public final class Iso8601TimeUtil {

    private static final long MILLISECOND = 1000L;
    private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("Asia/Taipei");
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private static final String DEFAULT_DATE_FORMAT_WITH_MILLISECOND = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String MYSQL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Constructor
     */
    private Iso8601TimeUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Convert ISO-8601 to Unix timestamp, will not apply the time zone.
     *
     * @param iso8601 ISO-8601 time
     * @return String
     * @throws ParseException parse exception
     */
    public static String convertIso8601ToUnixTimestamp(final String iso8601) throws ParseException {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.TAIWAN, DEFAULT_TIME_ZONE);
        try {
            return String.valueOf(simpleDateFormat.parse(iso8601).getTime() / MILLISECOND);
        } catch (ParseException parseException) {
            final SimpleDateFormat formatWithMs = new SimpleDateFormat(DEFAULT_DATE_FORMAT_WITH_MILLISECOND, Locale.TAIWAN);
            return String.valueOf(formatWithMs.parse(iso8601).getTime() / MILLISECOND);
        }
    }

    /**
     * Optional input for converter.
     *
     * @param input ISO-8601 time string
     * @return null if the input is empty, otherwise, it will be a unit timestamp
     * @throws ParseException parse exception
     */
    public static String optionalConvertIso8601ToUnixTimestamp(final String input) throws ParseException {
        if (null == input) {
            return null;
        } else if ("".equals(input)) {
            return null;
        } else {
            return convertIso8601ToUnixTimestamp(input);
        }
    }

    /**
     * Convert unix time to ISO8601, the result will apply the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @return ISO-8601 time string
     */
    public static String convertUnixTimestampToIso8601(final String unixTimestamp) {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.TAIWAN, DEFAULT_TIME_ZONE);
        return parseUnixTimestampToStringByDateFormat(simpleDateFormat, unixTimestamp);
    }

    /**
     * Convert unix time to MySQL date time, the result will apply the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @return MySQL date time string
     */
    public static String convertUnixTimestampToMySqlDateTime(final String unixTimestamp) {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(MYSQL_DATE_TIME_FORMAT, Locale.TAIWAN, DEFAULT_TIME_ZONE);
        return parseUnixTimestampToStringByDateFormat(simpleDateFormat, unixTimestamp);
    }

    /**
     * Get current unix timestamp
     *
     * @return current unix timestamp
     */
    public static String getCurrentUnixTimestamp() {
        return String.valueOf(System.currentTimeMillis() / MILLISECOND);
    }

    /**
     * Compare two iso8601 strings
     *
     * @param iso8601StringA input iso8601 string A
     * @param iso8601StringB input iso8601 string B
     * @return compared result in int format
     * @throws ParseException
     */
    public static int compare(final String iso8601StringA, final String iso8601StringB) throws ParseException {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.TAIWAN, DEFAULT_TIME_ZONE);
        return simpleDateFormat.parse(iso8601StringA).compareTo(simpleDateFormat.parse(iso8601StringB));
    }

    /**
     * Convert unix timestamp to custom format
     *
     * @param unixTimestamp input unix timestamp
     * @param customFormat  custom timestamp format
     * @return timestamp in custom format
     */
    public static String convertUnixTimestampToCustomFormat(final String unixTimestamp, final String customFormat) {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(customFormat, Locale.TAIWAN, DEFAULT_TIME_ZONE);
        return parseUnixTimestampToStringByDateFormat(simpleDateFormat, unixTimestamp);
    }

    private static SimpleDateFormat getSimpleDateFormat(String dateFormat, Locale locale, TimeZone timeZone) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, locale);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat;
    }

    private static String parseUnixTimestampToStringByDateFormat(final SimpleDateFormat simpleDateFormat,
            final String unixTimestamp) {
        return simpleDateFormat.format(new Date(Long.parseLong(unixTimestamp) * MILLISECOND));
    }

}
