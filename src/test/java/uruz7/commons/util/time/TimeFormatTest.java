package uruz7.commons.util.time;

import org.junit.Test;

import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Emily Hsieh, Carl Lu
 */
public class TimeFormatTest {

    @Test(expected = DateTimeParseException.class)
    public void testConvertIso8601ToUnixTimestampWithParseException() {
        String invalidIso8601Input = "2017-08-0823:25:51Z";
        TimeFormat.convert(invalidIso8601Input, TimeFormat.ISO8601UTC, TimeFormat.UNIX_TIME);
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertUnixTimestampToIso8601WithInvalidInput() {
        String invalidUnixTimestampInput = "Que Pa So!";
        TimeFormat.convert(invalidUnixTimestampInput, TimeFormat.UNIX_TIME, TimeFormat.ISO8601TW);
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertUnixTimestampToMySqlDateTimeWithInvalidInput() {
        String invalidUnixTimestampInput = "For Shizzle My Nizzle!";
        TimeFormat.convert(invalidUnixTimestampInput, TimeFormat.UNIX_TIME, TimeFormat.MYSQL_DATE_TIME);
    }

    @Test
    public void testShiftTimeZoneForIso8601TimestampWithEmptyInput() {
        String input = "";
        String actual = TimeFormat.convert(input, TimeFormat.UNIX_TIME, TimeFormat.ISO8601TW);
        assertNull(actual);
    }

    @Test
    public void testShiftTimeZoneForIso8601TimestampWithNullInput() {
        assertNull(TimeFormat.convert(null, TimeFormat.UNIX_TIME, TimeFormat.ISO8601TW));
    }

    @Test
    public void testIso8601UTCTimeToIso8601TWTime() {
        String iso8601Time = "2017-07-27T15:41:00Z";
        String actual = TimeFormat.convert(iso8601Time, TimeFormat.ISO8601UTC, TimeFormat.ISO8601TW);
        String expected = "2017-07-27T23:41:00+08:00";
        assertEquals(expected, actual);
    }

    @Test
    public void testIso8601TWTimeToIso8601UTCTime() {
        String iso8601TWTime = "2017-07-27T23:41:00+08:00";
        String actual = TimeFormat.convert(iso8601TWTime, TimeFormat.ISO8601TW, TimeFormat.ISO8601UTC);
        String expected = "2017-07-27T15:41:00Z";
        assertEquals(expected, actual);
    }

    @Test
    public void testIso8601UTCTimeToIso8601UTCTime() {
        String iso8601Time = "2017-07-27T15:41:00Z";
        String actual = TimeFormat.convert(iso8601Time, TimeFormat.ISO8601UTC, TimeFormat.ISO8601UTC);
        String expected = "2017-07-27T15:41:00Z";
        assertEquals(expected, actual);
    }

    @Test
    public void testIso8601UTCTimeToDBDateTime() {
        String iso8601Time = "2017-07-27T15:41:00Z";
        String actual = TimeFormat.convert(iso8601Time, TimeFormat.ISO8601UTC, TimeFormat.DB_DATE_TIME);
        String expected = "2017-07-27 23:41:00.0";
        assertEquals(expected, actual);
    }

    @Test
    public void testIso8601UTCTimeToMySqlDateTime() {
        String iso8601Time = "2017-07-27T15:41:00Z";
        String actual = TimeFormat.convert(iso8601Time, TimeFormat.ISO8601UTC, TimeFormat.MYSQL_DATE_TIME);
        String expected = "2017-07-27 23:41:00";
        assertEquals(expected, actual);
    }

    @Test
    public void testIso8601UTCTimeToUnixTime() {
        String iso8601Time = "2017-07-27T15:41:00Z";
        String actual = TimeFormat.convert(iso8601Time, TimeFormat.ISO8601UTC, TimeFormat.UNIX_TIME);
        String expected = "1501170060";
        assertEquals(expected, actual);
    }

    @Test
    public void testDBDateTimeToIso8601TWTime() {
        String dbDateTime = "2017-07-27 23:41:00.0";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.DB_DATE_TIME, TimeFormat.ISO8601TW);
        String expected = "2017-07-27T23:41:00+08:00";
        assertEquals(expected, actual);
    }

    @Test
    public void testDBDateTimeToIso8601UTCTime() {
        String dbDateTime = "2017-07-27 23:41:00.0";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.DB_DATE_TIME, TimeFormat.ISO8601UTC);
        String expected = "2017-07-27T15:41:00Z";
        assertEquals(expected, actual);
    }

    @Test
    public void testDBDateTimeToDbDateTime() {
        String dbDateTime = "2017-07-27 23:41:00.0";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.DB_DATE_TIME, TimeFormat.DB_DATE_TIME);
        String expected = "2017-07-27 23:41:00.0";
        assertEquals(expected, actual);
    }

    @Test
    public void testDBDateTimeToMySqlDateTime() {
        String dbDateTime = "2017-07-27 23:41:00.0";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.DB_DATE_TIME, TimeFormat.MYSQL_DATE_TIME);
        String expected = "2017-07-27 23:41:00";
        assertEquals(expected, actual);
    }

    @Test
    public void testDBDateTimeToUnixTime() {
        String dbDateTime = "2017-07-27 23:41:00.0";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.DB_DATE_TIME, TimeFormat.UNIX_TIME);
        String expected = "1501170060";
        assertEquals(expected, actual);
    }

    @Test
    public void testMySqlDateTimeToIso8601TWTime() {
        String dbDateTime = "2017-07-27 23:41:00";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.MYSQL_DATE_TIME, TimeFormat.ISO8601TW);
        String expected = "2017-07-27T23:41:00+08:00";
        assertEquals(expected, actual);
    }

    @Test
    public void testMySqlDateTimeToIso8601UTCTime() {
        String dbDateTime = "2017-07-27 23:41:00";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.MYSQL_DATE_TIME, TimeFormat.ISO8601UTC);
        String expected = "2017-07-27T15:41:00Z";
        assertEquals(expected, actual);
    }

    @Test
    public void testMySqlDateTimeToDbDateTime() {
        String dbDateTime = "2017-07-27 23:41:00";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.MYSQL_DATE_TIME, TimeFormat.DB_DATE_TIME);
        String expected = "2017-07-27 23:41:00.0";
        assertEquals(expected, actual);
    }

    @Test
    public void testMySqlDateTimeToMySqlDateTime() {
        String dbDateTime = "2017-07-27 23:41:00";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.MYSQL_DATE_TIME, TimeFormat.MYSQL_DATE_TIME);
        String expected = "2017-07-27 23:41:00";
        assertEquals(expected, actual);
    }

    @Test
    public void testMySqlDateTimeToUnixTime() {
        String dbDateTime = "2017-07-27 23:41:00";
        String actual = TimeFormat.convert(dbDateTime, TimeFormat.MYSQL_DATE_TIME, TimeFormat.UNIX_TIME);
        String expected = "1501170060";
        assertEquals(expected, actual);
    }

    @Test
    public void testUnixTimeToIso8601TWTime() {
        String unixTimeString = "1501170060";
        String actual = TimeFormat.convert(unixTimeString, TimeFormat.UNIX_TIME, TimeFormat.ISO8601TW);
        String expected = "2017-07-27T23:41:00+08:00";
        assertEquals(expected, actual);
    }

    @Test
    public void testUnixTimeToIso8601UTCTime() {
        String unixTimeString = "1501170060";
        String actual = TimeFormat.convert(unixTimeString, TimeFormat.UNIX_TIME, TimeFormat.ISO8601UTC);
        String expected = "2017-07-27T15:41:00Z";
        assertEquals(expected, actual);
    }

    @Test
    public void testUnixTimeToDbDateTime() {
        String unixTimeString = "1501170060";
        String actual = TimeFormat.convert(unixTimeString, TimeFormat.UNIX_TIME, TimeFormat.DB_DATE_TIME);
        String expected = "2017-07-27 23:41:00.0";
        assertEquals(expected, actual);
    }

    @Test
    public void testUnixTimeToMySqlTime() {
        String unixTimeString = "1501170060";
        String actual = TimeFormat.convert(unixTimeString, TimeFormat.UNIX_TIME, TimeFormat.MYSQL_DATE_TIME);
        String expected = "2017-07-27 23:41:00";
        assertEquals(expected, actual);
    }

    @Test
    public void testUnixTimeToUnixTime() {
        String unixTimeString = "1501170060";
        String actual = TimeFormat.convert(unixTimeString, TimeFormat.UNIX_TIME, TimeFormat.UNIX_TIME);
        String expected = "1501170060";
        assertEquals(expected, actual);
    }

}
