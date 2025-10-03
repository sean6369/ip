package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Represents an abstract task in the Finch application.
 * <p>
 * A task has a description and a completion status (done or not done).
 * Subclasses ({@link ToDo}, {@link Deadline}, {@link Event}) define
 * specific types of tasks with additional attributes (e.g., deadlines or event times).
 */
public abstract class Task {

    // The task description provided by the user
    protected String description;

    // Boolean flag indicating whether the task is done
    protected boolean isDone;

    /**
     * Constructs a new {@code Task} with the given description.
     * The task is initially marked as not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is done, or a single space (" ") if it is not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    // Marks the task as done
    public void markAsDone() {
        isDone = true;
    }

    // Marks the task as not done
    public void unmark() {
        isDone = false;
    }

    /**
     * Encodes this task into a string representation suitable for saving to file.
     * <p>
     * Implemented by subclasses to provide their own save format.
     *
     * @return a string representation of the task for storage
     */
    public abstract String encode();

    /**
     * Decodes a task from a saved file line into the corresponding {@link Task} object.
     * <p>
     * Supports the following formats:
     * <ul>
     *     <li>ToDo: {@code T | isDone | description}</li>
     *     <li>Deadline: {@code D | isDone | description | yyyy-MM-ddTHH:mm}</li>
     *     <li>Event: {@code E | isDone | description | startDateTime | endDateTime}</li>
     * </ul>
     *
     * @param line the encoded task string read from storage
     * @return the corresponding {@link Task} object
     * @throws FinchException if the task type is unknown or the date format is invalid
     */
    public static Task decode(String line) throws FinchException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");

        Task task;
        try {
            switch (type) {
            case "T":
                task = new ToDo(parts[2]);
                break;
            case "D":
                // parts[3] is ISO string like "2025-09-26T18:00"
                LocalDateTime deadlineDateTime = LocalDateTime.parse(parts[3]);
                task = new Deadline(parts[2], deadlineDateTime);
                break;
            case "E":
                // parts[3] and parts[4] are ISO strings like "2025-09-26T14:00"
                LocalDateTime fromDateTime = LocalDateTime.parse(parts[3]);
                LocalDateTime toDateTime = LocalDateTime.parse(parts[4]);
                task = new Event(parts[2], fromDateTime, toDateTime);
                break;
            default:
                throw new FinchException("Corrupted task type in file: " + type);
            }
        } catch (DateTimeParseException e) {
            throw new FinchException("Invalid date format found in saved file: " + e.getMessage());
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Returns a string representation of this task for display purposes.
     *
     * @return a string containing the status icon and task description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts this task into a saveable format for persistent storage.
     * <p>
     * Implemented by subclasses to define their own file format.
     *
     * @return a string representation of the task suitable for storage
     */
    public abstract String toSaveFormat();
}
