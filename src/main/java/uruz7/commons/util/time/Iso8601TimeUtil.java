package uruz7.commons.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Carl Lu
 */
public class Iso8601TimeUtil {

    private static final long MILLISECOND = 1000L;
    private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("Asia/Taipei");
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private static final String DEFAULT_DATE_FORMAT_WITH_MILLISECOND = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * Constructor
     */
    private Iso8601TimeUtil() {

    }

    /**
     * Convert ISO-8601 to Unix timestamp.
     *
     * @param iso8601 ISO-8601 time
     * @return String
     * @throws ParseException parse exception
     */
    public static String convertIso8601ToUnixTimestamp(final String iso8601) throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.TAIWAN);
        simpleDateFormat.setTimeZone(DEFAULT_TIME_ZONE);
        try {
            return String.valueOf(simpleDateFormat.parse(iso8601).getTime() / MILLISECOND);
        } catch (ParseException parseException) {
            final SimpleDateFormat formatWithMs = new SimpleDateFormat(DEFAULT_DATE_FORMAT_WITH_MILLISECOND, Locale.TAIWAN);
            return String.valueOf(formatWithMs.parse(iso8601).getTime() / MILLISECOND);
        }
    }

}
