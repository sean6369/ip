package finch.task;

import finch.exception.FinchException;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    // Construct with an empty list
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    // Construct with a preloaded list (from Storage)
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Helper ToDo method with proper error handling
    private ToDo createTodo(String description) throws FinchException {
        if (description == null || description.trim().isEmpty()) {
            throw new FinchException("The description of a ToDo cannot be empty");
        }
        return new ToDo(description.trim());
    }

    //  Add a Todo
    public void addTodo(String description) throws FinchException {
        Task t = createTodo(description);
        tasks.add(t);
    }

    // Helper Deadline method with proper error handling
    private Deadline createDeadline(String descriptionAndBy) throws FinchException {
        // Check for required keywords
        if (!descriptionAndBy.contains(" /by")) {
            throw new FinchException("Deadline command must include '/by'");
        }

        String[] parts = descriptionAndBy.split(" /by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) throw new FinchException("Deadline description cannot be empty");
        if (by.isEmpty()) throw new FinchException("Deadline date/time cannot be empty");

        return new Deadline(description, by);
    }

    // Add a Deadline
    public void addDeadline(String descriptionAndBy) throws FinchException {
        Task t = createDeadline(descriptionAndBy);
        tasks.add(t);
    }

    // Helper Event method with proper error handling
    private Event createEvent(String descriptionAndFromTo) throws FinchException {
        // Check for required keywords
        if (!descriptionAndFromTo.contains(" /from")) {
            throw new FinchException("Event command must include '/from'");
        }
        if (!descriptionAndFromTo.contains(" /to")) {
            throw new FinchException("Event command must include '/to'");
        }

        // Make sure "/from" comes before "/to"
        int fromIndex = descriptionAndFromTo.indexOf(" /from");
        int toIndex = descriptionAndFromTo.indexOf(" /to");
        if (fromIndex > toIndex) {
            throw new FinchException("'/from' must come before '/to'");
        }

        // Split input safely
        String[] parts = descriptionAndFromTo.split(" /from| /to", 3); // Limit 3 parts
        if (parts.length < 3) {
            throw new FinchException("Event must have description, /from and /to");
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty()) throw new FinchException("Event description cannot be empty");
        if (from.isEmpty()) throw new FinchException("Event start date/time (/from) cannot be empty");
        if (to.isEmpty()) throw new FinchException("Event end date/time (/to) cannot be empty");

        return new Event(description, from, to);
    }

    // Add an Event
    public void addEvent(String descriptionAndFromTo) throws FinchException {
        Task t = createEvent(descriptionAndFromTo);
        tasks.add(t);
    }

    // Delete a task by index
    public Task deleteTask(int index) throws FinchException {
        if (index < 0 || index >= this.tasks.size()) {
            throw new FinchException("Task number " + (index + 1) + " does not exist!");
        }
        return this.tasks.remove(index);
    }

    // Mark as done
    public void markTask(int index) throws FinchException {
        if (index < 0 || index >= this.tasks.size()) {
            throw new FinchException("Task number " + (index + 1) + " does not exist!");
        }
        tasks.get(index).markAsDone();
    }

    // Unmark as not done
    public void unmarkTask(int index) throws FinchException {
        if (index < 0 || index >= this.tasks.size()) {
            throw new FinchException("Task number " + (index + 1) + " does not exist!");
        }
        tasks.get(index).unmark();
    }

    // Get number of tasks
    public int size() {
        return tasks.size();
    }

    // Get a task by index
    public Task getTask(int index) {
        if (index < 0 || index >= this.tasks.size()) {
            return null;
        }
        return tasks.get(index);
    }

    // Get last added task
    public Task getLastTask() {
        return tasks.get(tasks.size() - 1);
    }

}
