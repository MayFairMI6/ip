import java.util.Scanner;

/**
 * Handles all user interface operations, including displaying messages and reading user input.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Initializes the user interface by setting up the scanner used for input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a welcome message at the start of the application.
     */
    public void showWelcomeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Welcome aboard AirBorder.");
        System.out.println(" Ready to assist you with your travel needs!");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a line separator for better readability in the command line interface.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Shows tasks currently on the list.
     */
    public void showTaskList() {
        System.out.println("____________________________________________________________");
        if (Task.isEmpty()) {
            System.out.println(" No tasks to show.");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < Task.size(); i++) {
                System.out.println((i + 1) + ". " + Task.getTask(i));
            }
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating successful addition of a task.
     *
     * @param task The task that was added.
     */
    public void showTaskAdded(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + Task.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message when a task is successfully deleted.
     *
     * @param removedTask The task that was removed.
     */
    public void showTaskDeleted(Task removedTask) {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removedTask);
        System.out.println(" Now you have " + Task.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    public void showTaskUpdated(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've updated this task:");
        System.out.println("   " + task);
        System.out.println("____________________________________________________________");
    }

    /**
 * Displays the tasks found based on the search criteria.
 *
 * @param foundTasks The list of tasks that matched the search criteria.
 */
public void showFoundTasks(ArrayList<Task> foundTasks) {
    if (foundTasks.isEmpty()) {
        System.out.println("No tasks found with your search criteria.");
    } else {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < foundTasks.size(); i++) {
            System.out.println((i + 1) + ". " + foundTasks.get(i));
        }
    }
}


    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(" ERROR: " + message);
    }

    /**
     * Displays a goodbye message when the application is closing.
     */
    public void showExitMessage() {
        System.out.println(" Bye. Hope to see you across the border for your on-ground needs/connections!");
    }

    /**
     * Closes the scanner object and any other resources used for user interaction.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Reads a command from the user.
     * @return The command input by the user.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }
}
