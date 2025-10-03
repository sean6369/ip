package finch;

import finch.task.TaskList;
import finch.exception.FinchException;
import finch.storage.Storage;
import finch.parser.Parser;
import finch.ui.Ui;
import finch.command.Command;

/**
 * The main application class for Finch, a command-line task management chatbot.
 * <p>
 * Finch handles loading and saving tasks from persistent storage, processing
 * user commands, and interacting with the user via the {@link Ui} class.
 */
public class Finch {

    // Handles reading from and writing to the data file (persistent storage for tasks)
    private final Storage storage;

    // Use TaskList object to manage all tasks and task-related operations
    private final TaskList tasks;

    // Handles all user interactions (messages, input prompts, error messages)
    private final Ui ui;

    /**
     * Constructs a Finch application instance.
     *
     * <p>This constructor initializes the {@link Ui}, loads tasks from the
     * specified file path using {@link Storage}, and handles any errors
     * gracefully if storage cannot be initialized.
     *
     * @param filePath the path to the data file where tasks are stored
     */
    public Finch(String filePath) {
        this.ui = new Ui();
        Storage tempStorage;
        try {
            tempStorage = new Storage(filePath); // may throw FinchException
        } catch (FinchException e) {
            ui.showError("Failed to initialize storage: " + e.getMessage());
            tempStorage = null; // or handle gracefully
        }
        this.storage = tempStorage;

        TaskList loadedTasks;
        try {
            loadedTasks = storage != null ? storage.load() : new TaskList();
        } catch (FinchException e) {
            ui.showLoadingError(e.getMessage());
            loadedTasks = new TaskList();
        }
        this.tasks = loadedTasks;
    }

    /**
     * Starts the Finch application, displaying a welcome message and
     * entering the main command loop.
     *
     * <p>This method continuously reads user input, parses it into commands,
     * executes them, and handles exceptions. The loop exits when the user
     * enters the "bye" command.
     */
    public void run() {
        ui.showWelcomeMessage();
        boolean isExit = false;

        while (!isExit) {
            try {
                // Read user input directly using Ui
                String fullCommand = ui.readCommand();

                ui.showLine();

                // Parse and execute command
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);

                // Check exit condition
                isExit = command.isExit();

            } catch (FinchException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * The main entry point for the Finch application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Finch("data/tasks.txt").run();
    }
}