package dealership;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

public class Dealership {
    private final Collection<Car> data;
    public String name;
    public int capacity;

    public Dealership(String name, int capacity) {
        this.data = new ArrayList<>(capacity);
        this.name = name;
        this.capacity = capacity;
    }

    public void add(Car car) {
        if (this.capacity > 0) {
            this.data.add(car);
            this.capacity--;
        }
    }

    public boolean buy(String manufacturer, String model) {
        Predicate<Car> foundCar = car -> car.getManufacturer().equals(manufacturer) &&
                car.getModel().equals(model);
        boolean isRemoved = this.data.removeIf(foundCar);
        if (isRemoved) {
            this.capacity++;
        }
        return isRemoved;
    }

    public Car getLatestCar() {
        return this.data.stream().max(Comparator.comparing(Car::getYear)).orElse(null);
    }

    public Car getCar(String manufacturer, String model) {
        return this.data.stream().filter(c -> c.getManufacturer().equals(manufacturer) && c.getModel().equals(model)).findFirst().orElse(null);
    }

    public int getCount() {
        return this.data.size();
    }

    public String getStatistics() {
        StringBuilder result = new StringBuilder(String.format("The cars are in a car dealership %s:\n", this.name));
        this.data.forEach(c -> result.append(c).append(System.lineSeparator()));
        return result.toString().trim();
    }
}