package finch.task;

import finch.exception.FinchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public abstract class Task {

    // The task description provided by the user
    protected String description;

    // Boolean flag indicating whether the task is done
    protected boolean isDone;

    // Constructs a new task with the given description and the task is initially not done
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    // Returns the status icon for the task, "X" for done, " " (space) for not done
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

    // Encodes the task into a string format suitable for saving to file
    public abstract String encode();

    // Decodes a line from a saved file and returns the corresponding Task object
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

    // Returns a string representation of the task, including the status icon and description
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    // Converts the task into a format suitable for saving in a file
    public abstract String toSaveFormat();
}
