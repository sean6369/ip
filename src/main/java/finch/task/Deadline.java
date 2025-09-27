package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Override
    public String toString() {
        // Format to e.g. "Sep 26 2025, 6:30pm"
        return "[D]" + super.toString() + " (by: "
                + by.format(DISPLAY_FORMATTER)
                .replace("AM", "am")
                .replace("PM", "pm")
                + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    @Override
    public String encode() {
        return toSaveFormat();
    }
}
