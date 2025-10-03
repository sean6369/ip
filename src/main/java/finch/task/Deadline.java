package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task in the Finch task manager.
 * <p>
 * A {@code Deadline} is a task with a description and a specific due date/time.
 * It can be marked as done/undone, displayed in user-friendly format, or encoded
 * for persistent storage.
 */
public class Deadline extends Task {

    private final LocalDateTime by; // store date + time

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    // Example: Sep 26 2025, 6:30 PM

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
        // Format to e.g. "Sep 26 2025, 6:30pm"
        return "[D]" + super.toString() + " (by: "
                + by.format(DISPLAY_FORMATTER)
                .replace("AM", "am")
                .replace("PM", "pm")
                + ")";
    }

    // Returns a string representation of the Deadline task for saving to file
    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    // Encodes the Deadline for storage
    @Override
    public String encode() {
        return toSaveFormat();
    }
}
