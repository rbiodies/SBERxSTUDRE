import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<City> city = parse();

        printMax(city);
        printRegion(city);
        city.sort(Comparator.comparing(City::getName));
        print(city);
        city.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName));
        print(city);
    }

    private static ArrayList<City> parse() {
        ArrayList<City> city = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("resources/city_ru.csv"))) {
            while (scanner.hasNextLine()) {
                city.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return city;
    }

    private static City    getRecordFromLine(String line) {
        Scanner scanner = new Scanner(line);

        scanner.useDelimiter(";");
        scanner.skip("\\d*");

        String name = scanner.next();
        String region = scanner.next();
        String district = scanner.next();
        int population = scanner.nextInt();
        String foundation = null;

        if (scanner.hasNext()) {
            foundation = scanner.next();
        }
        scanner.close();

        return new City(name, region, district, population, foundation);
    }

    private static void printMax(ArrayList<City> city) {
        City[]  array = city.toArray(new City[0]);
        City current = array[0];
        int index = 0;

        for (int i = 1; i < array.length; i++) {
            if (current.getPopulation() < array[i].getPopulation()) {
                current = array[i];
                index = i;
            }
        }
//        System.out.printf("[%d]=%d\n", Arrays.asList(arrays).indexOf(current), current.getPopulation());
        System.out.printf("[%d]=%d\n", index, current.getPopulation());
    }

    private static void printRegion(ArrayList<City> city) {
        Map<String, Integer>    region = new HashMap<>();

        for (City current : city) {
            if (!region.containsKey(current.getRegion())) {
                region.put(current.getRegion(), 1);
            } else {
                region.put(current.getRegion(), region.get(current.getRegion()) + 1);
            }
        }
//        for (Map.Entry<String, Integer> pair : region.entrySet()) {
//            System.out.println(pair.getKey() + " = " + pair.getValue());
//        }
        region.forEach((key, value) -> System.out.println(key + " = " + value));    // java 8
    }

    private static void print(ArrayList<City> city) {
        city.forEach(System.out::println);
    }
}
