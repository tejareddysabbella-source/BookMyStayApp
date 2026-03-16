import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// System State (Serializable Snapshot)
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Reservation> bookings;
    Map<String, Integer> inventory;

    public SystemState(Map<String, Reservation> bookings,
                       Map<String, Integer> inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public void saveState(SystemState state) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public SystemState loadState() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh system.");
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state restored successfully.");
            return state;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting fresh system.");
            return null;
        }
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistenceService = new PersistenceService();

        // Attempt recovery
        SystemState restoredState = persistenceService.loadState();

        Map<String, Reservation> bookings;
        Map<String, Integer> inventory;

        if (restoredState != null) {
            bookings = restoredState.bookings;
            inventory = restoredState.inventory;
        } else {
            // Initialize fresh state
            bookings = new HashMap<>();
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);
        }

        // Simulate a booking
        if (inventory.get("Deluxe") > 0) {

            Reservation r1 = new Reservation("RES101", "Alice", "Deluxe");
            bookings.put(r1.getReservationId(), r1);

            inventory.put("Deluxe", inventory.get("Deluxe") - 1);

            System.out.println("New booking created: " + r1);
        }

        // Display current state
        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }

        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // Save state before exit
        SystemState currentState = new SystemState(bookings, inventory);
        persistenceService.saveState(currentState);

        System.out.println("\nSystem shutdown complete.");
    }
}