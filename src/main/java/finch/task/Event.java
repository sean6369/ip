package finch.task;

import finch.exception.FinchException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private final LocalDate from;
    private final LocalDate to;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    // Constructor 1: accept date strings
    public Event(String description, String fromString, String toString) throws FinchException {
        super(description);

        if (fromString == null || fromString.trim().isEmpty() ||
                toString == null || toString.trim().isEmpty()) {
            throw new FinchException("Event must have both start (/from) and end (/to) dates.");
        }

        try {
            this.from = LocalDate.parse(fromString.trim());
            this.to = LocalDate.parse(toString.trim());
        } catch (Exception e) {
            throw new FinchException("Invalid date format. Use yyyy-MM-dd (e.g., 2025-09-26).");
        }

        if (from.isAfter(to)) {
            throw new FinchException("Event start date must be on or before the end date.");
        }
    }

    // Constructor 2: accept LocalDate objects directly
    public Event(String description, LocalDate from, LocalDate to) throws FinchException {
        super(description);

        if (from == null || to == null) {
            throw new FinchException("Event must have both start and end dates.");
        }
        if (from.isAfter(to)) {
            throw new FinchException("Event start date must be on or before the end date.");
        }

        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DISPLAY_FORMATTER)
                + " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + from + " | " + to;
    }

    @Override
    public String encode() {
        return toSaveFormat();
    }

    public LocalDate getFromDate() {
        return from;
    }

    public LocalDate getToDate() {
        return to;
    }
}
