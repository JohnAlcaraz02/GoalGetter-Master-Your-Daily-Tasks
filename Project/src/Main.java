import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.Duration;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        TaskNotifier taskNotifier = new TaskNotifier(userManager);
        DatabaseManager dbManager = new DatabaseManager();  // Instantiate DatabaseManager
        dbManager.getConnection();  // Establish a connection to the database

        while (true) {
            System.out.println(" ██████╗  ██████╗  █████╗ ██╗      ██████╗ ███████╗████████╗████████╗███████╗██████╗ \n" +
                    "██╔════╝ ██╔═══██╗██╔══██╗██║     ██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗\n" +
                    "██║  ███╗██║   ██║███████║██║     ██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝\n" +
                    "██║   ██║██║   ██║██╔══██║██║     ██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗\n" +
                    "╚██████╔╝╚██████╔╝██║  ██║███████╗╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║\n" +
                    " ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝\n" +
                    "                                                                                     ");
            System.out.println("\n=========================");
            System.out.println("      📋 TO-DO LIST       ");
            System.out.println("=========================");
            System.out.println("1️⃣  Sign Up");
            System.out.println("2️⃣  Log In");
            System.out.println("3️⃣  Log Out");
            System.out.println("4️⃣  Exit");
            System.out.println("=========================");
            System.out.print("👉 Enter your choice: ");

            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // consume the newline
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number between 1 to 5.");
                scanner.nextLine();  // consume invalid input
                continue;
            }

            switch (choice) {
                case 1: // Sign Up
                    System.out.println("\n==================== SIGN UP ====================");
                    System.out.print("\uD83D\uDC64 Enter username: ");
                    String signUpUsername = scanner.nextLine();
                    System.out.print("\uD83D\uDD11 Set a 4-digit PIN: ");
                    String signUpPassword = scanner.nextLine();
                    userManager.signUp(signUpUsername, signUpPassword);
                    dbManager.signUpUser(signUpUsername, signUpPassword);  // Sign up user in the database
                    break;

                case 2: // Log In
                    System.out.println("\n==================== LOG IN ====================");
                    if (userManager.getLoggedInUser() != null) {
                        System.out.println("⚠\uFE0F Logging out the current user before new login.");
                        userManager.logOut();
                    }

                    System.out.print("\uD83D\uDC64 Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("\uD83D\uDD11 Enter password: ");
                    String loginPassword = scanner.nextLine();
                    if (userManager.logIn(loginUsername, loginPassword)) {
                        taskNotifier.startNotificationService();
                        // Pass dbManager into the method
                        manageToDoList(scanner, userManager.getLoggedInUser().getToDoList(), taskNotifier, loginUsername, dbManager);
                        taskNotifier.stopNotificationService();
                    } else {
                        System.out.println("❌ Login failed. Invalid username or password.");
                    }

                    break;

                case 3: // Log Out
                    userManager.logOut();
                    break;

                case 4: // Exit
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }

    private static void manageToDoList(Scanner scanner, ToDoList toDoList, TaskNotifier taskNotifier, String username, DatabaseManager dbManager) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            System.out.println("\n===================================");
            System.out.println("       📌 TO-DO LIST MENU          ");
            System.out.println("===================================");
            System.out.println("1️⃣  Add Task");
            System.out.println("2️⃣  Remove Task");
            System.out.println("3️⃣  List Tasks");
            System.out.println("4️⃣  Mark Task as Completed");
            System.out.println("5️⃣  Back to Main Menu");
            System.out.println("===================================");
            System.out.print("👉 Enter your choice: ");


            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // consume the newline
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number between 1 to 5.");
                scanner.nextLine();  // consume invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    // Gather task details from user
                    System.out.println("\n==================== ADD NEW TASK ====================");
                    System.out.print("\uD83D\uDCCB Enter task title: ");
                    String title = scanner.nextLine();
                    System.out.print("\uD83D\uDCDD Enter task description: ");
                    String description = scanner.nextLine();

                    LocalDateTime dueDate = null;
                    while (dueDate == null) {
                        System.out.print("\uD83D\uDCC5 Enter due date and time (yyyy-MM-dd HH:mm): ");
                        String dueDateString = scanner.nextLine();
                        try {
                            dueDate = LocalDateTime.parse(dueDateString, formatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("❌ Invalid date format. Please try again.");
                        }
                    }

                    int reminderAmount = 0;
                    boolean validReminder = false;

                    while (!validReminder) {
                        try {
                            System.out.print("⏰ Enter reminder time: ");
                            reminderAmount = scanner.nextInt();
                            scanner.nextLine();  // consume newline
                            validReminder = true;
                        } catch (InputMismatchException e) {
                            System.out.println("❌ Invalid input! Please enter a number for the reminder time.");
                            scanner.nextLine();  // consume invalid input
                        }
                    }

                    System.out.print("⏳ Is the reminder in minutes or hours? (Enter 'm' for minutes, 'h' for hours): ");
                    String timeUnit = scanner.nextLine().toLowerCase();

                    Duration reminderDuration;
                    if (timeUnit.equals("m")) {
                        reminderDuration = Duration.ofMinutes(reminderAmount);
                    } else if (timeUnit.equals("h")) {
                        reminderDuration = Duration.ofHours(reminderAmount);
                    } else {
                        System.out.println("❌ Invalid input. Setting default reminder to 30 minutes.");
                        reminderDuration = Duration.ofMinutes(30);  // default
                    }

                    String category = null;
                    while (category == null || category.trim().isEmpty()) {
                        System.out.print("\uD83D\uDCC2 Enter task category: ");
                        category = scanner.nextLine();
                        if (category.trim().isEmpty()) {
                            System.out.println("Category cannot be empty. Please enter a valid category.");
                        }
                    }

                    // Create new task and add to in-memory list
                    Task newTask = new Task(title, description, dueDate, reminderDuration, category);
                    toDoList.addTask(newTask);  // Add to in-memory list

                    int userId = dbManager.getUserId(username);
                    int categoryId = dbManager.addCategory( userId, title, dueDate.format(formatter), reminderAmount, false);


                    // Save task to database
                    dbManager.addTask(username, categoryId, title, description, dueDate.format(formatter));

                    // Notify user about the task being added
                    taskNotifier.scheduleNewTaskReminder(newTask);
                    System.out.println("✅ Task added successfully!");

                    break;

                case 2:
                    System.out.print("Enter the title of the task to remove: ");
                    String taskToRemove = scanner.nextLine();
                    toDoList.removeTask(taskToRemove);
                    break;

                case 3:
                    System.out.print("Enter category to filter by (or leave blank): ");
                    String filterCategory = scanner.nextLine();
                    toDoList.listTasks(filterCategory);
                    break;

                case 4:
                    System.out.print("Enter the title of the task to mark as complete: ");
                    String taskToComplete = scanner.nextLine();
                    toDoList.markTaskAsComplete(taskToComplete);
                    break;

                case 5:
                    return;  // Go back to the main menu

                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }
}
