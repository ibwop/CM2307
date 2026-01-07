package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Represents a hotel room with booking and locking functionality
public class Room {
    private String roomId;
    private float price;
    private boolean isLocked; // Indicates if the room is currently locked
    private final Lock lock = new ReentrantLock(); // Used for thread-safe locking
    private List<Booking> bookings = new ArrayList<>(); // Stores bookings for this room

    // Adds a booking to the room
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    // Returns all bookings for this room
    public List<Booking> getBookings() {
        return bookings;
    }

    // Constructs a room with given ID and price
    public Room(String roomId, float price) {
        this.roomId = roomId;
        this.price = price;
        this.isLocked = false;
    }

    // Attempts to acquire the lock for this room
    public boolean tryLock() {
        if (lock.tryLock()) {
            this.isLocked = true;
            return true;
        }
        return false;
    }

    // Releases the lock for this room
    public void unlock() {
        this.isLocked = false;
        lock.unlock();
    }

    // Returns the room ID
    public String getRoomId() { return roomId; }
    // Returns the price of the room
    public float getPrice() { return price; }
    // Returns whether the room is locked
    public boolean isLocked() { return isLocked; }
}