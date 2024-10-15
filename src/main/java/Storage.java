import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * Handles loading and saving tasks to and from a file.
 */
public class Storage {
    private Path path;

    /**
     * Constructs a Storage object that manages the file used to store task data.
     * @param filepath Path to the file used for storing task data.
     */
    public Storage(String filepath) {
        this.path = Paths.get(filepath).toAbsolutePath().normalize();
        ensureDirectoryExists(path.getParent());
    }

    /**
     * Ensures that the directory for the storage file exists.
     * @param directoryPath Path to the directory to check and create if needed.
     */
    private void ensureDirectoryExists(Path directoryPath) {
        try {
            Files.createDirectories(directoryPath);
        } catch (IOException e) {
            System.err.println("Error creating storage directory: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the file into an ArrayList.
     * @return ArrayList containing all loaded tasks.
     * @throws IOException If an I/O error occurs reading from the file.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = parseTask(line);
                    if (task != null) tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * Saves the list of tasks to the file.
     * @param tasks ArrayList of Task objects to save.
     * @throws IOException If an I/O error occurs writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Task task : tasks) {
                writer.write(task.toString() + System.lineSeparator());
            }
        }
    }

    /**
     * Parses a line from the storage file into a Task object.
     * @param line The line from the file to parse.
     * @return The Task object represented by the line, or null if the line is invalid.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null; // Basic validation to ensure data integrity
        String type = parts[0].trim(); // 'T', 'D', or 'E' indicating the task type
        boolean isDone = parts[1].trim().equals("1"); // '1' if done, '0' otherwise
        String description = parts[2].trim();
        Task task = null;
        switch (type) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                if (parts.length < 4) return null; // Ensure there's a deadline part
                String deadline = parts[3].trim();
                task = new Deadline(description, deadline);
                break;
            case "E":
                if (parts.length < 5) return null; // Ensure there are start and end times
                String startTime = parts[3].trim();
                String endTime = parts[4].trim();
                task = new Event(description, startTime, endTime);
                break;
            default:
                return null; // Invalid task type
        }
        if (isDone) {
            task.markAsDone(); // Set the task as done if it was stored as done
        }
        return task;
    }
}
