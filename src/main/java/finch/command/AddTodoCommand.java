package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String arguments) throws FinchException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new FinchException("ToDo command must have a description: todo <desc>");
        }
        this.description = arguments.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = tasks.addTodo(description);
        ui.showAdded(t, tasks.size());
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
