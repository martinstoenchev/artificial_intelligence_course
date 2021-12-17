import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {

    private int N;
    private double fitness;
    private double distance;
    private List<City> route;

    public Route(int N, boolean emptyRoute) {
        if (emptyRoute) {
            this.N = N;
            this.route = new ArrayList<>(Collections.nCopies(N, null));
        } else {
            this.N = N;
            this.route = generateRandomCities();
            this.distance = calculateDistance();
            this.fitness = calculateFitness();
        }
    }

    public Route(List<City> cities) {
        this.N = cities.size();
        this.route = cities;
        this.distance = calculateDistance();
        this.fitness = calculateFitness();
    }

    public double getFitness() {
        return this.fitness;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setCity(int index, City city) {
        this.route.remove(index);
        this.route.add(index, city);
    }

    public void setDistance() {
        this.distance = calculateDistance();
    }

    public void setFitness() {
        this.fitness = calculateFitness();
    }

    public City getCity(int index) {
        return this.route.get(index);
    }

    public int getRouteLength() {
        return this.N;
    }

    public boolean containsCity(City city) {
        return this.route.contains(city);
    }

    private List<City> generateRandomCities() {
        List<City> cities = new ArrayList<>(this.N);

        for (int i = 0; i < this.N; i++) {
            cities.add(i, new City());
        }

        return cities;
    }

    private double calculateDistance() {
        double distance = 0;
        City startCity = route.get(0);

        for (int i = 1; i < this.N; i++) {
            distance+=startCity.getDistanceTo(route.get(i));
            startCity = route.get(i);
        }
        //distance+= startCity.getDistanceTo(route.get(0));

        return distance;
    }

    private double calculateFitness() {
        return 1/this.distance;
    }

    @Override
    public String toString() {
        return route.toString();
    }
}
