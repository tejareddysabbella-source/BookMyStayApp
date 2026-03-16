import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * This program processes booking requests from a queue,
 * allocates rooms safely, and updates inventory while
 * preventing double-booking using Set and HashMap.
 *
 * @author Student
 * @version 6.1
 */

// Reservation class representing booking requests
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Booking Service
class BookingService {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    // Track unique room IDs
    private Set<String> allocatedRoomIds;

    // Map room type -> allocated room IDs
    private Map<String, Set<String>> roomAllocations;

    public BookingService(Queue<Reservation> bookingQueue, RoomInventory inventory) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;

        allocatedRoomIds = new HashSet<>();
        roomAllocations = new HashMap<>();
    }

    public void processBookings() {

        while (!bookingQueue.isEmpty()) {

            Reservation reservation = bookingQueue.poll();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing booking for: " + reservation.getGuestName());

            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Store allocation
                allocatedRoomIds.add(roomId);

                roomAllocations
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                // Update inventory
                inventory.decrementRoom(roomType);

                System.out.println("Reservation confirmed!");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Allocated Room ID: " + roomId);

            } else {

                System.out.println("Reservation failed: No rooms available for " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {

        String prefix = roomType.substring(0, 2).toUpperCase();
        String roomId;

        do {
            roomId = prefix + "-" + (int) (Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    public void displayAllocations() {

        System.out.println("\nRoom Allocation Summary:");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {

            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Main Application
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println(" Book My Stay - Hotel Booking System ");
        System.out.println(" Version 6.1 ");
        System.out.println("=========================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Booking request queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.offer(new Reservation("Alice", "Single Room"));
        bookingQueue.offer(new Reservation("Bob", "Double Room"));
        bookingQueue.offer(new Reservation("Charlie", "Suite Room"));
        bookingQueue.offer(new Reservation("David", "Suite Room"));

        // Booking service
        BookingService bookingService = new BookingService(bookingQueue, inventory);

        // Process bookings
        bookingService.processBookings();

        // Display results
        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}