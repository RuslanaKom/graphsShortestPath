package app;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        PathFinder pathFinder = new PathFinder();

        City vilnius = new City("Vilnius");
        City kaunas = new City("Kaunas");
        City panevezis = new City("Panevezis");
        City klaipeda = new City("Klaipeda");
        City siauliai = new City("Siauliai");
        City mazeikiai = new City("Mazeikiai");
        City kelme = new City("Kelme");

        MutableValueGraph<City, Integer> roads = ValueGraphBuilder
                .undirected()
                .build();

        roads.addNode(vilnius);
        roads.addNode(kaunas);
        roads.addNode(panevezis);
        roads.addNode(klaipeda);
        roads.addNode(siauliai);
        roads.addNode(mazeikiai);
        roads.addNode(kelme);

        roads.putEdgeValue(vilnius, kaunas, 100);
        roads.putEdgeValue(vilnius, panevezis, 130);
        roads.putEdgeValue(kaunas, panevezis, 90);
        roads.putEdgeValue(kaunas, kelme, 105);
        roads.putEdgeValue(panevezis, siauliai, 80);
        roads.putEdgeValue(kelme, siauliai, 60);
        roads.putEdgeValue(kelme, klaipeda, 110);
        roads.putEdgeValue(siauliai, klaipeda, 150);
        roads.putEdgeValue(siauliai, mazeikiai, 70);

        //read inputs
        System.out.println("Cities to travel between:");
        roads.nodes()
                .forEach(n -> System.out.println(n.getName()));

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter start city: ");
        String start  = reader.readLine();
        System.out.println("Enter destination city: ");
        String end = reader.readLine();

        City startCity = roads.nodes().stream().filter(c-> StringUtils.equals(start, c.getName())).findFirst().orElse(vilnius);
        City endCity = roads.nodes().stream().filter(c-> StringUtils.equals(end, c.getName())).findFirst().orElse(vilnius);

        pathFinder.findPath(startCity, endCity, roads);
    }
}
