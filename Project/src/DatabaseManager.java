import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/todo_list_db";
    private static final String USER = "root";
    private static final String PASS = "johnpogi2";
    private Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(false);  // Start transaction handling
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);  // Start transaction handling
        }
        return connection;
    }

    // Method to sign up a new user
    public void signUpUser(String username, String pin) {
        // First, check if the username already exists
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            // Ensure we get a valid result and check if count > 0
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println();
                return; // Exit the method if username already exists
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            rollbackTransaction();  // Rollback transaction if there's an error in username check
            return;
        }

        // If username doesn't exist, proceed with the signup
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, pin); // You should hash the pin before storing in a real application
            pstmt.executeUpdate();
            commitTransaction();  // Commit transaction after successful insert
            System.out.println("User signed up successfully.");
        } catch (SQLException e) {
            System.err.println("Error signing up user: " + e.getMessage());
            rollbackTransaction();  // Rollback transaction if error occurs
        }
    }

    // Commit transaction
    public void commitTransaction() throws SQLException {
        connection.commit();
    }

    // Rollback transaction if an error occurs
    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.err.println("Error rolling back transaction: " + e.getMessage());
        }
    }

    // Helper method to retrieve the user ID from the username
    public int getUserId(String username) throws SQLException {
        String query = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("User not found: " + username);
    }

    // Method to add a new task
    public void addTask(String username, int categoryId, String title, String description, String dueDate) {
        String sql = "INSERT INTO tasks (user_id, category_id, task, description, due_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int userId = getUserId(username);
            System.out.println("User ID retrieved: " + userId); // Debugging info

            pstmt.setInt(1, userId);           // User ID
            pstmt.setInt(2, categoryId);       // Category ID
            pstmt.setString(3, title);         // Task title
            pstmt.setString(4, description);   // Task description
            pstmt.setString(5, dueDate);       // Due date
            pstmt.executeUpdate();

            // Commit transaction after successful insert
            commitTransaction();
            System.out.println("Task added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding task: " + e.getMessage());
            rollbackTransaction(); // Rollback on failure
        }
    }


    // Method to add a new category
    // Updated to accept userId
    public int addCategory(int userId, String title, String dueDate, int reminder, boolean completed) throws SQLException {
        // Format the dueDate to "yyyy-MM-dd" (only date part, no time)
        String formattedDueDate = dueDate.split(" ")[0]; // Keep only the date part

        String query = "INSERT INTO categories (user_id, title, due_date, reminder, completed) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);                           // User ID
            stmt.setString(2, title);                        // Category title
            stmt.setDate(3, java.sql.Date.valueOf(formattedDueDate)); // Due date (only date part)
            stmt.setInt(4, reminder);                        // Reminder in minutes
            stmt.setBoolean(5, completed);                  // Completion status
            stmt.executeUpdate();

            // Commit transaction after successful insert
            commitTransaction();

            // Retrieve generated key
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the auto-generated category ID
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding category: " + e.getMessage());
            rollbackTransaction(); // Rollback on failure
        }
        throw new SQLException("Failed to add category.");
    }
}


