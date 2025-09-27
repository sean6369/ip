package finch.ui;

import finch.task.Task;
import finch.task.TaskList;

import java.util.Scanner;

/**
 * Handles all user interactions in the Finch application.
 * Responsible for reading input, displaying messages, and showing task lists.
 */
public class Ui {

    // Scanner object for reading user input from console
    private final Scanner scanner;

    // Divider string for consistent output formatting
    private static final String DIVIDER = "    ____________________________________________________________";

    // Constant for the bot's name
    private static final String NAME = "Finch";

    // Constructs a Ui instance and initialises the Scanner
    public Ui(){
        this.scanner = new Scanner(System.in);
    }

    // Prints a divider line for better readability in the console
    public void showLine(){
        System.out.println(DIVIDER);
    }

    // Displays the welcome message when the application starts
    public void showWelcomeMessage(){
        System.out.println("""
                        ███████╗██╗███╗   ██╗ ██████╗██╗  ██╗
                        ██╔════╝██║████╗  ██║██╔════╝██║  ██║
                        █████╗  ██║██╔██╗ ██║██║     ███████║
                        ██╔══╝  ██║██║╚██╗██║██║     ██╔══██║
                        ██║     ██║██║ ╚████║╚██████╗██║  ██║
                        ╚═╝     ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝  ╚═╝
                """.stripTrailing());
        showLine();
        System.out.println("    Hello! I'm " + NAME);
        System.out.println("    What can I do for you?");
        showLine();
    }

    // Reads a line of input from the user
    public String readCommand() {
        return scanner.nextLine();
    }

    // Show goodbye message when the application exits
    public void showGoodbye(){
        System.out.println("    Bye! Hope to see you again soon!");
    }

    // Show error message
    public void showError(String message){
        System.out.println("    OOPS!!! " + message);
    }

    // Show all tasks in the given TaskList
    public void showTasks(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println("    There are no tasks in your list yet!");
            return;
        }

        System.out.println("    Here are your tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            try {
                System.out.printf("    %d. %s%n", i + 1, tasks.getTask(i));
            } catch (Exception e) {
                System.out.println("    Error retrieving task: " + e.getMessage());
            }
        }
    }

    // Shows a message after adding a task
    public void showAdded(Task task, int totalTasks) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in your list.");
    }

    // Shows a message after deleting a task
    public void showDeleted(Task task,  int totalTasks) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in your list.");
    }

    // Shows a message after marking a task as done
    public void showMarked(Task task) {
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
    }

    // Shows a message after unmarking task as not done
    public void showUnmarked(Task task) {
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
    }

    // Show Loading Error when tasks cannot be loaded from storage
    public void showLoadingError(String message){
        System.out.println("    OOPS! Could not load tasks: " + message);
    }

    // Displays a list of all available commands to the user
    public void showCommands() {
        System.out.println("    Available commands:");
        System.out.println("      list                - Show all tasks");
        System.out.println("      todo <desc>         - Add a ToDo");
        System.out.println("      deadline <desc> /by <date> - Add a Deadline");
        System.out.println("      event <desc> /from <start> /to <end> - Add an Event");
        System.out.println("      mark <number>       - Mark a task as done");
        System.out.println("      unmark <number>     - Mark a task as not done");
        System.out.println("      delete <number>     - Delete a task");
        System.out.println("      bye                 - Exit Finch");
    }
}
