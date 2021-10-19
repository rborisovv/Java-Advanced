package christmas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bag {
    private String color;
    private int capacity;
    private List<Present> data;

    public Bag(String color, int capacity) {
        this.color = color;
        this.capacity = capacity;
        this.data = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public int getCapacity() {
        return capacity;
    }

    public int count() {
        return this.data.size();
    }

    public void add(Present present) {
        if (this.capacity > 0) {
            this.data.add(present);
            this.capacity--;
        }
    }

    public boolean remove(String name) {
        return this.data.removeIf(p -> p.getName().equals(name));
    }

    public Present heaviestPresent() {
        return this.data.stream().max(Comparator.comparing(Present::getWeight)).orElse(null);
    }

    public Present getPresent(String name) {
        return this.data.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    public String report() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%s bag contains:", this.color)).append(System.lineSeparator());
        this.data.forEach(p -> result.append(p).append(System.lineSeparator()));
        return result.toString().trim();
    }
}