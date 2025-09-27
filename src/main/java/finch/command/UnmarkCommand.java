package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class UnmarkCommand extends Command {
    private final int index;

    // Constructs a UnmarkCommand for the specified task index
    public UnmarkCommand(String argument) throws FinchException {
        try {
            this.index = Integer.parseInt(argument.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FinchException("Please provide a valid task number to unmark.");
        }
    }

    // Executes the unmark command: unmark the task at the given index as not done, and shows a confirmation message to the user
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = tasks.getTask(index);
        if (t == null) throw new FinchException("Task number " + (index + 1) + " does not exist!");
        tasks.unmarkTask(index);
        ui.showUnmarked(t);
        storage.save(tasks);
    }

    // Indicates whether this command exits the program
    @Override
    public boolean isExit() {
        return false;
    }
}
