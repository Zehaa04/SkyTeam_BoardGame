import com.skyteam.model.FlightPlan;
import com.skyteam.model.Plan;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightPlanTest {

    @Test
    void testImportFlightPlanMontreal() throws IOException {
        FlightPlan flightPlan = new FlightPlan();

        flightPlan.importFlightPlan(Plan.MONTREAL);

        List<Integer> flightPath = flightPlan.getFlightPath();

        assertNotNull(flightPath, "Flight path should not be null");
        assertEquals(List.of(0, 0, 1, 2, 1, 3, 2), flightPath, "Flight path does not match expected values for Montreal");
        assertEquals(2, flightPlan.getPlanes(3), "Value at position index 3 should be 2 for Montreal");
    }

    @Test
    void testImportFlightPlanInvalidLocation() {
        FlightPlan flightPlan = new FlightPlan();
        Plan invalidPlan = null;

        Exception exception = assertThrows(IOException.class, () -> flightPlan.importFlightPlan(invalidPlan));
        assertEquals("Invalid input", exception.getMessage());
    }


    @Test
    void testImportFlightPlanNullInput() {
        FlightPlan flightPlan = new FlightPlan();

        Exception exception = assertThrows(IOException.class, () -> flightPlan.importFlightPlan(null));
        assertEquals("Invalid input", exception.getMessage());
    }
}
