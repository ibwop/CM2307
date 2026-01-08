package src.controller;

import src.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class BookingController {
    private Map<String, Room> roomCatalog;
    private Map<String, User> userDatabase;

    public BookingController() {
        this.roomCatalog = new HashMap<>();
        this.userDatabase = new HashMap<>();
    }

    // Authenticates user credentials
    public User login(String userId, String password) {
        User user = userDatabase.get(userId);
        if (user != null && user.validatePassword(password)) {
            return user;
        }
        return null;
    }

    // Adds a room to the catalog and links it to the homeowner
    public void addRoom(Room room, Homeowner owner) {
        roomCatalog.put(room.getRoomId(), room);
        owner.addRoom(room); 
    }

    // Registers a new user in the system
    public void registerUser(User user) {
        userDatabase.put(user.getUserId(), user);
    }

    // Retrieves user by ID
    public User getUser(String userId) {
        return userDatabase.get(userId);
    }

    // Returns a list of rooms sorted by price in ascending or descending order
    public List<Room> searchRoomsSorted(boolean ascending) {
        return roomCatalog.values().stream()
            .sorted((r1, r2) -> ascending 
                ? Float.compare(r1.getPrice(), r2.getPrice())
                : Float.compare(r2.getPrice(), r1.getPrice()))
            .collect(Collectors.toList());
    }

    // Processes a booking request from a student for a room
    public String processBooking(String studentId, String roomId) {
        Room room = roomCatalog.get(roomId);
        User user = userDatabase.get(studentId);

        if (room == null) return "Error: Room ID not found.";
        if (user == null || !(user instanceof Student)) return "Error: Invalid Student ID.";
        Student student = (Student) user;

        // Lock room to prevent concurrent bookings
        if (room.tryLock()) {
            try { 
                float totalAmount = room.getPrice(); 
                
                // Check if student has enough funds
                if (student.deductFunds(totalAmount)) {
                    Booking newBooking = new Booking(UUID.randomUUID().toString(), "PENDING", student.getEmail());
                    Payment receipt = new Payment(totalAmount);
                    newBooking.setPayment(receipt);

                    // Link booking to student and room
                    student.addBooking(newBooking);
                    room.addBooking(newBooking);

                    System.out.println("[System] Pending Request email sent to: " + student.getEmail());
                    System.out.println("[System] 'We will notify you once the homeowner confirms.'");
                    return "Success! Request placed for room " + roomId + ". Remaining Balance: $" + student.getBalance();
                } else {
                    return "Error: Insufficient funds. Balance: $" + student.getBalance() + " vs Rent: $" + totalAmount;
                }

            } finally {
                room.unlock();
            }
        } else {
            return "Error: Room is currently being processed by another user.";
        }
    }
   
    // Cancels a booking for a student
    public String cancelBooking(String studentId, String bookingId) {
        User user = userDatabase.get(studentId);
        if (!(user instanceof Student)) return "Error: User is not a student.";
        
        Student student = (Student) user;
        Optional<Booking> target = student.getBookings().stream()
            .filter(b -> b.getBookingId().equals(bookingId))
            .findFirst();

        if (target.isPresent()) {
            Booking booking = target.get();
            booking.cancel();
            Payment payment = booking.getPayment();
            if (payment != null) {
                student.addFunds(payment.getAmount());
            }
            // Booking cancelled successfully
            return "Success: Booking " + bookingId + " cancelled.";
        }
        return "Error: Booking ID not found.";
    }

    // Homeowner reviews a booking request (accept or reject)
    public String reviewBooking(String homeownerId, String bookingId, boolean accept) {
        User user = userDatabase.get(homeownerId);
        if (!(user instanceof Homeowner)) return "Error: Not a homeowner.";

        Homeowner owner = (Homeowner) user; 

        // Search for booking in homeowner's rooms
        for (Room room : owner.getRooms()) {
            for (Booking b : room.getBookings()) {
                if (b.getBookingId().equals(bookingId)) {
                    b.setStatus(accept ? "ACCEPTED" : "REJECTED");

                    if (accept) {
                        System.out.println(">>> [System] Confirmation email sent to: " + b.getStudentEmail());
                    } else {
                        // Refund student money if rejected
                        User studentUser = userDatabase.values().stream()
                            .filter(u -> u.getEmail().equals(b.getStudentEmail()))
                            .findFirst()
                            .orElse(null);
                        if (studentUser instanceof Student) {
                            Student student = (Student) studentUser;
                            Payment payment = b.getPayment();
                            if (payment != null) {
                                student.addFunds(payment.getAmount());
                            }
                        }
                        System.out.println(">>> [System] Rejection email sent to: " + b.getStudentEmail());
                    }

                    return "Success: Booking " + bookingId + (accept ? " ACCEPTED" : " REJECTED");
                }
            }
        }
        return "Error: Booking ID not found in your properties.";
    }
}