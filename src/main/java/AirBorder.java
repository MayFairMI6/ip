import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Main application class that orchestrates the running of the AirBorder task manager.
 */
public class AirBorder {
    private Ui ui;
    private Storage storage;

    /**
     * Initializes the application by setting up the user interface and storage.
     */
    public AirBorder() {
        ui = new Ui();
        storage = new Storage("./data/tasks.txt");

    }

    /**
     * Starts the application and remains in the command processing loop until 'exit' command is issued.
     */
    public void run() {
        ui.showWelcomeMessage();
        boolean exit = false;
        while (!exit) {
            String command = ui.readCommand();
            try {
                exit = processCommand(command);
            } catch (Exception e) {
                ui.showError("An error occurred: " + e.getMessage());
            }
        }
        ui.showExitMessage();
        ui.close();
    }

    /**
     * Processes user commands.
     *
     * @param command The command string entered by the user.
     * @return true if the command is "exit", signaling the end of the application run.
     */
    private boolean processCommand(String command) throws IOException {
        // Trim and convert the entire command to lower case for uniform processing
        command = command.trim().toLowerCase();
        String[] parts = command.split("\\s+", 2); // Split by spaces, limiting to two parts
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        try {
            switch (commandWord) {
                case "todo":
                case "deadline":
                case "event":
                    handleTaskCommand(commandWord, arguments);
                    break;
                case "list":
                    ui.showTaskList(); // Directly calls Task.getAllTasks() internally
                    break;
                case "delete":
                    handleDeleteCommand(arguments);
                    break;
                case "mark":
                case "unmark":
                    handleMarkCommand(commandWord, arguments);
                    break;
                case "find":
                    ArrayList<Task> foundTasks = Task.findTasks(arguments);
                    ui.showFoundTasks(foundTasks);
                    break;
                case "exit":
                    return true; // Signal to exit the application
                default:
                    throw new IllegalArgumentException("Unrecognized command! Please try again.");
            }
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
        return false; // Continue running unless "exit" command is issued
    }

    /**
     * Handles adding tasks based on command type.
     *
     * @param type The type of task command (todo, deadline, event).
     * @param description Description and possibly date information for the task.
     */

    private String[] parseCommandDetails(String description, String keyword) throws IllegalArgumentException {
        // Using a regex that handles multiple spaces and is case-insensitive for the keyword
        String[] parts = description.split("\\s+" + Pattern.quote(keyword) + "\\s+", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Error: Missing '" + keyword + "' with required details.");
        }
        return parts;
    }

    private void handleTaskCommand(String type, String description) throws IOException {
        Task newTask = null;
        try {
            switch (type) {
                case "todo":
                    newTask = new ToDo(description);
                    break;
                case "deadline":
                    // Ensure we use "by" as the keyword for deadlines
                    String[] deadlineParts = parseCommandDetails(description, "by");
                    newTask = new Deadline(deadlineParts[0], deadlineParts[1]);
                    break;
                case "event":
                    // Parsing description for event with "from" and "to"
                    String[] eventDescriptionParts = parseCommandDetails(description, "from");
                    String[] eventTimeParts = parseCommandDetails(eventDescriptionParts[1], "to");
                    newTask = new Event(eventDescriptionParts[0], eventTimeParts[0], eventTimeParts[1]);
                    break;
            }
            Task.addTask(newTask);
            storage.save(Task.getAllTasks());
            ui.showTaskAdded(newTask);
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Handles the deletion of a task.
     *
     * @param indexStr The index string of the task to delete.
     */
    private void handleDeleteCommand(String indexStr) throws IOException {
        int index = Integer.parseInt(indexStr.trim()) - 1;
        Task removedTask = Task.deleteTask(index);
        storage.save(Task.getAllTasks());
        ui.showTaskDeleted(removedTask);
    }

    /**
     * Handles marking a task as done or not done.
     *
     * @param commandWord The command word (mark or unmark).
     * @param indexStr The index string of the task to update.
     */
    private void handleMarkCommand(String commandWord, String indexStr) throws IOException {
        int index = Integer.parseInt(indexStr.trim()) - 1;
        Task task = Task.getTask(index);
        if (commandWord.equals("mark")) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        storage.save(Task.getAllTasks());
        ui.showTaskUpdated(task);
    }

    public static void main(String[] args) {
        new AirBorder().run();
    }
}
