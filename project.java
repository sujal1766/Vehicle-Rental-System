import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Exception Classes
class VehicleNotFoundException extends Exception {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}

class VehicleAlreadyRentedException extends Exception {
    public VehicleAlreadyRentedException(String message) {
        super(message);
    }
}

// Interface for Vehicle
interface IVehicle {
    String getId();

    String getModel();

    boolean isRented();

    void rent();

    void returnVehicle();
}

// Interface for VehicleRentalSystem
interface IVehicleRentalSystem {
    void addVehicle(String id, String model);

    void rentVehicle(String id) throws VehicleNotFoundException, VehicleAlreadyRentedException;

    void returnVehicle(String id) throws VehicleNotFoundException;

    List<IVehicle> getAvailableVehicles();

    List<IVehicle> getRentedVehicles();

    void displayAvailableVehicles();

    void displayRentedVehicles();

    void displayAllVehicles();
}

// Vehicle Class
class Vehicle implements IVehicle {
    private String id;
    private String model;
    private boolean rented;

    public Vehicle(String id, String model) {
        this.id = id;
        this.model = model;
        this.rented = false;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public boolean isRented() {
        return rented;
    }

    @Override
    public void rent() {
        this.rented = true;
    }

    @Override
    public void returnVehicle() {
        this.rented = false;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Model: " + model + ", Rented: "
                + (rented ? "Yes" : "No");
    }
}

// VehicleRentalSystem Class
class VehicleRentalSystem implements IVehicleRentalSystem {
    private List<IVehicle> vehicleList;

    public VehicleRentalSystem() {
        vehicleList = new ArrayList<>();
    }

    @Override
    public void addVehicle(String id, String model) {
        vehicleList.add(new Vehicle(id, model));
    }

    @Override
    public void rentVehicle(String id) throws VehicleNotFoundException, VehicleAlreadyRentedException {
        IVehicle vehicle = null;
        for (IVehicle v : vehicleList) {
            if (v.getId().equals(id)) {
                vehicle = v;
                break;
            }
        }

        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle with ID " + id + " not found.");
        }
        if (vehicle.isRented()) {
            throw new VehicleAlreadyRentedException("Vehicle with ID " + id + " is already rented.");
        }

        vehicle.rent();
    }

    @Override
    public void returnVehicle(String id) throws VehicleNotFoundException {
        IVehicle vehicle = null;
        for (IVehicle v : vehicleList) {
            if (v.getId().equals(id)) {
                vehicle = v;
                break;
            }
        }

        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle with ID " + id + " not found.");
        }
        if (!vehicle.isRented()) {
            System.out.println("Vehicle with ID " + id + " was not rented.");
            return;
        }

        vehicle.returnVehicle();
        System.out.println("Vehicle with ID " + id + " has been returned.");
    }

    @Override
    public List<IVehicle> getAvailableVehicles() {
        List<IVehicle> availableVehicles = new ArrayList<>();
        for (IVehicle vehicle : vehicleList) {
            if (!vehicle.isRented()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    @Override
    public List<IVehicle> getRentedVehicles() {
        List<IVehicle> rentedVehicles = new ArrayList<>();
        for (IVehicle vehicle : vehicleList) {
            if (vehicle.isRented()) {
                rentedVehicles.add(vehicle);
            }
        }
        return rentedVehicles;
    }

    @Override
    public void displayAllVehicles() {
        List<IVehicle> allVehicles = vehicleList;
        if (allVehicles.isEmpty()) {
            System.out.println("\nNo vehicles in the system.\n");
        } else {
            System.out.println("\nAll Vehicles: \n");
            for (IVehicle vehicle : allVehicles) {
                System.out.println(vehicle);
            }
        }
    }

    @Override
    public void displayAvailableVehicles() {
        List<IVehicle> availableVehicles = getAvailableVehicles();
        if (availableVehicles.isEmpty()) {
            System.out.println("\nNo vehicles available.\n");
        } else {
            System.out.println("\nAvailable Vehicles: \n");
            for (IVehicle vehicle : availableVehicles) {
                System.out.println(vehicle);
            }
        }
    }

    @Override
    public void displayRentedVehicles() {
        List<IVehicle> rentedVehicles = getRentedVehicles();
        if (rentedVehicles.isEmpty()) {
            System.out.println("No vehicles are currently rented.\n");
        } else {
            System.out.println("\nRented Vehicles: \n");
            for (IVehicle vehicle : rentedVehicles) {
                System.out.println(vehicle);
            }
        }
    }
}

public class project {
    public static void main(String[] args) {
        VehicleRentalSystem rentalSystem = new VehicleRentalSystem();

        rentalSystem.addVehicle("V001", "Toyota Corolla");
        rentalSystem.addVehicle("V002", "Honda Civic");
        rentalSystem.addVehicle("V003", "Ford Focus");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nVehicle Rental System");
            System.out.println("1. Display All Vehicles");
            System.out.println("2. Rent Vehicle");
            System.out.println("3. Return Vehicle");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            try {
                switch (choice) {
                    case 1:
                        rentalSystem.displayAllVehicles();
                        break;
                    case 2:
                        rentalSystem.displayAvailableVehicles();
                        System.out.println("***********************************");
                        System.out.println();
                        System.out.print("Enter vehicle ID to rent: ");
                        String rentId = scanner.nextLine();
                        rentalSystem.rentVehicle(rentId);
                        break;

                    case 3:
                        System.out.println();
                        System.out.print("Enter vehicle ID to return: ");
                        String returnId = scanner.nextLine();
                        rentalSystem.returnVehicle(returnId);
                        break;
                    case 4:
                        System.out.println();
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println();
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (VehicleNotFoundException | VehicleAlreadyRentedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
