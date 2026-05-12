package com.example.engg1420project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CityGraph {
    private final Map<String, City> cities = new LinkedHashMap<>();
    private final Map<String, List<String>> connections = new LinkedHashMap<>();

    public static CityGraph createDefaultMap() {
        CityGraph graph = new CityGraph();

        graph.addCity("Oakport", 0, 0);
        graph.addCity("Pinewatch", 2, 0);
        graph.addCity("Meadowrun", 5, 0);
        graph.addCity("Riverbend", 1, 2);
        graph.addCity("Northpass", 4, 1);
        graph.addCity("Goldfield", 7, 1);
        graph.addCity("Eastcliff", 9, 2);
        graph.addCity("Marketon", 8, 3);
        graph.addCity("Stonewick", 3, 4);
        graph.addCity("Lakeside", 6, 4);
        graph.addCity("Midtown", 5, 5);
        graph.addCity("Southgate", 8, 5);
        graph.addCity("Bridgeford", 1, 5);
        graph.addCity("Ironhill", 2, 7);
        graph.addCity("Harborview", 0, 8);
        graph.addCity("Westhaven", 5, 8);
        graph.addCity("Sunfield", 7, 8);
        graph.addCity("Moonwell", 4, 9);
        graph.addCity("Castleford", 9, 9);

        graph.connectCities("Oakport", "Riverbend");
        graph.connectCities("Oakport", "Pinewatch");
        graph.connectCities("Pinewatch", "Meadowrun");
        graph.connectCities("Pinewatch", "Northpass");
        graph.connectCities("Pinewatch", "Riverbend");
        graph.connectCities("Meadowrun", "Northpass");
        graph.connectCities("Meadowrun", "Goldfield");
        graph.connectCities("Riverbend", "Stonewick");
        graph.connectCities("Riverbend", "Bridgeford");
        graph.connectCities("Northpass", "Goldfield");
        graph.connectCities("Northpass", "Lakeside");
        graph.connectCities("Goldfield", "Eastcliff");
        graph.connectCities("Goldfield", "Marketon");
        graph.connectCities("Eastcliff", "Marketon");
        graph.connectCities("Stonewick", "Lakeside");
        graph.connectCities("Stonewick", "Midtown");
        graph.connectCities("Stonewick", "Bridgeford");
        graph.connectCities("Stonewick", "Ironhill");
        graph.connectCities("Bridgeford", "Ironhill");
        graph.connectCities("Bridgeford", "Harborview");
        graph.connectCities("Lakeside", "Midtown");
        graph.connectCities("Lakeside", "Southgate");
        graph.connectCities("Lakeside", "Westhaven");
        graph.connectCities("Marketon", "Southgate");
        graph.connectCities("Marketon", "Eastcliff");
        graph.connectCities("Southgate", "Sunfield");
        graph.connectCities("Ironhill", "Harborview");
        graph.connectCities("Ironhill", "Westhaven");
        graph.connectCities("Harborview", "Westhaven");
        graph.connectCities("Westhaven", "Moonwell");
        graph.connectCities("Westhaven", "Sunfield");
        graph.connectCities("Moonwell", "Castleford");
        graph.connectCities("Sunfield", "Castleford");

        return graph;
    }

    public void addCity(String name, int x, int y) {
        City city = new City(name, x, y);
        cities.put(name, city);
        connections.put(name, new ArrayList<>());
    }

    public void connectCities(String firstCityName, String secondCityName) {
        validateCity(firstCityName);
        validateCity(secondCityName);

        addConnection(firstCityName, secondCityName);
        addConnection(secondCityName, firstCityName);
    }

    public City getCity(String cityName) {
        validateCity(cityName);
        return cities.get(cityName);
    }

    public List<City> getCities() {
        return Collections.unmodifiableList(new ArrayList<>(cities.values()));
    }

    public List<City> getConnectedCities(String cityName) {
        validateCity(cityName);

        List<City> connectedCities = new ArrayList<>();
        for (String connectedCityName : connections.get(cityName)) {
            connectedCities.add(cities.get(connectedCityName));
        }
        return Collections.unmodifiableList(connectedCities);
    }

    public boolean hasConnection(String firstCityName, String secondCityName) {
        validateCity(firstCityName);
        validateCity(secondCityName);
        return connections.get(firstCityName).contains(secondCityName);
    }

    private void addConnection(String firstCityName, String secondCityName) {
        List<String> connectedCities = connections.get(firstCityName);
        if (!connectedCities.contains(secondCityName)) {
            connectedCities.add(secondCityName);
        }
    }

    private void validateCity(String cityName) {
        if (!cities.containsKey(cityName)) {
            throw new IllegalArgumentException("Unknown city: " + cityName);
        }
    }

    public static class City {
        private final String name;
        private final int x;
        private final int y;

        public City(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        public String getName() {
            return name;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
