package dealership;

public class Main {
    public static void main(String[] args) {
        // Initialize the repository
        Dealership dealership = new Dealership("mercedes", 3);
        
        Car car = new Car("mercedes", "s-class", 2020);
        Car car1 = new Car("mercedes", "c-class", 2010);
        Car car2 = new Car("mercedes", "e-class", 2000);
        
        dealership.add(car);
        dealership.add(car1);
        dealership.add(car2);
    }
}
