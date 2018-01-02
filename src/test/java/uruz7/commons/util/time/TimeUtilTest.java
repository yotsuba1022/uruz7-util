package uruz7.commons.util.time;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

/**
 * @author Emily Hsieh, Carl Lu
 */
public class TimeUtilTest {

    @Test(expected = InvocationTargetException.class)
    public void testPrivateConstructor()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<TimeUtil> constructor = TimeUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testGetCurrentUnixTimestamp() {
        String expected = String.valueOf(System.currentTimeMillis() / 1000L);
        String actual = TimeUtil.getCurrentUnixTimestamp();
        assertEquals(expected, actual);
    }

    @Test
    public void testCompareThatWillGreaterThanZero() {
        String earlier = "2017-08-08T13:45:00Z";
        String later = "2017-08-08T21:45:00Z";
        assertTrue(TimeUtil.compare(later, earlier) > 0);
    }

    @Test
    public void testCompareThatWillEqualsToZero() {
        String same = "2017-08-08T13:45:00Z";
        assertTrue(TimeUtil.compare(same, same) == 0);
    }

    @Test
    public void testCompareThatWillLessThanZeo() {
        String earlier = "2017-08-08T13:45:00Z";
        String later = "2017-08-08T21:45:00Z";
        assertTrue(TimeUtil.compare(earlier, later) < 0);
    }

    @Test(expected = DateTimeParseException.class)
    public void testCompareWithInvalidInput() {
        String invalidUnixTimestampInput = "For Shizzle My Nizzle!";
        TimeUtil.compare(invalidUnixTimestampInput, invalidUnixTimestampInput);
    }

    @Test
    public void testCompareLocalIso8601ThatWillGreaterThanZero() {
        String earlier = "2017-08-08T13:45:00+08:00";
        String later = "2017-08-08T21:45:00+08:00";
        assertTrue(TimeUtil.compareLocalIso8601(later, earlier) > 0);
    }

    @Test
    public void testCompareLocalIso8601ThatWillEqualsToZero() {
        String same = "2017-08-08T13:45:00+08:00";
        assertTrue(TimeUtil.compareLocalIso8601(same, same) == 0);
    }

    @Test
    public void testCompareLocalIso8601ThatWillLessThanZero() {
        String earlier = "2017-08-08T13:45:00+08:00";
        String later = "2017-08-08T21:45:00+08:00";
        assertTrue(TimeUtil.compareLocalIso8601(earlier, later) < 0);
    }

    @Test
    public void testCompareLocalIso8601ThatAreInDifferentTimeZoneAndWillGreaterThanZero() {
        String earlier = "2017-08-08T13:45:00-10:00";
        String later = "2017-08-08T13:46:00+07:00";
        assertTrue(TimeUtil.compareLocalIso8601(later, earlier) > 0);
    }

    @Test
    public void testCompareLocalIso8601ThatAreInDifferentTimeZoneAndWillEqualsToZero() {
        String earlier = "2017-08-08T13:45:00-03:00";
        String later = "2017-08-08T13:45:00+07:00";
        assertTrue(TimeUtil.compareLocalIso8601(earlier, later) == 0);
    }

    @Test
    public void testCompareLocalIso8601ThatAreInDifferentTimeZoneAndWillLessThanZero() {
        String earlier = "2017-08-08T13:44:59-09:00";
        String later = "2017-08-08T13:45:00+02:00";
        assertTrue(TimeUtil.compareLocalIso8601(earlier, later) < 0);
    }

    @Test(expected = DateTimeParseException.class)
    public void testCompareLocalIso8601WithInvalidInput() {
        String invalidUnixTimestampInput = "For Shizzle My Nizzle!";
        TimeUtil.compareLocalIso8601(invalidUnixTimestampInput, invalidUnixTimestampInput);
    }

    @Test(expected = DateTimeParseException.class)
    public void testCompareLocalIso8601WithPartialInvalidInput() {
        String invalidFormatA = "2017-08-08T13:44:59-09:00.999";
        String invalidFormatB = "2017-08-08T13:45:00+2:00";
        TimeUtil.compareLocalIso8601(invalidFormatA, invalidFormatB);
    }

    @Test
    public void testTimeZoneInUtc8() {
        String input = "2017-08-08T13:45:00+08:00";
        assertTrue(TimeUtil.isTimeZoneInUtc8(input));
    }

    @Test
    public void testTimeZoneNotInUtc8() {
        String input = "2017-08-08T13:45:00Z";
        assertFalse(TimeUtil.isTimeZoneInUtc8(input));
    }

}
