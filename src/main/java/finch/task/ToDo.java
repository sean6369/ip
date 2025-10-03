package finch.task;

/**
 * Represents a ToDo task in the Finch task manager.
 * <p>
 * A {@code ToDo} is a basic task with only a description and no associated time.
 * It can be marked as done/undone, displayed in user-friendly format, or encoded
 * for persistent storage.
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    // Returns a string representation of the Todo task for display
    @Override
    public String toString() {
        return "  [T]" + super.toString();
    }

    // Returns a string representation of the Todo task for saving to file
    @Override
    public String toSaveFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}