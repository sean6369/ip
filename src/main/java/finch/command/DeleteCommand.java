package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(String argument) throws FinchException {
        try {
            this.index = Integer.parseInt(argument.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FinchException("Please provide a valid task number to delete.");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task removed = tasks.deleteTask(index);
        ui.showDeleted(removed, tasks.size());
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}