package src.model;

import java.time.LocalDate;

// Represents a payment transaction with amount and date
public class Payment {
    private float amount; // Amount paid
    private LocalDate transactionDate; // Date of transaction

    // Constructor sets the payment amount and records the current date
    public Payment(float amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }
    
    // Returns the payment amount
    public float getAmount() { 
        return amount; 
    }

    // Returns a formatted receipt string with amount and date
    public String getReceiptDetails() {
        return "Paid: $" + amount + " on " + transactionDate;
    }
}