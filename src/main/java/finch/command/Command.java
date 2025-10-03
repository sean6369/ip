package finch.command;

import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public abstract class Command {
    // Execute the command
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException;

    public boolean isExit() {
        return false;
    }
}