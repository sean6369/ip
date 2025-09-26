package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;

    public AddDeadlineCommand(String arguments) throws FinchException {
        // Check if user typed anything
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new FinchException("Deadline command must be in this format: deadline <desc> /by <date>");
        }

        // Check for "/by"
        if (!arguments.contains("/by")) {
            throw new FinchException("Deadline command must contain '/by <date>'");
        }

        // Split description and date/time
        String[] parts = arguments.split("/by", 2);

        this.description = parts[0].trim();
        this.by = parts[1].trim();

        // Handle empty description or date/time
        if (description.isEmpty()) {
            throw new FinchException("Deadline description cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new FinchException("Deadline date/time cannot be empty.");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = tasks.addDeadline(description, by);
        ui.showAdded(t, tasks.size());
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
