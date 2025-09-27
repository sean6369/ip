package finch.command;

import finch.task.Deadline;
import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AddDeadlineCommand extends Command {

    private final String description;
    private final LocalDateTime by;

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

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = new Deadline(description, by); // now uses LocalDateTime constructor
        tasks.addDeadline(description, by.toString()); // keep TaskList compatibility (saves ISO string)
        ui.showAdded(t, tasks.size());
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}