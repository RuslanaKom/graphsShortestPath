package app;

public class City {

    private String name;

    private int distanceFormStart;

    private boolean visited;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistanceFormStart() {
        return distanceFormStart;
    }

    public void setDistanceFormStart(int distanceFormStart) {
        this.distanceFormStart = distanceFormStart;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return getName();
    }
}
