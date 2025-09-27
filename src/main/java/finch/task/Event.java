package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {

    private final LocalDateTime from;
    private final LocalDateTime to;
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    // Example: Sep 26 2025, 2:00pm

    // Constructor 1: accept date strings ("yyyy-MM-dd HH:mm")
    public Event(String description, String fromString, String toString) throws FinchException {
        super(description);

        if (fromString == null || fromString.trim().isEmpty() ||
                toString == null || toString.trim().isEmpty()) {
            throw new FinchException("Event must have both start (/from) and end (/to) date/times.");
        }

        try {
            this.from = LocalDateTime.parse(fromString.trim().replace(" ", "T"));
            this.to = LocalDateTime.parse(toString.trim().replace(" ", "T"));
        } catch (DateTimeParseException e) {
            throw new FinchException("Invalid date/time format. Use yyyy-MM-dd HH:mm (e.g., 2025-09-26 14:00).");
        }

        if (from.isAfter(to)) {
            throw new FinchException("Event start date/time must be on or before the end date/time.");
        }
    }

    // Constructor 2: accept LocalDateTime objects directly
    public Event(String description, LocalDateTime from, LocalDateTime to) throws FinchException {
        super(description);

        if (from == null || to == null) {
            throw new FinchException("Event must have both start and end date/times.");
        }
        if (from.isAfter(to)) {
            throw new FinchException("Event start date/time must be on or before the end date/time.");
        }

        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DISPLAY_FORMATTER).replace("AM", "am").replace("PM", "pm")
                + ", to: " + to.format(DISPLAY_FORMATTER).replace("AM", "am").replace("PM", "pm") + ")";
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

    public LocalDateTime getFromDateTime() {
        return from;
    }

    public LocalDateTime getToDateTime() {
        return to;
    }
}
