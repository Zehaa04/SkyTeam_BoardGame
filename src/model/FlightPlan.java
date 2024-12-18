package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlightPlan {

    private List<Integer> flightPath;

    public void importFlightPlan(Plan flightPlan) throws IOException {
        if (flightPlan == null) {
            throw new IOException("Invalid input");
        }

        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/plans.txt"));
        String line;
        int planLength = 0;

        String name = flightPlan.getName();

        while ((line = reader.readLine()) != null) {
            if (line.equals(name)) {
                reader.readLine();
                planLength = Integer.parseInt(reader.readLine());
                break;
            }
        }

        if (planLength == 0) {
            reader.close();
            throw new IllegalArgumentException("Plan not found!");
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < planLength; i++) {
            if ((line = reader.readLine()) == null) {
                reader.close();
                throw new IOException("End of file");
            }

            int planes = Integer.parseInt(line);
            list.add(planes);
        }

        reader.close();
        flightPath = list;
    }

    public List<Integer> getFlightPath() {
        return flightPath;
    }

    public int getPlanes(int positionIndex){
        return flightPath.get(positionIndex);
    }
}
