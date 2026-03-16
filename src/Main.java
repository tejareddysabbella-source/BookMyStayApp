import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// Inventory Manager
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, Reservation> confirmedBookings;
    private Stack<String> rollbackStack = new Stack<>();
    private RoomInventory inventory;

    public CancellationService(Map<String, Reservation> confirmedBookings, RoomInventory inventory) {
        this.confirmedBookings = confirmedBookings;
        this.inventory = inventory;
    }

    public void cancelBooking(String reservationId) {

        // Validate reservation existence
        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation reservation = confirmedBookings.get(reservationId);

        // Record room ID for rollback
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventory.incrementRoom(reservation.getRoomType());

        // Remove booking from history
        confirmedBookings.remove(reservationId);

        System.out.println("Reservation " + reservationId + " cancelled successfully.");
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Simulated confirmed bookings
        Map<String, Reservation> confirmedBookings = new HashMap<>();

        Reservation r1 = new Reservation("RES101", "Deluxe", "D101");
        Reservation r2 = new Reservation("RES102", "Standard", "S201");

        confirmedBookings.put(r1.getReservationId(), r1);
        confirmedBookings.put(r2.getReservationId(), r2);

        CancellationService cancellationService =
                new CancellationService(confirmedBookings, inventory);

        System.out.println("Attempting cancellation for RES101...");
        cancellationService.cancelBooking("RES101");

        System.out.println("\nAttempting cancellation for invalid reservation...");
        cancellationService.cancelBooking("RES999");

        inventory.displayInventory();
    }
}