package finch.task;

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

    // Encodes the Todo Task for storage
    @Override
    public String encode() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
