package uruz7.commons.util.time;

import java.time.Instant;

/**
 * @author Carl Lu
 */
public interface TimeFormatable {

    Instant getInstant(String timestamp);

    String getTimestampString(Instant instant);

}
