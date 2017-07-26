package uruz7.commons.util.time;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        String invalidIso8601Input = "2017-07-2523:25:51Z";
        Iso8601TimeUtil.convertIso8601ToUnixTimestamp(invalidIso8601Input);
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

}
