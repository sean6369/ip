import java.util.Scanner;

public class Finch {

    // Constant for the bot's name
    private static final String NAME = "Finch";

    // Prints a horizontal line
    private static void printHorizontalLine() {
        System.out.println("    ____________________________________________________________");
    }

    // Array to store tasks
    private static final Task[] tasks = new Task[100];

    // Counter for the number of tasks
    private static int taskCount = 0;

    public static void main(String[] args) {
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
                    System.out.println("    Sorry, I don’t recognize that command!");
                    System.out.println("    Available commands: list, todo, deadline, event, mark, unmark, bye");
                    printHorizontalLine();
                    break;
                }

            } catch (Exception e) {
                System.out.println("    OOPS!!! Something went wrong: " + e.getMessage());
                printHorizontalLine();
            }
        }
    }

    // Method for helper function to add tasks
    private static void addTask(Task task) {
        try {
            if (task == null) {
                System.out.println("    OOPS!!! Cannot add an empty task!");
                printHorizontalLine();
                return;
            }

            if (taskCount >= tasks.length) {
                System.out.println("    OOPS!!! Cannot add more tasks, the list is full!");
                printHorizontalLine();
                return;
            }

            tasks[taskCount] = task;
            System.out.println("    Got it. I've added this task");
            System.out.println("    " + tasks[taskCount]);
            taskCount++;
            System.out.println("    Now you have " + taskCount + " tasks in the list");
            printHorizontalLine();

        } catch (Exception e) {
            System.out.println("    OOPS!!! Something went wrong while adding the task: " + e.getMessage());
            printHorizontalLine();
        }
    }

    // Method to add todo
    private static void addToDo(String userInput) {
        // Extract everything after "todo"
        String description = userInput.substring(4).trim();

        // Handle empty description
        if (description.isEmpty()) {
            System.out.println("    OOPS!!! The description of a todo cannot be empty!");
            printHorizontalLine();
            return;
        }

        // If valid, create and let addTask handle the rest
        Task newTask = new ToDo(description);
        addTask(newTask);
    }

    // Method to add deadline
    private static void addDeadline(String userInput) {
        // Check if the user included the "/by" keyword
        if (!userInput.contains(" /by")) {
            System.out.println("    OOPS! The deadline command must be followed by <description> /by <date/time>.");
            printHorizontalLine();
            return;
        }

        // Split description and date/time
        String[] parts = userInput.split(" /by", 2); // Limit 2 to avoid splitting extra /by
        String description = parts[0].substring(8).trim(); // Get the description after "deadline"
        String by = parts[1].trim(); // Get the date/time

        // Handle empty description or empty date/time
        if (description.isEmpty()) {
            System.out.println("    OOPS! The description of a deadline cannot be empty!");
            printHorizontalLine();
            return;
        }

        if (by.isEmpty()) {
            System.out.println("    OOPS! Please provide a date/time for the deadline!");
            printHorizontalLine();
            return;
        }

        // Create the Deadline task and pass it to addTask
        Task newTask = new Deadline(description, by);
        addTask(newTask);
    }

    // Method to add event
    private static void addEvent(String userInput) {
        // Check if the user included both "/from and /to"
        if (!userInput.contains(" /from") || !userInput.contains(" /to")) {
            System.out.println("    OOPS! The event command needs to be followed by <description> /from <date/time> /to date/time>.");
            printHorizontalLine();
            return;
        }

        // Split the input safely into 3 parts: description, from and to
        String[] parts = userInput.split(" /from| /to", 3);
        String description = parts[0].substring(5).trim(); // Get the description after "event"
        String from = parts[1].trim(); // Get the start date/time
        String to = parts[2].trim(); // Get the end date/time

        // Handle empty description or empty dates
        if (description.isEmpty()) {
            System.out.println("    OOPS! The description of a event cannot be empty!");
            printHorizontalLine();
            return;
        }

        if (from.isEmpty() || to.isEmpty()) {
            System.out.println("    OOPS! Please provide both start (/from) and end (/to) date/time.");
            printHorizontalLine();
            return;
        }

        // Create the Event and pass it to addTask
        Task newTask = new Event(description, from, to);
        addTask(newTask);
    }

    //Method to list all tasks
    private static void listTasks() {
        if (taskCount == 0) {
            System.out.println("    There are no tasks in the list yet!");
            printHorizontalLine();
            return;
        }

        System.out.println("    Here are your tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("    " + (i + 1) + ". " + tasks[i]);
        }
        printHorizontalLine();
    }

    //Method to mark task
    private static void markTask(String userInput) {

        String[] parts = userInput.trim().split("\\s+");

        // Check if user provided exactly 1 argument
        if (parts.length != 2) {
            System.out.println("    OOPS! The mark command should be followed by exactly one task number.");
            printHorizontalLine();
            return;
        }

        try {
            // Try converting to integer
            int taskIndex = Integer.parseInt(parts[1]) - 1;

            // Check if the number is valid
            if (taskIndex < 0 || taskIndex >= taskCount) {
                System.out.println("    Task number " + (taskIndex + 1) + " does not exist!");
                printHorizontalLine();
                return;
            }

            // Mark as done
            tasks[taskIndex].markAsDone();
            System.out.println("    Nice! I've marked this task as done:");
            System.out.println("    " + tasks[taskIndex]);
            printHorizontalLine();

        } catch (NumberFormatException e) {
            System.out.println("    Task number must be a valid integer!");
            printHorizontalLine();
        }
    }

    //Method to unmark task
    private static void unmarkTask(String userInput) {

        String[] parts = userInput.trim().split("\\s+");

        // Check if user provided a task number
        if (parts.length != 2) {
            System.out.println("    OOPS! The mark command should be followed by exactly one task number.");
            printHorizontalLine();
            return;
        }

        try {
            // Try converting to integer
            int taskIndex = Integer.parseInt(parts[1]) - 1;

            // Check if the number is valid
            if (taskIndex < 0 || taskIndex >= taskCount) {
                System.out.println("    Task number " + (taskIndex + 1) + " does not exist!");
                printHorizontalLine();
                return;
            }

            // Unmark as not done
            tasks[taskIndex].unmark();
            System.out.println("    OK, I've marked this task as not done yet:");
            System.out.println("    " + tasks[taskIndex]);
            printHorizontalLine();

        } catch (NumberFormatException e) {
            System.out.println("    Task number must be a valid integer!");
            printHorizontalLine();
        }
    }
}