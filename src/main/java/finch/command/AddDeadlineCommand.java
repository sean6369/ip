package finch.command;

import finch.task.Deadline;
import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddDeadlineCommand extends Command {

    private final String description;
    private final LocalDate by;

    public AddDeadlineCommand(String arguments) throws FinchException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new FinchException("Deadline command must be in this format: deadline <desc> /by <date>");
        }

        if (!arguments.contains("/by")) {
            throw new FinchException("Deadline command must contain '/by <date>'");
        }

        // Split description and date string
        String[] parts = arguments.split("/by", 2);
        String descriptionPart = parts[0].trim();
        String byPart = parts[1].trim();

        if (descriptionPart.isEmpty()) {
            throw new FinchException("Deadline description cannot be empty.");
        }
        if (byPart.isEmpty()) {
            throw new FinchException("Deadline date cannot be empty.");
        }

        this.description = descriptionPart;

        // Parse date into LocalDate
        try {
            this.by = LocalDate.parse(byPart); // expects yyyy-MM-dd format
        } catch (DateTimeParseException e) {
            throw new FinchException("Invalid date format. Use yyyy-MM-dd (e.g., 2025-09-26).");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = new Deadline(description, by); // Deadline constructor updated to take LocalDate
        tasks.addDeadline(description, by.toString()); // save raw string if TaskList still stores strings
        ui.showAdded(t, tasks.size());
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}