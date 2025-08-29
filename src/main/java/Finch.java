import java.util.Scanner;

public class Finch {

    // Constant for the bot's name
    private static final String NAME = "Finch";

    // Prints a horizontal line
    private static void line() {
        System.out.println("    ____________________________________________________________");
    }

    //Array to store tasks
    private static final String[] tasks = new String[100];

    //Counter for the number of tasks
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
                                                    \s""");
        line();
        System.out.println("    Hello! I'm " + NAME);
        System.out.println("    What can I do for you?");
        line();

        String userInput;
        while (true) {
            System.out.println();
            userInput = scanner.nextLine();
            line();
            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                listTasks();
            } else {
                addTask(userInput);
            }
        }

        System.out.println("    Bye. Hope to see you again soon!");
        line();
        scanner.close();
    }

    //Method to add task
    private static void addTask(String task) {
        if (taskCount < tasks.length) {
            tasks[taskCount] = task;
            taskCount++;
            System.out.println("    Added task: " + task);
        } else {
            System.out.println("    Task list is full!");
        }
        line();
    }

    //Method to list all tasks
    private static void listTasks() {
        System.out.println("    Here are your tasks:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("    "  + (i + 1) + ". " + tasks[i]);
        }
        line();
    }
}