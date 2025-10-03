package finch.command;

import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;

/**
 * Represents the command to display all tasks in the {@link TaskList}.
 * <p>
 * The {@code ListCommand} does not modify the task list or storage.
 * It simply shows the user a formatted list of tasks via the {@link Ui}.
 * </p>
 *
 * <p><b>Expected input format:</b></p>
 * <pre>
 *     list
 * </pre>
 * Example:
 * <pre>
 *     list
 * </pre>
 */
public class ListCommand extends Command {

    /**
     * Executes the command by showing all tasks currently stored in the {@link TaskList}.
     * <ul>
     *   <li>Delegates the display of tasks to the {@link Ui}</li>
     *   <li>Does not alter the {@link TaskList} or {@link Storage}</li>
     * </ul>
     *
     * @param tasks   the {@link TaskList} containing all tasks
     * @param ui      the {@link Ui} for displaying the task list to the user
     * @param storage the {@link Storage} (unused in this command, but included for consistency)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }
}