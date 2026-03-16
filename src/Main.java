import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * This program demonstrates how booking requests are collected
 * and stored in a queue using the FIFO principle (First-Come-First-Served).
 * The queue preserves the arrival order of booking requests.
 *
 * No room allocation or inventory updates occur at this stage.
 *
 * @author Student
 * @version 5.1
 */

// Reservation class representing a booking request
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Request Queue
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation reservation : requestQueue) {
            reservation.displayReservation();
        }
    }
}

// Main Application
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println(" Book My Stay - Hotel Booking System ");
        System.out.println(" Version 5.1 ");
        System.out.println("=========================================\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued booking requests
        bookingQueue.displayQueue();

        System.out.println("\nRequests stored in FIFO order.");
        System.out.println("No inventory updates performed at this stage.");
    }
}