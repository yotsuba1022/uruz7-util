package uruz7.commons.util.time;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

/**
 * @author Carl Lu
 */
public class JdkIso8601TimeUtilTest {

    @Test(expected = InvocationTargetException.class)
    public void testPrivateConstructor()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<JdkIso8601TimeUtil> constructor = JdkIso8601TimeUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testConvertIso8601ToUnixTimestamp() {
        String iso8601 = "2017-08-08T13:59:17Z";
        String expected = "1502200757";
        String actual = JdkIso8601TimeUtil.convertIso8601ToUnixTimestamp(iso8601);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertIso8601ToUnixTimestampWithMillisecond() {
        String iso8601 = "2017-08-08T13:59:17.777Z";
        String expected = "1502200757";
        String actual = JdkIso8601TimeUtil.convertIso8601ToUnixTimestamp(iso8601);
        assertEquals(expected, actual);
    }

    @Test(expected = DateTimeParseException.class)
    public void testConvertIso8601ToUnixTimestampWithParseException() {
        String invalidIso8601Input = "2017-08-0823:25:51Z";
        JdkIso8601TimeUtil.convertIso8601ToUnixTimestamp(invalidIso8601Input);
    }

    @Test
    public void testOptionalConvertIso8601ToUnixTimestampWithNullReturned() {
        assertNull(JdkIso8601TimeUtil.optionalConvertIso8601ToUnixTimestamp(null));
        String emptyInput = "";
        String expectedNull = JdkIso8601TimeUtil.optionalConvertIso8601ToUnixTimestamp(emptyInput);
        assertNull(expectedNull);
    }

    @Test
    public void testOptionalConvertIso8601ToUnixTimestampWithIso8601InputString() {
        String iso8601 = "2017-08-08T13:59:17Z";
        String expected = "1502200757";
        String actual = JdkIso8601TimeUtil.optionalConvertIso8601ToUnixTimestamp(iso8601);
        assertEquals(expected, actual);
    }

    @Test(expected = DateTimeParseException.class)
    public void testOptionalConvertIso8601ToUnixTimestampWithParseException() {
        String invalidIso8601Input = "2017-08-0823:25:51Z";
        JdkIso8601TimeUtil.optionalConvertIso8601ToUnixTimestamp(invalidIso8601Input);
    }

    @Test
    public void testConvertUnixTimestampToIso8601() {
        // Which means: 2017-07-27T15:41:00Z
        String unixTimestampInput = "1501170060";
        String expected = "2017-07-27T23:41:00+08:00";
        String actual = JdkIso8601TimeUtil.convertUnixTimestampToIso8601(unixTimestampInput);
        assertEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertUnixTimestampToIso8601WithInvalidInput() {
        String invalidUnixTimestampInput = "Que Pa So!";
        JdkIso8601TimeUtil.convertUnixTimestampToIso8601(invalidUnixTimestampInput);
    }

    @Test
    public void testConvertUnixTimestampToMySqlDateTime() {
        // Which means: 2017-07-27T15:41:00Z
        String unixTimestampInput = "1501170060";
        String expected = "2017-07-27 23:41:00";
        String actual = JdkIso8601TimeUtil.convertUnixTimestampToMySqlDateTime(unixTimestampInput);
        assertEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertUnixTimestampToMySqlDateTimeWithInvalidInput() {
        String invalidUnixTimestampInput = "For Shizzle My Nizzle!";
        JdkIso8601TimeUtil.convertUnixTimestampToMySqlDateTime(invalidUnixTimestampInput);
    }

    @Test
    public void testGetCurrentUnixTimestamp() {
        String expected = JdkIso8601TimeUtil.getCurrentUnixTimestamp();
        String actual = JdkIso8601TimeUtil.getCurrentUnixTimestamp();
        assertEquals(expected, actual);
    }

    @Test
    public void testCompareThatWillGreaterThanZero() {
        String earlier = "2017-08-08T13:45:00Z";
        String later = "2017-08-08T21:45:00Z";
        assertTrue(JdkIso8601TimeUtil.compare(later, earlier) > 0);
    }

    @Test
    public void testCompareThatWillEqualsToZero() {
        String same = "2017-08-08T13:45:00Z";
        assertTrue(JdkIso8601TimeUtil.compare(same, same) == 0);
    }

    @Test
    public void testCompareThatWillLessThanZeo() {
        String earlier = "2017-08-08T13:45:00Z";
        String later = "2017-08-08T21:45:00Z";
        assertTrue(JdkIso8601TimeUtil.compare(earlier, later) < 0);
    }

    @Test(expected = DateTimeParseException.class)
    public void testCompareWithInvalidInput() {
        String invalidUnixTimestampInput = "For Shizzle My Nizzle!";
        JdkIso8601TimeUtil.compare(invalidUnixTimestampInput, invalidUnixTimestampInput);
    }

    @Test
    public void testShiftTimeZoneForIso8601Timestamp() {
        String input = "2017-08-08T16:00:00Z";
        String expected = "2017-08-09T00:00:00+08:00";
        String actual = JdkIso8601TimeUtil.shiftTimeZoneForIso8601Timestamp(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testShiftTimeZoneForIso8601TimestampWithEmptyInput() {
        String input = "";
        String actual = JdkIso8601TimeUtil.shiftTimeZoneForIso8601Timestamp(input);
        assertNull(actual);
    }

    @Test
    public void testShiftTimeZoneForIso8601TimestampWithNullInput() {
        String actual = JdkIso8601TimeUtil.shiftTimeZoneForIso8601Timestamp(null);
        assertNull(actual);
    }
}
