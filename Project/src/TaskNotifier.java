import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskNotifier {
    private ScheduledExecutorService scheduler;
    private final UserManager userManager;

    public TaskNotifier(UserManager userManager) {
        this.userManager = userManager;
    }

    // Starts the notification service by scheduling reminders for all tasks
    public void startNotificationService() {
        if (scheduler != null && !scheduler.isShutdown()) {
            return;
        }
        scheduler = Executors.newScheduledThreadPool(1);
        scheduleAllReminders(); // Schedule reminders for existing tasks
    }

    // Schedules reminders for all tasks in the user's to-do list
    private void scheduleAllReminders() {
        User loggedInUser = userManager.getLoggedInUser();
        if (loggedInUser != null) {
            List<Task> tasks = loggedInUser.getToDoList().getTasks();
            for (Task task : tasks) {
                scheduleReminder(task);
            }
        }
    }

    // Schedules a reminder for a specific task
    private void scheduleReminder(Task task) {
        if (task.isCompleted() || task.isNotified()) {
            return; // Skip completed or already notified tasks
        }

        LocalDateTime reminderTime = task.getReminderDateTime();
        Duration delay = Duration.between(LocalDateTime.now(), reminderTime);
        long delayInMillis = delay.toMillis();

        if (delayInMillis <= 0) {
            // Task is due or past due, notify immediately
            notifyUser(task);
        } else {
            // Schedule the notification at the exact reminder time
            scheduler.schedule(() -> notifyUser(task), delayInMillis, TimeUnit.MILLISECONDS);
        }
    }

    private void notifyUser(Task task) {
        System.out.println("Reminder: Task '" + task.getTitle() + "' is due soon! Due at: " + task.getDueDate());
        task.markNotified(); // Mark task as notified to prevent re-trigger
    }

    // If tasks are updated or a new task is added, schedule new reminders
    public void scheduleNewTaskReminder(Task task) {
        scheduleReminder(task);
    }

    // Stops the notification service
    public void stopNotificationService() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}