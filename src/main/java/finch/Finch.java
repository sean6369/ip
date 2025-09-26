package finch;

import finch.task.TaskList;
import finch.exception.FinchException;
import finch.storage.Storage;
import finch.parser.Parser;
import finch.ui.Ui;

import java.util.Scanner;

public class Finch {

    // Handles reading from and writing to the data file (persistent storage for tasks)
    private final Storage storage;

    // Use TaskList object to manage all tasks and task-related operations
    private final TaskList tasks;

    // Handles all user interactions (messages, input prompts, error messages)
    private final Ui ui;

    // Constructor
    public Finch(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        TaskList loadedTasks;
        try {
            loadedTasks = storage.load(); // TaskList returned
        } catch (FinchException e) {
            ui.showLoadingError(e.getMessage());
            loadedTasks = new TaskList(); // Start with empty TaskList
        }
        this.tasks = loadedTasks;
    }

    public void run() {
        ui.showWelcomeMessage();
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = sc.nextLine();
                String command = Parser.parseCommand(fullCommand);
                String arguments = Parser.parseArguments(fullCommand);

                switch (command) {
                case "list":
                    ui.showTasks(tasks); // TaskList handles task retrieval
                    break;

                case "todo":
                    tasks.addTodo(arguments); // TaskList creates ToDo
                    ui.showAdded(tasks.getLastTask(), tasks.size());
                    storage.save(tasks); // Save list to file
                    break;

                case "deadline":
                    tasks.addDeadline(arguments); // Tasklist creates Deadline
                    ui.showAdded(tasks.getLastTask(), tasks.size());
                    storage.save(tasks);
                    break;

                case "event":
                    tasks.addEvent(arguments); // TaskList creates Event
                    ui.showAdded(tasks.getLastTask(), tasks.size());
                    storage.save(tasks);
                    break;

                case "mark":
                    int markIndex = Integer.parseInt(arguments) - 1;
                    tasks.markTask(markIndex);
                    ui.showMarked(tasks.getTask(markIndex));
                    storage.save(tasks);
                    break;

                case "unmark":
                    int unmarkIndex = Integer.parseInt(arguments) - 1;
                    tasks.unmarkTask(unmarkIndex);
                    ui.showUnmarked(tasks.getTask(unmarkIndex));
                    storage.save(tasks);
                    break;

                case "delete":
                    int deleteIndex = Integer.parseInt(arguments) - 1;
                    var removed = tasks.deleteTask(deleteIndex);
                    ui.showDeleted(removed, tasks.size());
                    storage.save(tasks);
                    break;

                case "bye":
                    ui.showGoodbye();
                    isExit = true;
                    break;

                default:
                    ui.showError("Unknown command: " + command);
                    ui.showCommands();
                }
            } catch (FinchException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showError("Invalid number format!");
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Finch("data/tasks.txt").run();
    }
}