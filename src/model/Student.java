package src.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String studentId;
    private List<Booking> bookings;
    private double balance;

    // Constructor initialises student details and sets default balance
    public Student(String userId, String password, String email, String studentId) {
        super(userId, password, email);
        this.studentId = studentId;
        this.bookings = new ArrayList<>();
        this.balance = 2000.00;
    }

    // Deducts funds if sufficient balance is available
    public boolean deductFunds(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    // Adds funds to the student's balance and prints confirmation
    public void addFunds(float amount) {
        this.balance += amount;
        System.out.println("[Bank] Refunded $" + amount);
    }
    
    // Returns current balance
    public double getBalance() { 
        return balance; 
    }

    // Placeholder for booking logic, handled by Controller
    public void bookRoom(Room room) {
        // Logic handled by Controller.
    }
    
    // Adds a booking to the student's list
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    // Returns all bookings for the student
    public List<Booking> getBookings() {
        return bookings;
    }

    // Returns the student ID
    public String getStudentId() { return studentId; }
}