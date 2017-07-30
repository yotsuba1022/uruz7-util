package uruz7.commons.util.time;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * @author Carl Lu
 */
public class Iso8601TimeUtilTest {

    @Test(expected = InvocationTargetException.class)
    public void testPrivateConstructor()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Iso8601TimeUtil> constructor = Iso8601TimeUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testConvertIso8601ToUnixTimestamp() throws ParseException {
        //input in long format: 1501025150000
        String iso8601 = "2017-07-25T23:25:50Z";
        String expected = "1501025150";
        String actual = Iso8601TimeUtil.convertIso8601ToUnixTimestamp(iso8601);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertIso8601ToUnixTimestampWithMillisecond() throws ParseException {
        String iso8601 = "2017-07-25T23:25:51.777Z";
        String expected = "1501025151";
        String actual = Iso8601TimeUtil.convertIso8601ToUnixTimestamp(iso8601);
        assertEquals(expected, actual);
    }

    @Test(expected = ParseException.class)
    public void testConvertIso8601ToUnixTimestampWithParseException() throws ParseException {
        String invalidIso8601Input = "2017-07-2523:25:51Z";
        Iso8601TimeUtil.convertIso8601ToUnixTimestamp(invalidIso8601Input);
    }

    @Test
    public void testConvertIso8601ToUnixTimestampWithSpecifiedParams() throws ParseException {
        String iso8601 = "2017-07-30T03Z";
        String dateFormat = "yyyy-MM-dd'T'HHXXX";
        String expected = "1501383600";
        String actual = Iso8601TimeUtil.convertIso8601ToUnixTimestamp(iso8601, dateFormat, Locale.CANADA,
                TimeZone.getTimeZone("Canada/Pacific"));
        assertEquals(expected, actual);
    }

    @Test
    public void testOptionalConvertIso8601ToUnixTimestampWithNullReturned() throws ParseException {
        assertNull(Iso8601TimeUtil.optionalConvertIso8601ToUnixTimestamp(null));
        String emptyInput = "";
        String expectedNull = Iso8601TimeUtil.optionalConvertIso8601ToUnixTimestamp(emptyInput);
        assertNull(expectedNull);
    }

    @Test
    public void testOptionalConvertIso8601ToUnixTimestampWithIso8601InputString() throws ParseException {
        String iso8601 = "2017-07-26T23:30:51Z";
        String expected = "1501111851";
        String actual = Iso8601TimeUtil.optionalConvertIso8601ToUnixTimestamp(iso8601);
        assertEquals(expected, actual);
    }

    @Test(expected = ParseException.class)
    public void testOptionalConvertIso8601ToUnixTimestampWithParseException() throws ParseException {
        String invalidIso8601Input = "2017-07-2623:30:51Z";
        Iso8601TimeUtil.optionalConvertIso8601ToUnixTimestamp(invalidIso8601Input);
    }

    @Test
    public void testConvertUnixTimestampToIso8601() {
        // Which means: 2017-07-27T15:41:00Z
        String unixTimestampInput = "1501170060";
        String expected = "2017-07-27T23:41:00+08:00";
        String actual = Iso8601TimeUtil.convertUnixTimestampToIso8601(unixTimestampInput);
        assertEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertUnixTimestampToIso8601WithInvalidInput() {
        String invalidUnixTimestampInput = "Que Pa So!";
        Iso8601TimeUtil.convertUnixTimestampToIso8601(invalidUnixTimestampInput);
    }

    @Test
    public void testConvertUnixTimestampToMySqlDateTime() {
        // Which means: 2017-07-27T15:41:00Z
        String unixTimestampInput = "1501170060";
        String expected = "2017-07-27 23:41:00";
        String actual = Iso8601TimeUtil.convertUnixTimestampToMySqlDateTime(unixTimestampInput);
        assertEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertUnixTimestampToMySqlDateTimeWithInvalidInput() {
        String invalidUnixTimestampInput = "For Shizzle My Nizzle!";
        Iso8601TimeUtil.convertUnixTimestampToMySqlDateTime(invalidUnixTimestampInput);
    }

    @Test
    public void testGetCurrentUnixTimestamp() {
        String expected = Iso8601TimeUtil.getCurrentUnixTimestamp();
        String actual = Iso8601TimeUtil.getCurrentUnixTimestamp();
        assertEquals(expected, actual);
    }

    @Test
    public void testCompareThatWillGreaterThanZero() throws ParseException {
        String earlier = "2017-07-28T13:45:00Z";
        String later = "2017-07-28T21:45:00Z";
        assertTrue(Iso8601TimeUtil.compare(later, earlier) > 0);
    }

    @Test
    public void testCompareThatWillEqualsToZero() throws ParseException {
        String same = "2017-07-28T13:45:00Z";
        assertTrue(Iso8601TimeUtil.compare(same, same) == 0);
    }

    @Test
    public void testCompareThatWillLessThanZeo() throws ParseException {
        String earlier = "2017-07-28T13:45:00Z";
        String later = "2017-07-28T21:45:00Z";
        assertTrue(Iso8601TimeUtil.compare(earlier, later) < 0);
    }

    @Test(expected = ParseException.class)
    public void testCompareWithInvalidInput() throws ParseException {
        String invalidUnixTimestampInput = "For Shizzle My Nizzle!";
        Iso8601TimeUtil.compare(invalidUnixTimestampInput, invalidUnixTimestampInput);
    }

    @Test
    public void testConvertUnixTimestampToCustomFormat() {
        String unixTimestamp = "1501307211";
        String customFormat = "yyyy-MM-dd HH:mmXXX";
        String expected = "2017-07-29 13:46+08:00";
        String actual = Iso8601TimeUtil.convertUnixTimestampToCustomFormat(unixTimestamp, customFormat);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertUnixTimestampToCustomFormatWithInvalidFormat() {
        String unixTimestamp = "1501307211";
        String customFormat = "yyMMdd que pa so";
        Iso8601TimeUtil.convertUnixTimestampToCustomFormat(unixTimestamp, customFormat);
    }

}
