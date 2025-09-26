package finch.ui;

import finch.task.Task;
import finch.task.TaskList;

import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    private static final String DIVIDER = "    ____________________________________________________________";

    // Prints a divider line
    public void showLine(){
        System.out.println(DIVIDER);
    }

    public Ui(){
        this.scanner = new Scanner(System.in);
    }

    // Constant for the bot's name
    private static final String NAME = "Finch";

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

    // Show goodbye message
    public void showGoodbye(){
        System.out.println("    Bye! Hope to see you again soon!");
    }

    // Show error message
    public void showError(String message){
        System.out.println("    OOPS!!! " + message);
        showLine();
    }

    // Show all tasks
    public void showTasks(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println("    There are no tasks in your list yet!");
        } else {
            System.out.println("    Here are your tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("    " + (i + 1) + ". " + tasks.getTask(i));
            }
        }
        showLine();
    }

    // Show added task
    public void showAdded(Task task, int totalTasks) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in your list.");
        showLine();
    }

    // Show deleted task
    public void showDeleted(Task task,  int totalTasks) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + totalTasks + " tasks in your list.");
        showLine();
    }

    // Show marked task
    public void showMarked(Task task) {
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
        showLine();
    }

    // Show unmarked task
    public void showUnmarked(Task task) {
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        showLine();
    }

    public void showLoadingError(String message){
        System.out.println("    OOPS! Could not load tasks: " + message);
        showLine();
    }

    // Show available commands
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
        showLine();
    }
}
