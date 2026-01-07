package src.model;

// Represents a booking made by a student
public class Booking {
    private String bookingId; // Unique identifier for the booking
    private String status; // Current status of the booking
    private String studentEmail; // Email of the student who made the booking
    private Payment paymentRecord; // Associated payment record, if any

    // Constructor initialises booking details
    public Booking(String bookingId, String status, String studentEmail) {
        this.bookingId = bookingId;
        this.status = status;
        this.studentEmail = studentEmail;
    }

    // Returns the email of the student who made the booking
    public String getStudentEmail() { 
        return studentEmail; 
    }

    // Associates a payment record with this booking
    public void setPayment(Payment p) {
        this.paymentRecord = p;
    }

    // Retrieves the payment record for this booking
    public Payment getPayment() {
        return paymentRecord;
    }

    // Returns the unique booking ID
    public String getBookingId() { 
        return bookingId;
    }
    
    // Returns the current status of the booking
    public String getStatus() { 
        return status;
    }

    // Updates the status of the booking
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Cancels the booking by updating its status
    public void cancel() {
        this.status = "CANCELLED";
    }
    
    // Returns a string representation of the booking, including payment details if available
    @Override
    public String toString() {
        String receiptInfo = (paymentRecord != null) ? " | " + paymentRecord.getReceiptDetails() : "";
        return "Booking ID: " + bookingId + " | Status: " + status + receiptInfo;
    }
}