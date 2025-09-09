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
        tasks[taskCount] = task;
        System.out.println("    Got it. I've added this task");
        System.out.println("    " + tasks[taskCount]);
        taskCount++;
        System.out.println("    Now you have " + taskCount + " tasks in the list");
        printHorizontalLine();
    }

    // Method to add todo
    private static void addToDo(String userInput) {
        String taskDescription = userInput.substring(5); // Get the description after "todo "
        addTask(new ToDo(taskDescription));
    }

    // Method to add deadline
    private static void addDeadline(String userInput) {
        // Check if the input contains "/by"
        if (!userInput.contains(" /by ")) {
            System.out.println("    OOPS! The deadline command needs to be followed by 'description /by date/time'");
            printHorizontalLine();
            return;
        }
        String[] parts = userInput.split(" /by ");
        String taskDescription = parts[0].substring(9); // Get the description after "deadline "
        String by = parts[1]; // Get the date/time
        addTask(new Deadline(taskDescription, by));
    }

    // Method to add event
    private static void addEvent(String userInput) {
        // Check if the input contains "/from and /to"
        if (!userInput.contains(" /from ") || !userInput.contains(" /to ")) {
            System.out.println("    OOPS! The event command needs to be followed by 'description /from date/time /to date/time'");
            printHorizontalLine();
            return;
        }
        String[] parts = userInput.split(" /from | /to ");
        String taskDescription = parts[0].substring(6); // Get the description after "event "
        String from = parts[1]; // Get the start date/time
        String to = parts[2]; // Get the end date/time
        addTask(new Event(taskDescription, from, to));
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
        try {
            String[] parts = userInput.trim().split("\\s+");

            // Check if user provided a task number
            if (parts.length < 2) {
                System.out.println("    Please provide the task number to mark!");
                printHorizontalLine();
                return;
            }

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
        try {
            String parts[] = userInput.trim().split("\\s+");

            // Check if user provided a task number
            if (parts.length < 2) {
                System.out.println("    Please provide the task number to unmark!");
                printHorizontalLine();
                return;
            }

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