package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Represents the command to add a {@code Deadline} task in the Finch application.
 * <p>
 * The {@code AddDeadlineCommand} parses user input, validates the description and
 * deadline time, and when executed, adds the deadline task into the {@link TaskList},
 * displays confirmation via the {@link Ui}, and saves the updated task list to {@link Storage}.
 * </p>
 *
 * <p><b>Expected input format:</b></p>
 * <pre>
 *     deadline &lt;desc&gt; /by yyyy-MM-dd HH:mm
 * </pre>
 * Example:
 * <pre>
 *     deadline submit assignment /by 2025-09-26 18:00
 * </pre>
 */
public class AddDeadlineCommand extends Command {

    private final String description;
    private final LocalDateTime by;

    /**
     * Constructs an {@code AddDeadlineCommand} by parsing and validating the user input.
     *
     * @param arguments the raw argument string provided by the user
     * @throws FinchException if the input is empty, missing '/by', has invalid date format,
     *                        or if description/date fields are empty
     */
    public AddDeadlineCommand(String arguments) throws FinchException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new FinchException("Deadline command must be in this format: deadline <desc> /by <yyyy-MM-dd HH:mm>");
        }

        if (!arguments.contains("/by")) {
            throw new FinchException("Deadline command must contain '/by <date time>'");
        }

        // Split description and date string
        String[] parts = arguments.split("/by", 2);
        String descriptionPart = parts[0].trim();
        String byPart = parts[1].trim();

        if (descriptionPart.isEmpty()) {
            throw new FinchException("Deadline description cannot be empty.");
        }
        if (byPart.isEmpty()) {
            throw new FinchException("Deadline date/time cannot be empty.");
        }

        this.description = descriptionPart;

        // Parse into LocalDateTime
        try {
            // Convert "2025-09-26 18:00" → "2025-09-26T18:00" so LocalDateTime can parse
            this.by = LocalDateTime.parse(byPart.replace(" ", "T"));
        } catch (DateTimeParseException e) {
            throw new FinchException("Invalid date/time format. Use yyyy-MM-dd HH:mm (e.g., 2025-09-26 18:00).");
        }
    }

    /**
     * Executes the command by adding a new {@code Deadline} task to the task list.
     * <ul>
     *   <li>Creates and appends a {@code Deadline} task with the given description and deadline</li>
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
        Task t = tasks.addDeadline(description, by);
        ui.showAdded(t, tasks.size());
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