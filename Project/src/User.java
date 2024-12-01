public class User {
    private final String username;
    private final String password;
    private final ToDoList toDoList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.toDoList = new ToDoList(); // Each user has their own ToDoList
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password); // Simple authentication logic
    }

    public ToDoList getToDoList() {
        return toDoList;
    }

    @Override
    public String toString() {
        return "User: " + username;
    }
}