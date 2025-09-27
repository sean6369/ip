package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class DeleteCommand extends Command {
    private final int index;

    // Constructs a DeleteCommand by parsing the task number from user input
    public DeleteCommand(String argument) throws FinchException {
        try {
            this.index = Integer.parseInt(argument.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FinchException("Please provide a valid task number to delete.");
        }
    }

    // Executes the command: deletes the specified task from the TaskList, shows confirmation, and saves to storage
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task removed = tasks.deleteTask(index);
        ui.showDeleted(removed, tasks.size());
        storage.save(tasks);
    }

    // Indicates whether this command exits the program
    @Override
    public boolean isExit() {
        return false;
    }
}