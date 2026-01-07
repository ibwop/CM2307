package src.model;

// Abstract base class for all user types
public abstract class User {
    private String userId;
    private String password;
    private String email;

    // Constructor to initialize user details
    public User(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    // Checks if the provided password matches the user's password
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Returns the user's ID
    public String getUserId() { 
        return userId; 
    }

    // Returns the user's email address
    public String getEmail() { 
        return email; 
    }
    
    // Updates the user's password
    public void setPassword(String password) { 
        this.password = password; 
    }
}