import java.util.*;

// Add-On Service class
class AddOnService {
    private String serviceName;
    private double serviceCost;

    public AddOnService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + serviceCost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println(service.getServiceName() + " added to reservation " + reservationId);
    }

    // Display services for reservation
    public void displayServices(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for reservation " + reservationId);
            return;
        }

        System.out.println("\nAdd-On Services for Reservation " + reservationId + ":");
        for (AddOnService service : services) {
            System.out.println("- " + service);
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getServiceCost();
            }
        }

        return total;
    }
}

// Main class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES101";

        // Guest selects services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
        AddOnService spa = new AddOnService("Spa Service", 2000);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        // Display selected services
        manager.displayServices(reservationId);

        // Calculate additional cost
        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Additional Cost: ₹" + totalCost);

        System.out.println("\nNote: Core booking and inventory remain unchanged.");
    }
}