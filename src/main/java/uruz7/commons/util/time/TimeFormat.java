package uruz7.commons.util.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Emily Hsieh, Carl Lu
 */
public enum TimeFormat implements TimeFormatable {

    UNIX_TIME {
        @Override
        public Instant getInstant(String timestamp) {
            return Instant.ofEpochSecond(Long.valueOf(timestamp));
        }

        @Override
        public String getTimestampString(Instant instant) {
            return String.valueOf(instant.getEpochSecond());
        }
    }, ISO8601UTC {
        @Override
        public Instant getInstant(String timestamp) {
            return Instant.parse(timestamp);
        }

        @Override
        public String getTimestampString(Instant instant) {
            return getDateTimeFormatter(iso8601DateFormat).format(instant.atZone(ZoneOffset.UTC));
        }
    }, ISO8601TW {
        @Override
        public Instant getInstant(String timestamp) {
            long epochSecond = convertTimestampToEpochSecond(timestamp, iso8601DateFormat);
            return Instant.ofEpochSecond(epochSecond);
        }

        @Override
        public String getTimestampString(Instant instant) {
            return getDateTimeFormatter(iso8601DateFormat).format(instant.atZone(defaultZoneId));
        }
    }, DB_DATE_TIME {
        @Override
        public Instant getInstant(String timestamp) {
            long epochSecond = convertTimestampToEpochSecond(timestamp, dbDateTimeFormat);
            return Instant.ofEpochSecond(epochSecond);
        }

        @Override
        public String getTimestampString(Instant instant) {
            return getDateTimeFormatter(dbDateTimeFormat).format(instant.atZone(defaultZoneId));
        }
    }, MYSQL_DATE_TIME {
        @Override
        public Instant getInstant(String timestamp) {
            long epochSecond = convertTimestampToEpochSecond(timestamp, mysqlDateTimeFormat);
            return Instant.ofEpochSecond(epochSecond);
        }

        @Override
        public String getTimestampString(Instant instant) {
            return getDateTimeFormatter(mysqlDateTimeFormat).format(instant.atZone(defaultZoneId));
        }
    };

    private final static String iso8601DateFormat = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private final static String dbDateTimeFormat = "yyyy-MM-dd HH:mm:ss.S";
    private final static String mysqlDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private final static ZoneId defaultZoneId = ZoneId.of("Asia/Taipei");

    public static String convert(String timestamp, TimeFormat fromType, TimeFormat toType) {
        if (null == timestamp || "".equals(timestamp)) {
            return null;
        }
        Instant instant = fromType.getInstant(timestamp);
        return toType.getTimestampString(instant);
    }

    private static DateTimeFormatter getDateTimeFormatter(String dateTimeFormat) {
        return DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    protected long convertTimestampToEpochSecond(String timestamp, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format, Locale.TAIWAN);
        LocalDateTime localDateTime = LocalDateTime.parse(timestamp, dateTimeFormatter);
        return localDateTime.atZone(defaultZoneId).toEpochSecond();
    }

}
