package finch.command;

import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class UnknownCommand extends Command {
    private final String commandWord;

    public UnknownCommand(String commandWord) {
        this.commandWord = commandWord;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        ui.showError("Unknown command: " + commandWord);
        ui.showLine();
        ui.showCommands();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
