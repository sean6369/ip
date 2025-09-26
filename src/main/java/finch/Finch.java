package finch;

import finch.task.TaskList;
import finch.exception.FinchException;
import finch.storage.Storage;
import finch.parser.Parser;
import finch.ui.Ui;
import finch.command.Command;

import java.util.Scanner;

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

        try (Scanner sc = new Scanner(System.in)) { // try-with-resources ensures scanner closes automatically
            while (!isExit) {
                String fullCommand;
                try {
                    // Read user input
                    fullCommand = ui.readCommand(sc);

                    // Show divider line
                    ui.showLine();

                    // Parse and execute command
                    Command command = Parser.parse(fullCommand);
                    command.execute(tasks, ui, storage);

                    // Check if command indicates exit
                    isExit = command.isExit();

                } catch (FinchException e) {
                    // Handle all known Finch exceptions gracefully
                    ui.showError(e.getMessage());
                } catch (Exception e) {
                    // Catch any unexpected runtime errors to prevent crash
                    ui.showError("Unexpected error: " + e.getMessage());
                } finally {
                    // Always show a line after each command
                    ui.showLine();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Finch("data/tasks.txt").run();
    }
}