import java.util.ArrayList;
import java.util.Scanner;

public class AirBorder {
    // Use ArrayList for task storage
    private static ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        printWelcomeMessage();

        while (true) {
            String userCommand = inputScanner.nextLine().trim();

            if (userCommand.equalsIgnoreCase("bye")) {
                printExitMessage();
                break;
            }

            try {
                switch (getCommandType(userCommand)) {
                    case "list":
                        printTaskList();
                        break;
                    case "todo":
                        addToDoTask(userCommand.substring(5).trim());
                        break;
                    case "deadline":
                        addDeadlineTask(userCommand);
                        break;
                    case "event":
                        addEventTask(userCommand);
                        break;
                    case "delete":
                        deleteTask(userCommand);
                        break;
                    case "mark":
                        markTaskAsDone(userCommand);
                        break;
                    case "unmark":
                        unmarkTask(userCommand);
                        break;
                    default:
                        throw new InvalidCommandException();
                }
            } catch (AirBorderException e) {
                printErrorMessage(e.getMessage());
            }
        }

        inputScanner.close();
    }

    private static String getCommandType(String userCommand) {
        if (userCommand.startsWith("todo ")) {
            return "todo";
        } else if (userCommand.startsWith("deadline ")) {
            return "deadline";
        } else if (userCommand.startsWith("event ")) {
            return "event";
        } else if (userCommand.startsWith("delete ")) {
            return "delete";
        } else if (userCommand.equalsIgnoreCase("list")) {
            return "list";
        } else if (userCommand.startsWith("mark ")) {
            return "mark";
        } else if (userCommand.startsWith("unmark ")) {
            return "unmark";
        } else {
            return "unknown";
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Welcome aboard AirBorder.");
        System.out.println(" Ready to assist you with your tasks!");
        System.out.println("____________________________________________________________");
    }

    private static void printExitMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Thank you for flying with AirBorder!");
        System.out.println("____________________________________________________________");
    }

    private static void printTaskList() throws AirBorderException {
        if (taskList.isEmpty()) {
            throw new AirBorderException("No tasks in your itinerary yet. Please add tasks before listing.");
        }
        System.out.println("____________________________________________________________");
        System.out.println(" Here are the tasks in your itinerary:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + taskList.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    private static void addToDoTask(String description) throws AirBorderException {
        if (description.isEmpty()) {
            throw new AirBorderException("Insufficient Documentation: Task description cannot be empty. Please provide a valid task.");
        }
        taskList.add(new ToDo(description));
        printTaskAddedMessage(taskList.get(taskList.size() - 1));
    }

    private static void addDeadlineTask(String userCommand) throws AirBorderException {
        String[] taskDetails = userCommand.substring(9).split(" /by ");
        if (taskDetails.length < 2 || taskDetails[0].trim().isEmpty()) {
            throw new AirBorderException("Insufficient Documentation: Invalid deadline format. Use: deadline <description> /by <date>");
        }
        taskList.add(new Deadline(taskDetails[0].trim(), taskDetails[1].trim()));
        printTaskAddedMessage(taskList.get(taskList.size() - 1));
    }

    private static void addEventTask(String userCommand) throws AirBorderException {
        String[] taskDetails = userCommand.substring(6).split(" /from | /to ");
        if (taskDetails.length < 3 || taskDetails[0].trim().isEmpty()) {
            throw new AirBorderException("Minimum Passport Validity Condition Not Met for Destination: Invalid event format. Use: event <description> /from <start> /to <end>");
        }
        taskList.add(new Event(taskDetails[0].trim(), taskDetails[1].trim(), taskDetails[2].trim()));
        printTaskAddedMessage(taskList.get(taskList.size() - 1));
    }

    private static void deleteTask(String userCommand) throws AirBorderException {
        int taskIndex = Integer.parseInt(userCommand.split(" ")[1]) - 1;
        if (isValidTaskIndex(taskIndex)) {
            Task removedTask = taskList.remove(taskIndex);
            printTaskDeletedMessage(removedTask);
        } else {
            throw new AirBorderException("Check-In Refused: Invalid task number.");
        }
    }

    private static void markTaskAsDone(String userCommand) throws AirBorderException {
        int taskIndex = Integer.parseInt(userCommand.split(" ")[1]) - 1;
        if (isValidTaskIndex(taskIndex)) {
            taskList.get(taskIndex).markAsDone();
            printTaskDoneMessage(taskList.get(taskIndex));
        } else {
            throw new AirBorderException("Check-In Refused: Invalid task number.");
        }
    }

    private static void unmarkTask(String userCommand) throws AirBorderException {
        int taskIndex = Integer.parseInt(userCommand.split(" ")[1]) - 1;
        if (isValidTaskIndex(taskIndex)) {
            taskList.get(taskIndex).markAsNotDone();
            printTaskUndoneMessage(taskList.get(taskIndex));
        } else {
            throw new AirBorderException("Check-In Refused: Invalid task number.");
        }
    }

    private static boolean isValidTaskIndex(int taskIndex) {
        return taskIndex >= 0 && taskIndex < taskList.size();
    }

    private static void printTaskAddedMessage(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Flight Confirmed! I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void printTaskDeletedMessage(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void printTaskDoneMessage(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Task completed! Ready for boarding:");
        System.out.println("   " + task);
        System.out.println("____________________________________________________________");
    }

    private static void printTaskUndoneMessage(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Task unchecked. Unable to board yet:");
        System.out.println("   " + task);
        System.out.println("____________________________________________________________");
    }

    private static void printErrorMessage(String errorMessage) {
        System.out.println("____________________________________________________________");
        System.out.println(" ERROR: " + errorMessage);
        System.out.println("____________________________________________________________");
    }
}
