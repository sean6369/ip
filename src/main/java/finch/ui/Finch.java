package finch.ui;

import finch.task.Deadline;
import finch.task.Event;
import finch.exception.FinchException;
import finch.task.Task;
import finch.task.ToDo;
import finch.storage.Storage;

import java.util.ArrayList;
import java.util.Scanner;

public class Finch {

    // Constant for the bot's name
    private static final String NAME = "Finch";

    // Prints a horizontal line
    private static void printHorizontalLine() {
        System.out.println("    ____________________________________________________________");
    }

    private static Storage storage;

    // Use ArrayList to store tasks
    private static ArrayList<Task> tasks;

    public static void main(String[] args) {
        storage = new Storage("./data/finch.txt");

        try {
            tasks = storage.load();
        } catch (FinchException e) {
            System.out.println("    OOPS! Could not load tasks: " + e.getMessage());
            tasks = new ArrayList<>();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                        ███████╗██╗███╗   ██╗ ██████╗██╗  ██╗
                        ██╔════╝██║████╗  ██║██╔════╝██║  ██║
                        █████╗  ██║██╔██╗ ██║██║     ███████║
                        ██╔══╝  ██║██║╚██╗██║██║     ██╔══██║
                        ██║     ██║██║ ╚████║╚██████╗██║  ██║
                        ╚═╝     ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝  ╚═╝
                """.stripTrailing());
        printHorizontalLine();
        System.out.println("    Hello! I'm " + NAME);
        System.out.println("    What can I do for you?");
        printHorizontalLine();

        String userInput;
        while (true) {
            try {
                userInput = scanner.nextLine();

                // Handle empty input
                if (userInput == null || userInput.trim().isEmpty()) {
                    printHorizontalLine();
                    System.out.println("    Please enter a command!");
                    printHorizontalLine();
                    continue;
                }
                printHorizontalLine();
                String[] inputParts = userInput.trim().split("\\s+"); // Splits the command by spaces (multiple spaces handled as well)
                String command = inputParts[0]; // Gets the first command word

                switch (command.toLowerCase()) {
                case "bye":
                    System.out.println("    Bye. Hope to see you again soon!");
                    printHorizontalLine();
                    scanner.close();
                    return; // Exit program

                case "list":
                    listTasks();
                    break;

                case "mark":
                    markTask(userInput);
                    break;

                case "unmark":
                    unmarkTask(userInput);
                    break;

                case "todo":
                    addToDo(userInput);
                    break;

                case "deadline":
                    addDeadline(userInput);
                    break;

                case "event":
                    addEvent(userInput);
                    break;

                default:
                    // Unknow command: throw FinchException
                    throw new FinchException(
                            "I don't recognise this command\n" +
                                    "    Available commands: list, todo, deadline, event, mark, unmark, bye"
                    );
                }

            } catch (FinchException e) {
                // All Finch-specific input errors are handled here
                System.out.println("    OOPS!!! " + e.getMessage());
                printHorizontalLine();

            } catch (Exception e) {
                // Any unexpected runtime errors
                System.out.println("    OOPS!!! Something went wrong: " + e.getMessage());
                printHorizontalLine();
            }
        }
    }

    // Method for helper function to add tasks
    private static void addTask(Task task) throws FinchException {
        if (task == null) {
            throw new FinchException("Cannot add an empty task!");
        }

        tasks.add(task);

        System.out.println("    Got it. I've added this task");
        System.out.println("    " + task);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list");
        printHorizontalLine();

        // Save updated task list
        storage.save(tasks);
    }

    // Method to add todo
    private static void addToDo(String userInput) throws FinchException {
        // Extract everything after "todo"
        String description = userInput.substring(4).trim();

        // Handle empty description
        if (description.isEmpty()) {
            throw new FinchException("The description of a todo cannot be empty!");
        }

        // If valid, create and let addTask handle the rest
        Task newTask = new ToDo(description);
        addTask(newTask);
    }

    // Helper method: parse user input and return a Deadline task
    private static Task parseDeadline(String userInput) throws FinchException {
        // Check if the user included the "/by" keyword
        if (!userInput.contains(" /by")) {
            throw new FinchException(
                    "The deadline command must be followed by <description> /by <date/time>."
            );
        }

        // Split description and date/time
        String[] parts = userInput.split(" /by", 2);
        String description = parts[0].substring(8).trim(); // Get description after "deadline"
        String by = parts[1].trim(); // Get the date/time

        // Handle empty description or empty date/time
        if (description.isEmpty()) {
            throw new FinchException("The description of a deadline cannot be empty!");
        }
        if (by.isEmpty()) {
            throw new FinchException("Please provide a date/time for the deadline!");
        }

        // Return the Deadline object
        return new Deadline(description, by);
    }

    // Method to add deadline
    private static void addDeadline(String userInput) throws FinchException {
        Task newTask = parseDeadline(userInput); //parseEvent throws FinchException if input is invalid
        addTask(newTask); // addTask also throws FinchException if something goes wrong
    }

    // Helper method: parse user input and return an Event task
    private static Task parseEvent(String userInput) throws FinchException {
        // Check if the user included both "/from and /to"
        if (!userInput.contains(" /from") || !userInput.contains(" /to")) {
            throw new FinchException("The event command must be followed by <description> /from <date/time> /to <date/time>.");
        }

        // Make sure "/from" comes before "/to"
        int fromIndex = userInput.indexOf(" /from");
        int toIndex = userInput.indexOf(" /to");

        if (fromIndex > toIndex) {
            throw new FinchException("The /from <date/time> must come before the /to <date/time>.");
        }

        // Split input safely into 3 parts: description, from and to
        String[] parts = userInput.split(" /from| /to", 3); // Limit 3 to avoid splitting extra /from or /to

        String description = parts[0].substring(5).trim(); // Get the description after "event"
        String from = parts[1].trim(); // Get the start date/time
        String to = parts[2].trim(); // Get the end date/time

        // Handle empty description or empty dates
        if (description.isEmpty()) {
            throw new FinchException("The description of an event cannot be empty!");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new FinchException("Please provide both start (/from) and end (/to) date/time.");
        }

        return new Event(description, from, to); // Return Task
    }

    // Method to add event
    private static void addEvent(String userInput) throws FinchException {
        Task newTask = parseEvent(userInput); // parseEvent throws FinchException if input is invalid
        addTask(newTask); // addTask also throws FinchException if something goes wrong
    }

    //Method to list all tasks
    private static void listTasks() throws FinchException {
        if (tasks.isEmpty()) {
            throw new FinchException("There are no tasks in the list yet!");
        }

        System.out.println("    Here are your tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + tasks.get(i));
        }
        printHorizontalLine();
    }

    //Method to mark task
    private static void markTask(String userInput) throws FinchException {

        String[] parts = userInput.trim().split("\\s+");

        // Check if user provided exactly 1 argument
        if (parts.length != 2) {
            throw new FinchException("The mark command should be followed by exactly one task number.");
        }

        try {
            // Try converting to integer
            int taskIndex = Integer.parseInt(parts[1]) - 1;

            // Check if the number is valid
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new FinchException("Task number " + (taskIndex + 1) + " does not exist!");
            }

            // Mark as done
            Task task = tasks.get(taskIndex);
            task.markAsDone();
            System.out.println("    Nice! I've marked this task as done:");
            System.out.println("    " + task);
            printHorizontalLine();

            // Save change immediately
            storage.save(tasks);

        } catch (NumberFormatException e) {
            throw new FinchException("Task number must be a valid integer!");
        }
    }

    //Method to unmark task
    private static void unmarkTask(String userInput) throws FinchException {

        String[] parts = userInput.trim().split("\\s+");

        // Check if user provided exactly 1 argument
        if (parts.length != 2) {
            throw new FinchException("The mark command should be followed by exactly one task number.");
        }

        try {
            // Try converting to integer
            int taskIndex = Integer.parseInt(parts[1]) - 1;

            // Check if the number is valid
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new FinchException("Task number " + (taskIndex + 1) + " does not exist!");
            }

            // Unmark as not done
            Task task = tasks.get(taskIndex);
            task.unmark();
            System.out.println("    OK, I've marked this task as not done yet:");
            System.out.println("    " + task);
            printHorizontalLine();

            // Save change immediately
            storage.save(tasks);

        } catch (NumberFormatException e) {
            throw new FinchException("Task number must be a valid integer!");
        }
    }
}