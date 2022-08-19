import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

    private int N;
    private int populationSize;
    private List<Route> population;

    public Population(int populationSize) {
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);
    }

    public Population(int populationSize, int N) {
        this.N = N;
        this.populationSize = populationSize;
        this.population = initialPopulation();
    }

    public Population(int populationSize, List<City> cities) {
        this.N = cities.size();
        this.populationSize = populationSize;
        this.population = generatePopulation(cities);
    }

    private List<Route> initialPopulation() {
        List<Route> population = new ArrayList<>(populationSize);

        for (int i = 0; i < populationSize; i++) {
            population.add(i, new Route(this.N, false));
        }

        return population;
    }

    private List<Route> generatePopulation(List<City> cities) {
        List<Route> population = new ArrayList<>(populationSize);

        for (int i = 0; i < this.populationSize; i++) {
            population.add(new Route(cities));
            Collections.shuffle(cities);
        }
        return population;
    }

    public void saveRoute(int index, Route route) {
        this.population.add(index, route);
    }

    public Route getFittest() {
        Route fittest = population.get(0);

        for (int i = 1; i < this.populationSize; i++) {
            if (population.get(i).getFitness() > fittest.getFitness()) {
                fittest = population.get(i);
            }
        }

        return fittest;
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public Route getRoute(int index) {
        return this.population.get(index);
    }

}
