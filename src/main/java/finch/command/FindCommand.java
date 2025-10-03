package finch.command;

import finch.storage.Storage;
import finch.task.Task;
import finch.task.TaskList;
import finch.ui.Ui;
import finch.exception.FinchException;
import java.util.List;

public class FindCommand extends Command {

    private final String keyword;

    // Constructs a FindCommand with the specified search keyword
    public FindCommand(String keyword) throws FinchException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new FinchException("Keyword cannot be empty.");
        }
        this.keyword = keyword.trim();
    }

    // Executes the find command: searches for tasks containing the keyword, then prints the matching tasks to the user
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FinchException {
        List<Task> results = tasks.findTasks(keyword);

        if (results.isEmpty()) {
            System.out.println("    No tasks match your search: " + keyword);
        } else {
            System.out.println("    Here are the matching tasks in your list:");
            for (int i = 0; i < results.size(); i++) {
                System.out.printf("    %d.%s%n", i + 1, results.get(i));
            }
        }
    }
}