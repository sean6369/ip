package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> tasks;

    // Constructor: empty task list
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    // Constructor: preloaded task list
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }

    // --- Add tasks ---

    public Task addTodo(String description) throws FinchException {
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("ToDo description cannot be empty");
        }
        Task t = new ToDo(description.trim());
        tasks.add(t);
        return t;
    }

    public Task addDeadline(String description, LocalDateTime by) throws FinchException {
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("Deadline description cannot be empty");
        }
        if (by == null) {
            throw new FinchException("Deadline date/time cannot be null");
        }
        Task t = new Deadline(description.trim(), by);
        tasks.add(t);
        return t;
    }

    public Task addEvent(String description, LocalDateTime from, LocalDateTime to) throws FinchException {
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("Event description cannot be empty");
        }
        if (from == null || to == null) {
            throw new FinchException("Event must have both start (/from) and end (/to) times");
        }
        if (to.isBefore(from)) {
            throw new FinchException("Event end time cannot be before start time");
        }
        Task t = new Event(description.trim(), from, to);
        tasks.add(t);
        return t;
    }

    // --- Task operations ---

    public Task deleteTask(int index) throws FinchException {
        validateIndex(index);
        return tasks.remove(index);
    }

    public void markTask(int index) throws FinchException {
        validateIndex(index);
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) throws FinchException {
        validateIndex(index);
        tasks.get(index).unmark();
    }

    // --- Getters ---

    public int size() {
        return tasks.size();
    }

    public Task getTask(int index) throws FinchException {
        validateIndex(index);
        return tasks.get(index);
    }

    // --- Find tasks by keyword ---
    public List<Task> findTasks(String keyword) throws FinchException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new FinchException("Keyword cannot be empty");
        }

        List<Task> results = new ArrayList<>();
        for (Task t : tasks) {
            if (t.description.toLowerCase().contains(keyword.toLowerCase())) {
                results.add(t);
            }
        }
        return results;
    }

    // --- Helper ---

    private void validateIndex(int index) throws FinchException {
        if (index < 0 || index >= tasks.size()) {
            throw new FinchException("Task number " + (index + 1) + " does not exist!");
        }
    }
}
