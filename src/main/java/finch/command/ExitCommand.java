package finch.command;

import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        ui.showGoodbye();
    }

    // Indicates whether this command exits the program
    @Override
    public boolean isExit() {
        return true; // Signals the app to exit
    }
}
