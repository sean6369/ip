package finch.command;

import finch.task.Event;
import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AddEventCommand extends Command {

    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public AddEventCommand(String arguments) throws FinchException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new FinchException("Event command must be in this format: event <desc> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>");
        }

        // Ensure both /from and /to exist
        if (!arguments.contains("/from") || !arguments.contains("/to")) {
            throw new FinchException("Event command must contain '/from <start>' and '/to <end>'");
        }

        // Ensure /from comes before /to
        if (arguments.indexOf("/from") > arguments.indexOf("/to")) {
            throw new FinchException("'/from' must come before '/to'");
        }

        // Split description and dates
        String[] parts = arguments.split("/from", 2);
        String descriptionPart = parts[0].trim();
        String remaining = parts[1].trim();

        String[] fromToParts = remaining.split("/to", 2);
        if (fromToParts.length < 2) {
            throw new FinchException("Event command must contain both '/from <start>' and '/to <end>'");
        }

        String fromPart = fromToParts[0].trim();
        String toPart = fromToParts[1].trim();

        // Validate non-empty fields
        if (descriptionPart.isEmpty()) {
            throw new FinchException("Event description cannot be empty.");
        }
        if (fromPart.isEmpty()) {
            throw new FinchException("Event start date/time (/from) cannot be empty.");
        }
        if (toPart.isEmpty()) {
            throw new FinchException("Event end date/time (/to) cannot be empty.");
        }

        this.description = descriptionPart;

        // Parse date + time into LocalDateTime
        try {
            // Convert "2025-09-26 14:00" → "2025-09-26T14:00" for LocalDateTime.parse
            this.from = LocalDateTime.parse(fromPart.replace(" ", "T"));
            this.to = LocalDateTime.parse(toPart.replace(" ", "T"));
        } catch (DateTimeParseException e) {
            throw new FinchException("Invalid date/time format. Use yyyy-MM-dd HH:mm (e.g., 2025-09-26 14:00).");
        }

        // Ensure start <= end
        if (to.isBefore(from)) {
            throw new FinchException("Event end date/time cannot be before start date/time.");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = new Event(description, from, to);
        tasks.addEvent(description, from.toString(), to.toString()); // saves ISO format for compatibility
        ui.showAdded(t, tasks.size());
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
