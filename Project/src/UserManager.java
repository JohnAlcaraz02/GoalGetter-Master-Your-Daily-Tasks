import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final Map<String, User> users;  // In-memory cache of users (optional)
    private User loggedInUser;
    private final TaskNotifier taskNotifier;
    private final DatabaseManager dbManager;

    public UserManager() throws SQLException {
        this.users = new HashMap<>();
        this.loggedInUser = null;
        this.taskNotifier = new TaskNotifier(this);
        this.dbManager = new DatabaseManager();  // Initialize DatabaseManager
    }

    private boolean isValidPin(String pin) {
        return pin.matches("\\d{4}"); // Regular expression to check for exactly 4 digits
    }

    // Sign up a new user
    public void signUp(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
        } else if (!isValidPin(password)) {
            System.out.println("Password must be a 4-digit PIN.");
        } else {
            // Call the DatabaseManager to insert the user into the database
            dbManager.signUpUser(username, password);
            // Optionally store the user in the in-memory map as well
            users.put(username, new User(username, password));
            System.out.println("User signed up successfully: " + username);
        }
    }

    // Log in an existing user by querying the database
    public boolean logIn(String username, String password) {
        User user = users.get(username); // Check the in-memory cache
        if (user != null && user.authenticate(password)) {
            loggedInUser = user;
            System.out.println("Logged in as: " + username);
            taskNotifier.startNotificationService();
            return true;
        } else {
            try {
                // Query the database for the user
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement stmt = dbManager.getConnection().prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet resultSet = stmt.executeQuery();

                if (resultSet.next()) {
                    // User found, login successful
                    loggedInUser = new User(username, password);  // Load the user data
                    System.out.println("Logged in as: " + username);
                    taskNotifier.startNotificationService();
                    return true;
                } else {
                    System.out.println("Invalid username or password.");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Database error during login: " + e.getMessage());
                return false;
            }
        }
    }

    public void logOut() {
        if (loggedInUser != null) {
            System.out.println("Logged out: " + loggedInUser.getUsername());
            loggedInUser = null;
            taskNotifier.stopNotificationService();
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
