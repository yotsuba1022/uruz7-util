package uruz7.commons.util.time;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 * @author Carl Lu
 */
public class Iso8601TimeUtilTest {

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
        String iso8601 = "2017-07-2523:25:51Z";
        Iso8601TimeUtil.convertIso8601ToUnixTimestamp(iso8601);
    }

}
