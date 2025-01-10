package com.skyteam.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FlightPlan {

    private List<Integer> flightPath;

    public void importFlightPlan(Plan flightPlan) throws IOException {
        if (flightPlan == null) {
            throw new IOException("Invalid input");
        }

        String name = flightPlan.getName().trim();

        InputStream inputStream = getClass().getResourceAsStream("/plans.json");
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: plans.json");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(inputStream);

        JsonNode matchingLocation = null;
        for (JsonNode locationNode : rootNode) {
            if (locationNode.get("location").asText().equalsIgnoreCase(name)) {
                matchingLocation = locationNode;
                break;
            }
        }

        if (matchingLocation == null) {
            throw new IllegalArgumentException("Plan not found!!!");
        }

        List<Integer> list = new ArrayList<>();
        for (JsonNode valueNode : matchingLocation.get("values")) {
            list.add(valueNode.asInt());
        }

        flightPath = list;
    }


    public List<Integer> getFlightPath() {
        return flightPath;
    }

    public int getPlanes(int positionIndex){
        return flightPath.get(positionIndex);
    }
}
