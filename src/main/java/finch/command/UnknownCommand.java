package finch.command;

import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class UnknownCommand extends Command {
    private final String commandWord;

    // Constructs an UnknownCommand for an unrecognised command word
    public UnknownCommand(String commandWord) {
        this.commandWord = commandWord;
    }

    // Executes the unknown command by displaying an error message and showing the list of available commands to the user
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        ui.showError("Unknown command: " + commandWord);
        ui.showLine();
        ui.showCommands();
    }

    // Indicates whether this command exits the program
    @Override
    public boolean isExit() {
        return false;
    }
}
