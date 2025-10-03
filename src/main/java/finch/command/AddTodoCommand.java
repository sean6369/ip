package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

/**
 * Represents the command to add a {@code ToDo} task in the Finch application.
 * <p>
 * The {@code AddTodoCommand} parses the user input, validates it, and when executed,
 * adds the corresponding ToDo task into the {@link TaskList}, displays confirmation
 * via the {@link Ui}, and saves the updated task list to {@link Storage}.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Constructs an {@code AddTodoCommand} with the given task description.
     *
     * @param arguments the raw description provided by the user
     * @throws FinchException if the description is {@code null} or empty
     */
    public AddTodoCommand(String arguments) throws FinchException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new FinchException("ToDo command must have a description: todo <desc>");
        }
        this.description = arguments.trim();
    }

    /**
     * Executes the command by adding a new {@code ToDo} task to the task list.
     * <ul>
     *   <li>Creates and appends a {@code ToDo} task with the given description</li>
     *   <li>Displays a confirmation message to the user</li>
     *   <li>Saves the updated task list to persistent storage</li>
     * </ul>
     *
     * @param tasks   the {@link TaskList} to which the task is added
     * @param ui      the {@link Ui} for showing feedback to the user
     * @param storage the {@link Storage} for saving the updated task list
     * @throws FinchException if saving fails or if the description is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = tasks.addTodo(description);
        ui.showAdded(t, tasks.size());
        storage.save(tasks);
    }
}