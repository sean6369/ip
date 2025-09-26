package finch.command;

import finch.task.Task;
import finch.task.TaskList;
import finch.task.Event;
import finch.ui.Ui;
import finch.storage.Storage;
import finch.exception.FinchException;

public class AddEventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    public AddEventCommand(String arguments) throws FinchException {
        // Check if user typed anything
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new FinchException("Event command must be in this format: event <desc> /from <start> /to <end>");
        }

        // Check that both /from and /to exist
        if (!arguments.contains("/from") || !arguments.contains("/to")) {
            throw new FinchException("Event command must contain both '/from <start>' and '/to <end>'");
        }

        // Check that /from comes before /to
        if (arguments.indexOf("/from") > arguments.indexOf("/to")) {
            throw new FinchException("'/from' must come before '/to'");
        }

        // Split the description, from, and to
        String[] parts = arguments.split("/from", 2);
        this.description = parts[0].trim();

        String[] fromToParts = parts[1].split("/to", 2);
        this.from = fromToParts[0].trim();
        this.to = fromToParts[1].trim();

        // Validate non-empty fields
        if (description.isEmpty()) {
            throw new FinchException("Event description cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new FinchException("Event start time (/from) cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new FinchException("Event end time (/to) cannot be empty.");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        Task t = tasks.addEvent(description, from, to);
        ui.showAdded(t, tasks.size());
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
