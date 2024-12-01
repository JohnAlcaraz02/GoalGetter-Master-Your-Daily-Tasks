import java.util.ArrayList;
import java.util.List;


public class ToDoList {
    private final List<Task> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    // Add a task to the to-do list
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added: " + task.getTitle());
    }

    // Remove a task by its title
    public void removeTask(String title) {
        tasks.removeIf(task -> task.getTitle().equalsIgnoreCase(title));
        System.out.println("Task removed: " + title);
    }

    // List all tasks
    public void listTasks(String category) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (Task task : tasks) {
                if (category.isEmpty() || task.getCategory().equalsIgnoreCase(category)) {
                    System.out.println(task);
                }
            }
        }
    }

    // Mark a task as completed
    public void markTaskAsComplete(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                task.markAsComplete();
                System.out.println("Task marked as completed: " + title);
                return;
            }
        }
        System.out.println("Task not found: " + title);
    }
    public List<Task> getTasks() {
        return tasks;
    }

}