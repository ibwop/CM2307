package src.model;

import java.util.ArrayList;
import java.util.List;

// Represents a homeowner user who can list rooms for rent
public class Homeowner extends User {
    private String licenseNumber; // Unique license identifier for homeowner
    private float rating; // Homeowner's rating, default is 5.0
    private List<Room> rooms; // List of rooms owned by the homeowner

    // Constructor initialises homeowner details and sets default rating
    public Homeowner(String userId, String password, String email, String licenseNumber) {
        super(userId, password, email);
        this.licenseNumber = licenseNumber;
        this.rating = 5.0f; 
        this.rooms = new ArrayList<>();
    }

    // Adds a new room to the homeowner's list
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    // Returns the list of rooms owned by the homeowner
    public List<Room> getRooms() { 
        return rooms; 
    }

    // Returns the homeowner's license number
    public String getLicenseNumber() { 
        return licenseNumber; 
    }

    // Returns the homeowner's current rating
    public float getRating() { 
        return rating; 
    }
}