package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class MarkCommand extends Command {
    private final int index;

    // Constructs a MarkCommand for the specified task index
    public MarkCommand(String argument) throws FinchException {
        try {
            this.index = Integer.parseInt(argument.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FinchException("Please provide a valid task number to mark.");
        }
    }

    // Executes the mark command: marks the task at the given index as done, and shows a confirmation message to the user
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = tasks.getTask(index);
        if (t == null) throw new FinchException("Task number " + (index + 1) + " does not exist!");
        tasks.markTask(index);
        ui.showMarked(t);
        storage.save(tasks);
    }

    // Indicates whether this command exits the program
    @Override
    public boolean isExit() {
        return false;
    }
}
