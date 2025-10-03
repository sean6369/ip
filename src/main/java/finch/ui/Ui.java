package finch.ui;

import finch.task.Task;
import finch.task.TaskList;

import java.util.Scanner;

/**
 * The {@code Ui} class handles all user interactions in the Finch application.
 * It is responsible for displaying messages, prompts, and errors to the user,
 * as well as reading input commands from the console.
 *
 * <p>Key responsibilities include:</p>
 * <ul>
 *     <li>Displaying the welcome message with ASCII art at startup</li>
 *     <li>Reading user commands from standard input</li>
 *     <li>Showing task lists, errors, and success messages</li>
 *     <li>Displaying feedback when tasks are added, deleted, marked, or unmarked</li>
 *     <li>Providing a list of all available commands</li>
 * </ul>
 */
public class Ui {

    /**
     * Constructs a {@code Ui} instance and initializes the Scanner
     * to read from standard input.
     */
    public Ui(){
        this.scanner = new Scanner(System.in);
    }

    // Scanner object for reading user input from console
    private final Scanner scanner;

    // Divider string for consistent output formatting
    private static final String DIVIDER = "    ____________________________________________________________";

    // Prints a divider line for better readability in the console
    public void showLine(){
        System.out.println(DIVIDER);
    }

    // Constant for the bot's name
    private static final String NAME = "Finch";

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

    /**
     * Reads a full line of input entered by the user.
     *
     * @return the raw input command string
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    // Show goodbye message when the application exits
    public void showGoodbye(){
        System.out.println("    Bye! Hope to see you again soon!");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display
     */
    public void showError(String message){
        System.out.println("    OOPS!!! " + message);
    }

    /**
     * Displays all tasks in the given {@link TaskList}.
     * If the list is empty, notifies the user that no tasks exist.
     *
     * @param tasks the {@code TaskList} containing all tasks
     */
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

    /**
     * Displays a confirmation message after a task is successfully added.
     *
     * @param task the task that was added
     * @param totalTasks the total number of tasks after the addition
     */
    public void showAdded(Task task, int totalTasks) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in your list.");
    }

    /**
     * Displays a confirmation message after a task is deleted.
     *
     * @param task the task that was deleted
     * @param totalTasks the total number of tasks remaining
     */
    public void showDeleted(Task task,  int totalTasks) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in your list.");
    }

    /**
     * Displays a message after marking a task as done.
     *
     * @param task the task that was marked as done
     */
    public void showMarked(Task task) {
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
    }

    /**
     * Displays a message after marking a task as not done.
     *
     * @param task the task that was unmarked
     */
    public void showUnmarked(Task task) {
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
    }

    /**
     * Displays an error message when tasks cannot be loaded from storage.
     *
     * @param message the error message from storage
     */
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
        System.out.println("      find <keyword>      - Find tasks containing a keyword");
        System.out.println("      bye                 - Exit Finch");
    }
}
