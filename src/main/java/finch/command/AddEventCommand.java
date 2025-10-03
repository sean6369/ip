package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

import java.time.LocalDateTime;

/**
 * Represents the command to add an {@code Event} task in the Finch application.
 * <p>
 * The {@code AddEventCommand} parses user input, validates the description and
 * start/end times, and when executed, adds the event task into the {@link TaskList},
 * displays confirmation via the {@link Ui}, and saves the updated task list to {@link Storage}.
 * </p>
 *
 * <p><b>Expected input format:</b></p>
 * <pre>
 *     event &lt;desc&gt; /from yyyy-MM-dd HH:mm /to yyyy-MM-dd HH:mm
 * </pre>
 * Example:
 * <pre>
 *     event project meeting /from 2025-09-26 14:00 /to 2025-09-26 16:00
 * </pre>
 */
public class AddEventCommand extends Command {

    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs an {@code AddEventCommand} by parsing and validating the user input.
     *
     * @param arguments the raw argument string provided by the user
     * @throws FinchException if the input is empty, missing '/from' or '/to', has invalid date format,
     *                        or if description/date fields are empty
     */
    public AddEventCommand(String arguments) throws FinchException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new FinchException("Event command cannot be empty. Format: event <desc> /from <start> /to <end>");
        }

        // Ensure both /from and /to exist
        if (!arguments.contains("/from") || !arguments.contains("/to")) {
            throw new FinchException("Event command must contain both '/from <start>' and '/to <end>'");
        }

        // Split /from and /to
        int indexFrom = arguments.indexOf("/from");
        int indexTo = arguments.indexOf("/to");
        if (indexFrom > indexTo) {
            throw new FinchException("'/from' must come before '/to'");
        }

        // Split description and dates
        String[] parts = arguments.split("/from", 2);

        String descriptionPart = parts[0].trim();
        String remaining = parts[1].trim();

        String[] fromToParts = remaining.split("/to", 2);

        String fromPart = fromToParts[0].trim();
        String toPart = fromToParts[1].trim();

        if (descriptionPart.isEmpty()) {
            throw new FinchException("Event description cannot be empty");
        }
        if (fromPart.isEmpty()) {
            throw new FinchException("Event start date/time (/from) cannot be empty");
        }
        if (toPart.isEmpty()) {
            throw new FinchException("Event end date/time (/to) cannot be empty");
        }

        this.description = descriptionPart;

        // Parse date + time
        try {
            this.from = LocalDateTime.parse(fromPart.replace(" ", "T"));
            this.to = LocalDateTime.parse(toPart.replace(" ", "T"));
        } catch (Exception e) {
            throw new FinchException("Invalid date/time format. Use yyyy-MM-dd HH:mm (e.g., 2025-09-26 14:00)");
        }
    }

    /**
     * Executes the command by adding a new {@code Event} task to the task list.
     * <ul>
     *   <li>Creates and appends an {@code Event} task with the given description and time range</li>
     *   <li>Displays a confirmation message to the user</li>
     *   <li>Saves the updated task list to persistent storage</li>
     * </ul>
     *
     * @param tasks   the {@link TaskList} to which the task is added
     * @param ui      the {@link Ui} for showing feedback to the user
     * @param storage the {@link Storage} for saving the updated task list
     * @throws FinchException if saving fails or if task creation is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = tasks.addEvent(description, from, to);
        ui.showAdded(t, tasks.size());
        storage.save(tasks);
    }
}