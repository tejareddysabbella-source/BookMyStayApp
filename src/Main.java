import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 *
 * This program demonstrates centralized inventory management
 * using a HashMap. Room availability is stored and managed
 * through a dedicated RoomInventory class.
 *
 * @author Student
 * @version 3.1
 */

// RoomInventory class responsible for managing room availability
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes the room inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types and availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update room availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display full inventory
    public void displayInventory() {

        System.out.println("Current Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

// Main Application
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Book My Stay - Hotel Booking System ");
        System.out.println(" Version 3.1 ");
        System.out.println("======================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Example lookup
        System.out.println("\nChecking availability for Single Room:");
        int singleAvailable = inventory.getAvailability("Single Room");
        System.out.println("Available Single Rooms: " + singleAvailable);

        // Example update
        System.out.println("\nUpdating Suite Room availability...");
        inventory.updateAvailability("Suite Room", 3);

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication terminated successfully.");
    }
}