package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    private final LocalDateTime by; // store date + time
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    // Example: Sep 26 2025, 6:30 PM

    // Constructor: accepts date as String (yyyy-MM-dd HH:mm)
    public Deadline(String description, String byString) throws FinchException {
        super(description);
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("Deadline description cannot be empty.");
        }
        if (byString == null || byString.trim().isEmpty()) {
            throw new FinchException("Deadline date/time cannot be empty.");
        }

        try {
            // Replace space with "T" for LocalDateTime.parse
            this.by = LocalDateTime.parse(byString.trim().replace(" ", "T"));
        } catch (DateTimeParseException e) {
            throw new FinchException("Invalid date/time format. Use yyyy-MM-dd HH:mm (e.g., 2025-09-26 18:30).");
        }
    }

    // Overloaded constructor: accepts LocalDateTime directly
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

    public LocalDateTime getByDateTime() {
        return by;
    }
}
