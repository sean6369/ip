package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks in the Finch application.
 * <p>
 * Provides functionality for adding, deleting, marking/unmarking tasks,
 * searching tasks by keyword, and retrieving tasks.
 * Supports {@link ToDo}, {@link Deadline}, and {@link Event} task types.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} with preloaded tasks.
     *
     * @param tasks a list of tasks to initialize with; if {@code null}, an empty list is created
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }

    // --- Add tasks ---

    /**
     * Adds a new {@link ToDo} task to the task list.
     *
     * @param description the description of the task
     * @return the newly created ToDo task
     * @throws FinchException if the description is {@code null} or empty
     */
    public Task addTodo(String description) throws FinchException {
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("ToDo description cannot be empty");
        }
        Task t = new ToDo(description.trim());
        tasks.add(t);
        return t;
    }

    /**
     * Adds a new {@link Deadline} task to the task list.
     *
     * @param description the description of the deadline
     * @param by the deadline date and time
     * @return the newly created Deadline task
     * @throws FinchException if the description is {@code null}/empty, or {@code by} is {@code null}
     */
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

    /**
     * Adds a new {@link Event} task to the task list.
     *
     * @param description the description of the event
     * @param from the start date and time of the event
     * @param to the end date and time of the event
     * @return the newly created Event task
     * @throws FinchException if description is {@code null}/empty,
     *                        if {@code from} or {@code to} are {@code null},
     *                        or if {@code to} is before {@code from}
     */
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

    /**
     * Deletes a task at the specified index.
     *
     * @param index the zero-based index of the task
     * @return the task that was removed
     * @throws FinchException if the index is invalid
     */
    public Task deleteTask(int index) throws FinchException {
        validateIndex(index);
        return tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index the zero-based index of the task
     * @throws FinchException if the index is invalid
     */
    public void markTask(int index) throws FinchException {
        validateIndex(index);
        tasks.get(index).markAsDone();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index the zero-based index of the task
     * @throws FinchException if the index is invalid
     */
    public void unmarkTask(int index) throws FinchException {
        validateIndex(index);
        tasks.get(index).unmark();
    }

    // --- Getters ---

    /**
     * Returns the number of tasks in this list.
     *
     * @return the number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index the zero-based index of the task
     * @return the task at the given index
     * @throws FinchException if the index is invalid
     */
    public Task getTask(int index) throws FinchException {
        validateIndex(index);
        return tasks.get(index);
    }

    // --- Find tasks by keyword ---

    /**
     * Finds all tasks containing the given keyword in their description.
     *
     * @param keyword the keyword to search for
     * @return a list of matching tasks
     * @throws FinchException if the keyword is {@code null} or empty
     */
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

    /**
     * Validates whether the given index is within bounds of the task list.
     *
     * @param index the index to validate
     * @throws FinchException if the index is out of range
     */
    private void validateIndex(int index) throws FinchException {
        if (index < 0 || index >= tasks.size()) {
            throw new FinchException("Task number " + (index + 1) + " does not exist!");
        }
    }
}
