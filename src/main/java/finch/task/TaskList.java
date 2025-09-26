package finch.task;

import finch.exception.FinchException;

import java.util.ArrayList;
import java.util.Collections;
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

    public Task addDeadline(String description, String by) throws FinchException {
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("Deadline description cannot be empty");
        }
        if (by == null || by.trim().isEmpty()) {
            throw new FinchException("Deadline date/time cannot be empty");
        }
        Task t = new Deadline(description.trim(), by.trim());
        tasks.add(t);
        return t;
    }

    public Task addEvent(String description, String from, String to) throws FinchException {
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("Event description cannot be empty");
        }
        if (from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
            throw new FinchException("Event must have both start (/from) and end (/to) times");
        }
        Task t = new Event(description.trim(), from.trim(), to.trim());
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

    public Task getLastTask() throws FinchException {
        if (tasks.isEmpty()) {
            throw new FinchException("No tasks in the list.");
        }
        return tasks.get(tasks.size() - 1);
    }

    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks); // prevent external modification
    }

    // --- Helper ---

    private void validateIndex(int index) throws FinchException {
        if (index < 0 || index >= tasks.size()) {
            throw new FinchException("Task number " + (index + 1) + " does not exist!");
        }
    }
}
