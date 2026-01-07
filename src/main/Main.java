package src.main;

import src.controller.BookingController;
import src.model.*;
import src.view.BookingUI;

public class Main {
    public static void main(String[] args) {
        // Initialise
        BookingController controller = new BookingController();

        // Load Dummy Data
        setupDummyData(controller);

        // Launch UI
        BookingUI ui = new BookingUI(controller);
        ui.start();
    }
    
    private static void setupDummyData(BookingController controller) {
        // Add a Homeowner User
        Homeowner h1 = new Homeowner("U002", "securepass", "alice@home.com", "H67890");
        controller.registerUser(h1);

        // Add Rooms
        controller.addRoom(new Room("R101", 150.0f), h1);
        controller.addRoom(new Room("R102", 300.0f), h1);
        controller.addRoom(new Room("R103", 100.0f), h1); // Cheap one
        controller.addRoom(new Room("R201", 500.0f), h1); // Penthouse
        
        // Add a Student User
        Student s1 = new Student("U001", "password123", "john@uni.ac.uk", "S12345");
        controller.registerUser(s1);

        
        System.out.println("System Initialised with Dummy Data.");
    }
}