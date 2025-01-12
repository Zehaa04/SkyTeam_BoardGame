import com.skyteam.model.Altitude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AltitudeTest {

    @Test
    void testInitialAltitude() {
        Altitude altitude = new Altitude();
        assertEquals(6000, altitude.getAltitude());
    }

    @Test
    void testDecreaseAltitude() {
        Altitude altitude = new Altitude();
        altitude.decreaseAltitude();
        assertEquals(5000, altitude.getAltitude());
    }

    @Test
    void testHasRerollTokenAt6000() {
        Altitude altitude = new Altitude();
        assertTrue(altitude.hasRerollToken());
    }

    @Test
    void testHasRerollTokenAt2000() {
        Altitude altitude = new Altitude();
        altitude.decreaseAltitude(); // 5000
        altitude.decreaseAltitude(); // 4000
        altitude.decreaseAltitude(); // 3000
        altitude.decreaseAltitude(); // 2000
        assertTrue(altitude.hasRerollToken());
    }

    @Test
    void testNoRerollTokenAtOtherAltitudes() {
        Altitude altitude = new Altitude();
        altitude.decreaseAltitude(); // 5000
        assertFalse(altitude.hasRerollToken());

        altitude.decreaseAltitude(); // 4000
        assertFalse(altitude.hasRerollToken());

        altitude.decreaseAltitude(); // 3000
        assertFalse(altitude.hasRerollToken());
    }
}
