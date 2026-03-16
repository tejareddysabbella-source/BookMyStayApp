import java.util.*;

// Booking Request class
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

// Shared Room Inventory
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    // Critical section (thread-safe)
    public synchronized void allocateRoom(BookingRequest request) {

        String roomType = request.getRoomType();

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type requested by " + request.getGuestName());
            return;
        }

        int available = inventory.get(roomType);

        if (available > 0) {
            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName()
                    + " allocated " + roomType
                    + " room to " + request.getGuestName()
                    + " | Remaining: " + inventory.get(roomType));
        } else {
            System.out.println("No " + roomType + " rooms available for "
                    + request.getGuestName());
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory State:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Concurrent Booking Processor
class BookingProcessor implements Runnable {

    private Queue<BookingRequest> bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<BookingRequest> bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            BookingRequest request;

            // synchronized access to shared queue
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    return;
                }
                request = bookingQueue.poll();
            }

            // Allocate room safely
            inventory.allocateRoom(request);
        }
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        Queue<BookingRequest> bookingQueue = new LinkedList<>();
        RoomInventory inventory = new RoomInventory();

        // Simulated booking requests
        bookingQueue.add(new BookingRequest("Alice", "Deluxe"));
        bookingQueue.add(new BookingRequest("Bob", "Standard"));
        bookingQueue.add(new BookingRequest("Charlie", "Suite"));
        bookingQueue.add(new BookingRequest("David", "Deluxe"));
        bookingQueue.add(new BookingRequest("Eva", "Standard"));

        // Multiple guest threads
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}