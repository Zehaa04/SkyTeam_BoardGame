package com.skyteam.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FlightPlan {

    private List<Integer> flightPath;

    public void importFlightPlan(Plan flightPlan) throws IOException {
        if (flightPlan == null) {
            throw new IOException("Invalid input");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            getClass().getResourceAsStream("plans.txt")))) {
            if (reader == null) {
                throw new IllegalArgumentException("File not found: plans.txt");
            }

            String line;
            int planLength = 0;

            String name = flightPlan.getName().trim();

            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(name)) {
                    planLength = Integer.parseInt(reader.readLine().trim());
                    break;
                }
            }

            if (planLength == 0) {
                throw new IllegalArgumentException("Plan not found!!!");
            }

            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < planLength; i++) {
                if ((line = reader.readLine()) == null) {
                    throw new IOException("End of file reached unexpectedly");
                }

                list.add(Integer.parseInt(line.trim()));
            }

            flightPath = list;
        }
    }



    public List<Integer> getFlightPath() {
        return flightPath;
    }

    public int getPlanes(int positionIndex){
        return flightPath.get(positionIndex);
    }
}
