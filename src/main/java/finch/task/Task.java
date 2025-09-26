package finch.task;

import finch.exception.FinchException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    // Encode for saving to file
    public abstract String encode();

    // Decode from file
    public static Task decode(String line) throws FinchException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");

        Task task;
        switch (type) {
        case "T":
            task = new ToDo(parts[2]);
            break;
        case "D":
            task = new Deadline(parts[2], parts[3]);
            break;
        case "E":
            task = new Event(parts[2], parts[3], parts[4]);
            break;
        default:
            throw new FinchException("Corrupted task type in file: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public abstract String toSaveFormat();

}
