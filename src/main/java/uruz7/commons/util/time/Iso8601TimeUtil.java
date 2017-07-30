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
     * Convert ISO-8601 to Unix timestamp, will not affected by the time zone.
     * Notice that the input timestamp should match the date time format, the default format is: yyyy-MM-dd'T'HH:mm:ssXXX
     *
     * @param iso8601 ISO-8601 time
     * @return unix timestamp string
     * @throws ParseException parse exception
     */
    public static String convertIso8601ToUnixTimestamp(final String iso8601) throws ParseException {
        return convertIso8601ToUnixTimestamp(iso8601, DEFAULT_DATE_FORMAT, Locale.TAIWAN, DEFAULT_TIME_ZONE);
    }

    /**
     * Convert ISO-8601 to Unix timestamp with specified parameters, will not affected by the time zone.
     *
     * @param iso8601    ISO-8601 time
     * @param dateFormat specific date format
     * @param locale     specific local
     * @param timeZone   specific time zone
     * @return unix timestamp string
     * @throws ParseException parse exception
     */
    public static String convertIso8601ToUnixTimestamp(final String iso8601, final String dateFormat, final Locale locale,
            final TimeZone timeZone) throws ParseException {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(dateFormat, locale, timeZone);
        try {
            return String.valueOf(simpleDateFormat.parse(iso8601).getTime() / MILLISECOND);
        } catch (ParseException parseException) {
            final SimpleDateFormat formatWithMs = new SimpleDateFormat(DEFAULT_DATE_FORMAT_WITH_MILLISECOND, locale);
            return String.valueOf(formatWithMs.parse(iso8601).getTime() / MILLISECOND);
        }
    }

    /**
     * Optional input for converter.
     *
     * @param iso8601 ISO-8601 time string
     * @return null if the iso8601 input is empty, otherwise, it will be a unit timestamp
     * @throws ParseException parse exception
     */
    public static String optionalConvertIso8601ToUnixTimestamp(final String iso8601) throws ParseException {
        return optionalConvertIso8601ToUnixTimestamp(iso8601, DEFAULT_DATE_FORMAT, Locale.TAIWAN, DEFAULT_TIME_ZONE);
    }

    /**
     * Optional input for converter with specified parameters.
     *
     * @param iso8601    ISO-8601 time
     * @param dateFormat specific date format
     * @param locale     specific local
     * @param timeZone   specific time zone
     * @return null if the iso8601 input is empty, otherwise, it will be a unit timestamp
     * @throws ParseException parse exception
     */
    public static String optionalConvertIso8601ToUnixTimestamp(final String iso8601, final String dateFormat, final Locale locale,
            final TimeZone timeZone) throws ParseException {
        if (null == iso8601) {
            return null;
        } else if ("".equals(iso8601)) {
            return null;
        } else {
            return convertIso8601ToUnixTimestamp(iso8601, dateFormat, locale, timeZone);
        }
    }

    /**
     * Convert unix time to ISO8601, the result will affected by the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @return ISO-8601 time string
     */
    public static String convertUnixTimestampToIso8601(final String unixTimestamp) {
        return convertUnixTimestampToIso8601(unixTimestamp, DEFAULT_DATE_FORMAT, Locale.TAIWAN, DEFAULT_TIME_ZONE);
    }

    /**
     * Convert unix time to ISO8601 with specified parameters, the result will affected by the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @param dateFormat    specific date format
     * @param locale        specific local
     * @param timeZone      specific time zone
     * @return ISO-8601 time string
     */
    public static String convertUnixTimestampToIso8601(final String unixTimestamp, final String dateFormat, final Locale locale,
            final TimeZone timeZone) {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(dateFormat, locale, timeZone);
        return parseUnixTimestampToStringByDateFormat(simpleDateFormat, unixTimestamp);
    }

    /**
     * Convert unix time to MySQL date time, the result will affected by the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @return MySQL date time string
     */
    public static String convertUnixTimestampToMySqlDateTime(final String unixTimestamp) {
        return convertUnixTimestampToMySqlDateTime(unixTimestamp, Locale.TAIWAN, DEFAULT_TIME_ZONE);
    }

    /**
     * Convert unix time to MySQL date time with specified parameters, the result will affected by the time zone.
     *
     * @param unixTimestamp unix timestamp
     * @param locale        specific local
     * @param timeZone      specific time zone
     * @return MySQL date time string
     */
    public static String convertUnixTimestampToMySqlDateTime(final String unixTimestamp, final Locale locale,
            final TimeZone timeZone) {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(MYSQL_DATE_TIME_FORMAT, locale, timeZone);
        return parseUnixTimestampToStringByDateFormat(simpleDateFormat, unixTimestamp);
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
     * @throws ParseException
     */
    public static int compare(final String iso8601StringA, final String iso8601StringB) throws ParseException {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.TAIWAN, DEFAULT_TIME_ZONE);
        return simpleDateFormat.parse(iso8601StringA).compareTo(simpleDateFormat.parse(iso8601StringB));
    }

    /**
     * Convert unix timestamp to custom format.
     *
     * @param unixTimestamp input unix timestamp
     * @param customFormat  custom timestamp format
     * @return timestamp in custom format
     */
    public static String convertUnixTimestampToCustomFormat(final String unixTimestamp, final String customFormat) {
        return convertUnixTimestampToCustomFormat(unixTimestamp, customFormat, Locale.TAIWAN, DEFAULT_TIME_ZONE);
    }

    /**
     * Convert unix timestamp to custom format with specified parameters.
     *
     * @param unixTimestamp input unix timestamp
     * @param customFormat  specific date format
     * @param locale        specific local
     * @param timeZone      specific time zone
     * @return timestamp in custom format
     */
    public static String convertUnixTimestampToCustomFormat(final String unixTimestamp, final String customFormat,
            final Locale locale, final TimeZone timeZone) {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormat(customFormat, locale, timeZone);
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
