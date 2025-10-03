package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

/**
 * Represents the command to delete a task from the {@link TaskList}.
 * <p>
 * The {@code DeleteCommand} parses a user-provided task number, validates it,
 * and when executed, removes the corresponding task from the task list,
 * shows confirmation to the user, and updates the persistent storage.
 * </p>
 *
 * <p><b>Expected input format:</b></p>
 * <pre>
 *     delete &lt;taskNumber&gt;
 * </pre>
 * Example:
 * <pre>
 *     delete 2
 * </pre>
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a {@code DeleteCommand} by parsing the task number provided by the user.
     *
     * @param argument the raw argument string (expected to be a valid integer task number)
     * @throws FinchException if the input is not a valid integer
     */
    public DeleteCommand(String argument) throws FinchException {
        try {
            this.index = Integer.parseInt(argument.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FinchException("Please provide a valid task number to delete.");
        }
    }

    /**
     * Executes the command by deleting the specified task from the task list.
     * <ul>
     *   <li>Removes the task at the given index from {@link TaskList}</li>
     *   <li>Displays a confirmation message via {@link Ui}</li>
     *   <li>Saves the updated task list to {@link Storage}</li>
     * </ul>
     *
     * @param tasks   the {@link TaskList} from which the task is deleted
     * @param ui      the {@link Ui} for showing feedback to the user
     * @param storage the {@link Storage} for saving the updated task list
     * @throws FinchException if the index is invalid or saving fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task removed = tasks.deleteTask(index);
        ui.showDeleted(removed, tasks.size());
        storage.save(tasks);
    }

    /**
     * Indicates whether this command exits the program.
     *
     * @return {@code false} as this command does not terminate the program
     */
    @Override
    public boolean isExit() {
        return false;
    }
}