package app;

import com.google.common.graph.MutableValueGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PathFinder {
    public void findPath(City startCity, City destination, MutableValueGraph<City, Integer> roads) {

        resetDataForNewStart(roads, startCity);
        evaluateAllDistances(roads, startCity, destination);
        List<City> shortcut = findShortcut(roads, destination, startCity);
        Collections.reverse(shortcut);
        System.out.println("The shortest route is:");
        shortcut.forEach(c -> System.out.print( c.getName() + "["+c.getDistanceFormStart() +"]" + " -> "));
    }

    private List<City> findShortcut(MutableValueGraph<City, Integer> roads, City destination, City startCity) {
        List<City> shortcut = new ArrayList<>();
        shortcut.add(destination);
        City currentCity = destination;
        while (currentCity != startCity) {
            City previousCity = findPreviousCity(roads, currentCity);
            shortcut.add(previousCity);
            currentCity = previousCity;
        }
        return shortcut;
    }

    private City findPreviousCity(MutableValueGraph<City, Integer> roads, City city) {
        Set<City> neighbours = roads.adjacentNodes(city);
        for (City neighbour : neighbours) {
            int result = city.getDistanceFormStart() - roads.edgeValue(city, neighbour)
                    .orElse(0);
            if (neighbour.getDistanceFormStart() == result) {
                return neighbour;
            }
        }
        return null;
    }


    private void resetDataForNewStart(MutableValueGraph<City, Integer> roads, City startCity) {
        Iterator<City> iterator = roads.nodes()
                .iterator();
        while (iterator.hasNext()) {
            City city = iterator.next();
            if (city.equals(startCity)) {
                city.setDistanceFormStart(0);
            } else {
                city.setDistanceFormStart(10000);
            }
            city.setVisited(false);
        }
    }

    private void evaluateAllDistances(MutableValueGraph<City, Integer> roads, City startCity, City destination) {
        Set<City> citiesToCheck = new HashSet<>();
        citiesToCheck.add(startCity);

        while (notAllCitiesVisited(roads)) {
            for (City cityToCheck : citiesToCheck) {
                evaluateDistancesToAdjacentCities(roads, cityToCheck, destination);
            }
            citiesToCheck.forEach(c -> c.setVisited(true));
            Set<City> adjacentCities = new HashSet<>();
            for (City cityToCheck : citiesToCheck) {
                adjacentCities.addAll(roads.adjacentNodes(cityToCheck));
            }
            citiesToCheck.clear();
            citiesToCheck.addAll(adjacentCities);
        }
        System.out.println("All distances evaluated:");
        roads.nodes()
                .forEach(n -> System.out.println(n.getName() + " - " + n.getDistanceFormStart()));
    }


    private void evaluateDistancesToAdjacentCities(MutableValueGraph<City, Integer> roads, City currentCity, City destination) {
        if (currentCity.equals(destination)) {
            return;
        }
        Set<City> adjacentCities = roads.adjacentNodes(currentCity);
        Set<City> nextCities = adjacentCities.stream()
                .filter(c -> !c.isVisited())
                .collect(Collectors.toSet());
        for (City nextCity : nextCities) {
            Integer newDistance = roads.edgeValue(currentCity, nextCity)
                    .orElse(10000) + currentCity.getDistanceFormStart();
            if (nextCity.getDistanceFormStart() > newDistance) {
                nextCity.setDistanceFormStart(newDistance);
            }
        }
    }

    private boolean notAllCitiesVisited(MutableValueGraph<City, Integer> roads) {
        return roads.nodes()
                .stream()
                .anyMatch(city -> !city.isVisited());
    }

}
