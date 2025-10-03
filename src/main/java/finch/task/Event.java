package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task in the Finch task manager.
 * <p>
 * An {@code Event} is a task that occurs within a specific time period,
 * defined by a start date/time and an end date/time. It can be marked
 * as done/undone, displayed in a human-readable format, or encoded
 * for persistent storage.
 */
public class Event extends Task {

    // Start date and time of the event
    private final LocalDateTime from;

    // End date and time of the event
    private final LocalDateTime to;

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    // Example: Sep 26 2025, 2:00pm

    // Constructor
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

    // Returns a string representation of the Event task for display
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DISPLAY_FORMATTER).replace("AM", "am").replace("PM", "pm")
                + ", to: " + to.format(DISPLAY_FORMATTER).replace("AM", "am").replace("PM", "pm") + ")";
    }

    // Returns a string representation of the Event task for saving to file
    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + from + " | " + to;
    }

    // Encodes the Event task for storage
    @Override
    public String encode() {
        return toSaveFormat();
    }
}
