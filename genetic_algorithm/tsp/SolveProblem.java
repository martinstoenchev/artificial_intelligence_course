import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class SolveProblem {
    public static void main(String[] args) throws IOException {

        int populationSize = 150;
        int numberOfCities = 50;

        String line;
        String[] coordinates;
        List<City> cities = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("test\\uk12_xy.csv"));
        while ((line = br.readLine()) != null)
        {
            coordinates = line.split(",");
            BigDecimal x = new BigDecimal(coordinates[0]);
            BigDecimal y = new BigDecimal(coordinates[1]);
            cities.add(new City(x.doubleValue(), y.doubleValue()));
        }


        GeneticAlgorithm pop = new GeneticAlgorithm(populationSize, cities);
        pop.solveWithNGenerations(100);
    }
}
