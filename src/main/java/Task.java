import java.util.ArrayList;

/**
 * Abstract class representing the general form of a task.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor for creating a new Task.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the specific type of the task.
     *
     * @return A string representing the type of the task.
     */
    public abstract String getTaskType();

    /**
     * Gets the status icon indicating whether the task is done.
     *
     * @return A string ("X" or " ") indicating the completion status of the task.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns a string representation of the task, showing its type, status, and description.
     *
     * @return A formatted string of the task details.
     */
    @Override
    public String toString() {
        return "[" + getTaskType() + "][" + getStatusIcon() + "] " + description;
    }

    // Static methods and fields for task list management
    private static ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the static task list.
     *
     * @param task The task to be added.
     */
    public static void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the static task list by index.
     *
     * @param index The index of the task to delete.
     * @return The removed task.
     */
    public static Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Gets a task from the static task list by index.
     *
     * @param index The index of the task to retrieve.
     * @return The retrieved task.
     */
    public static Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns all tasks in the static task list.
     *
     * @return An ArrayList of all tasks.
     */
    public static ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the static task list.
     *
     * @return The size of the task list.
     */
    public static int size() {
        return tasks.size();
    }

    /**
     * Checks if the static task list is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public static boolean isEmpty() {
        return tasks.isEmpty();
    }
}

/**
 * Represents a task without any date/time attached to it.
 */
class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String getTaskType() {
        return "T";
    }
}

/**
 * Represents a task that needs to be completed before a specific date/time.
 */
class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}

/**
 * Represents an event that starts and ends at specific times.
 */
class Event extends Task {
    private String from;
    private String to;


    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
 * Searches for tasks containing the specified keyword in their descriptions.
 *
 * @param keyword The keyword to search for in the task descriptions.
 * @return An ArrayList of tasks that contain the keyword in their descriptions.
 */
public static ArrayList<Task> findTasks(String keyword) {
    ArrayList<Task> foundTasks = new ArrayList<>();
    for (Task task : tasks) {
        if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
            foundTasks.add(task);
        }
    }
    return foundTasks;
}

}
