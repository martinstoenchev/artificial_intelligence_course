import java.util.List;

public class GeneticAlgorithm {

    private final double mutationRate = 0.015;
    private final int tournamentSize = 105;

    private final int populationSize;
    private final int numberOfCities;
    private final Population startPopulation;

    public GeneticAlgorithm(int populationSize, List<City> cities) {
        this.populationSize = populationSize;
        this.numberOfCities = cities.size();
        this.startPopulation = new Population(populationSize, cities); // creating population according to given cities
    }

    public GeneticAlgorithm(int populationSize, int numberOfCities) {
        this.populationSize = populationSize;
        this.numberOfCities = numberOfCities;
        this.startPopulation = new Population(populationSize, numberOfCities); // creating random population
    }

    public void solveWithNGenerations(int N) {
        System.out.println("Initial distance: " + startPopulation.getFittest().getDistance());
        Population pop = evolvePopulation(startPopulation);
        for (int i = 1; i < N; i++) {
            pop = evolvePopulation(pop);
        }
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        //System.out.println("Solution:");
        //System.out.println(pop.getFittest());
    }

    public Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(populationSize);
        newPopulation.saveRoute(0, pop.getFittest());

        for (int i = 1; i < populationSize; i++) {
            Route parent1 = tournamentSelection(pop);
            Route parent2 = tournamentSelection(pop);

            Route offspring = crossover(parent1, parent2);

            newPopulation.saveRoute(i, mutate(offspring));
        }

        return newPopulation;
    }

    private Route tournamentSelection(Population pop) {
        Population tournament = new Population(this.tournamentSize);

        for (int i = 0; i < this.tournamentSize; i++) {
            int index = (int)(Math.random() * pop.getPopulationSize());
            tournament.saveRoute(i, pop.getRoute(index));
        }

        return tournament.getFittest();
    }

    private Route crossover(Route parent1, Route parent2) {
        Route offspring = new Route(numberOfCities, true);

        int k = (int)(Math.random() * numberOfCities);
        int l = (int)(Math.random() * numberOfCities);

        int startIndex = Math.min(k, l);
        int endIndex = Math.max(k, l);

        for (int i = startIndex; i <= endIndex; i++) {
            offspring.setCity(i, parent1.getCity(i));
        }

        for (int i = 0; i < numberOfCities; i++) {
            if (!offspring.containsCity(parent2.getCity(i))) {
                for (int j = 0; j < numberOfCities; j++) {
                    if (offspring.getCity(j) == null) {
                        offspring.setCity(j, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        offspring.setDistance();
        offspring.setFitness();
        return offspring;
    }

    private Route mutate(Route route) {
        for (int i = 0; i < route.getRouteLength(); i++) {
            int j = (int)(Math.random() * route.getRouteLength());

            if (Math.random() < this.mutationRate) {
                City city1 = route.getCity(i);
                City city2 = route.getCity(j);

                route.setCity(i, city2);
                route.setCity(j, city1);
            }

        }
        return route;
    }

}
