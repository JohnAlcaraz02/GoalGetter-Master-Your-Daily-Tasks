import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class Task {
    private final String title;
    private final String description;
    private boolean isCompleted;
    private final LocalDateTime dueDate;
    private final Duration reminderTime;
    private final String category;
    private boolean notified;


    // Constructor
    public Task(String title, String description, LocalDateTime dueDate, Duration reminderTime, String category) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
        this.dueDate = dueDate;
        this.reminderTime = reminderTime;
        this.category = category;
        this.notified = false;
    }
    public LocalDateTime getReminderDateTime() {
        return dueDate.minus(reminderTime);
    }

    public String getCategory(){
        return category;
    }

    public boolean isNotified(){
        return notified;
    }


    public void markNotified() {
        this.notified = true;
    }

    // Getter and Setter methods for encapsulation
    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsComplete() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Title: " + title + "\nDescription: " + description +
                "\nDue Date: " + dueDate.format(formatter) +
                "\nCategory: " + category +
                "\nCompleted: " + (isCompleted ? "Yes" : "No") + "\n";
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

}