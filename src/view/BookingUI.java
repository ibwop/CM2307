package src.view;

import src.controller.BookingController;
import src.model.Booking;
import src.model.Room;
import src.model.Student;
import src.model.Homeowner;
import src.model.User;

import java.util.List;
import java.util.Scanner;

public class BookingUI {
    private BookingController controller;
    private Scanner scanner;

    public BookingUI(BookingController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    // Main entry point for the UI
    public void start() {
        System.out.println("--- Welcome to UniStay ---");
        
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Search Rooms (Sorted)");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. Homeowner Review Request");
            System.out.println("5. View Booking History");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleSearch(); // Search and display rooms
                    break;
                case "2":
                    handleBooking(); // Book a room
                    break;
                case "3":
                    handleCancellation(); // Cancel an existing booking
                    break;
                case "4":
                    handleHomeownerReview(); // Homeowner reviews booking requests
                    break;
                case "5":
                    handleHistory(); // Show student's booking history
                    break;
                case "6":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // Handles searching for rooms with sorting
    private void handleSearch() {
        System.out.print("Sort by price? (1) Lowest-First (2) Highest-First: ");
        String sortChoice = scanner.nextLine();
        boolean ascending = sortChoice.equals("1");

        List<Room> rooms = controller.searchRoomsSorted(ascending);

        System.out.println("\n--- Available Rooms ---");
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (Room r : rooms) {
                System.out.println("ID: " + r.getRoomId() + " | Price: $" + r.getPrice());
            }
        }
    }

    // Handles booking a room for a student
    private void handleBooking() {
        System.out.print("Enter your Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = controller.login(studentId, password);

        if (user == null) {
            System.out.println("Error: Wrong ID or Password!");
            return; 
        }

        System.out.print("Enter Room ID to book: ");
        String roomId = scanner.nextLine();

        String result = controller.processBooking(studentId, roomId);
        System.out.println("\n>>> " + result);
    }

    // Handles cancellation of a booking
    private void handleCancellation() {
        System.out.print("Enter Student ID: ");
        String sId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = controller.login(sId, password);

        if (user == null) {
            System.out.println("Error: Wrong ID or Password!");
            return; 
        }

        System.out.print("Enter Booking ID to cancel: ");
        String bId = scanner.nextLine();
        System.out.println(controller.cancelBooking(sId, bId));
    }

    // Homeowner reviews booking requests for their rooms
    private void handleHomeownerReview() {

        System.out.println("\n--- HOMEOWNER PORTAL ---");
        System.out.print("Login (Enter Homeowner ID): ");
        String hId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = controller.login(hId, password);
        if (!(user instanceof Homeowner)) {
            System.out.println("Access Denied: Not a Homeowner account or wrong credentials.");
            return;
        }

        Homeowner h = (Homeowner) user;
        System.out.println("Welcome, " + h.getEmail());
        System.out.println("You have " + h.getRooms().size() + " rooms listed.");

        System.out.println("--- Bookings for Your Rooms ---");
        boolean found = false;
        for (Room room : h.getRooms()) {
            List<Booking> bookings = room.getBookings();
            for (Booking b : bookings) {
                System.out.println("- Booking ID: " + b.getBookingId() + " | Room ID: " + room.getRoomId() + " | Status: " + b.getStatus());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No bookings found for your rooms.");
        }

        System.out.print("Enter Booking ID: ");
        String bId = scanner.nextLine();
        System.out.print("Accept? (y/n): ");
        boolean accept = scanner.nextLine().equalsIgnoreCase("y");
        System.out.println(controller.reviewBooking(hId, bId, accept));
    }

    // Displays booking history for a student
    private void handleHistory() {
        System.out.print("Enter Student ID: ");
        String sId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = controller.login(sId, password);

        if (user == null) {
            System.out.println("Error: Wrong ID or Password!");
            return; 
        }
        
        if (user instanceof Student) {
            Student s = (Student) user;
            System.out.println("\n--- Wallet Balance: $" + s.getBalance() + " ---");
            System.out.println("--- Booking History ---");
            List<Booking> history = s.getBookings();
            
            if (history.isEmpty()) System.out.println("No bookings found.");
            
            for (Booking b : history) {
                System.out.println("- Booking ID: " + b.getBookingId() + " | Status: " + b.getStatus());
            }
        } else {
            System.out.println("User not found.");
        }
    }
}