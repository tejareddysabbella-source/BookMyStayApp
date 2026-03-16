import java.util.*;

// Custom Exception for invalid booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Room Inventory Manager
class RoomInventory {

    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        // Validate room type
        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int available = rooms.get(roomType);

        // Prevent negative inventory
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        // Update inventory
        rooms.put(roomType, available - 1);

        System.out.println("Room booked successfully: " + roomType);
        System.out.println("Remaining " + roomType + " rooms: " + rooms.get(roomType));
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        try {
            inventory.displayInventory();

            System.out.println("\nAttempting valid booking...");
            inventory.bookRoom("Deluxe");

            System.out.println("\nAttempting invalid room type...");
            inventory.bookRoom("Luxury");   // invalid type

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        try {
            System.out.println("\nAttempting multiple bookings...");
            inventory.bookRoom("Suite");
            inventory.bookRoom("Suite");   // will trigger availability error

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        System.out.println("\nSystem continues running safely.");
        inventory.displayInventory();
    }
}