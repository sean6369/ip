package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;

/**
 * Represents a Deadline task in the Finch task manager.
 * <p>
 * A {@code Deadline} is a task with a description and a specific due date/time.
 * It can be marked as done/undone, displayed in user-friendly format, or encoded
 * for persistent storage.
 */
public class Deadline extends Task {

    private final LocalDateTime by; // store date + time

    // Constructor
    public Deadline(String description, LocalDateTime by) throws FinchException {
        super(description);
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("Deadline description cannot be empty.");
        }
        if (by == null) {
            throw new FinchException("Deadline date/time cannot be null.");
        }
        this.by = by;
    }

    // Returns a string representation of the Deadline task for display
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatDateTime(by) + ")";
    }

    // Returns a string representation of the Deadline task for saving to file
    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
