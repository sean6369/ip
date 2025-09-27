package finch;

import finch.task.TaskList;
import finch.exception.FinchException;
import finch.storage.Storage;
import finch.parser.Parser;
import finch.ui.Ui;
import finch.command.Command;

public class Finch {

    // Handles reading from and writing to the data file (persistent storage for tasks)
    private final Storage storage;

    // Use TaskList object to manage all tasks and task-related operations
    private final TaskList tasks;

    // Handles all user interactions (messages, input prompts, error messages)
    private final Ui ui;

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

    // Main entry point for the Finch application
    public static void main(String[] args) {
        new Finch("data/tasks.txt").run();
    }
}