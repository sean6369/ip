package finch.task;

import finch.exception.FinchException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    private final LocalDate by; // store date as LocalDate
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    // Constructor: accepts date as String
    public Deadline(String description, String byString) throws FinchException {
        super(description);
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("Deadline description cannot be empty.");
        }
        if (byString == null || byString.trim().isEmpty()) {
            throw new FinchException("Deadline date cannot be empty.");
        }

        try {
            this.by = LocalDate.parse(byString.trim()); // expects yyyy-MM-dd
        } catch (DateTimeParseException e) {
            throw new FinchException("Invalid date format. Use yyyy-MM-dd (e.g., 2025-09-26).");
        }
    }

    // Overloaded constructor: accepts date as LocalDate
    public Deadline(String description, LocalDate by) throws FinchException {
        super(description);
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("Deadline description cannot be empty.");
        }
        if (by == null) {
            throw new FinchException("Deadline date cannot be null.");
        }
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    @Override
    public String encode() {
        return toSaveFormat(); // keep same as save format
    }

    // Optional: expose LocalDate for date-based filtering
    public LocalDate getByDate() {
        return by;
    }
}
