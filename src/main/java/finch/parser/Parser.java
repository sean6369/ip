package finch.parser;

import finch.command.*;
import finch.exception.FinchException;

/**
 * Parses user input into executable commands for the Finch application.
 * <p>
 * The Parser interprets a full command string entered by the user and
 * returns the corresponding {@link finch.command.Command} object.
 * It handles validation of input format and maps recognized command words
 * to their respective Command classes.
 */
public class Parser {

    /**
     * Parses the full command entered by the user and returns a {@link Command} object.
     *
     * <p>Recognized commands include:
     * <ul>
     *     <li>{@code todo} - Add a ToDo task</li>
     *     <li>{@code deadline} - Add a Deadline task</li>
     *     <li>{@code event} - Add an Event task</li>
     *     <li>{@code list} - List all tasks</li>
     *     <li>{@code mark} - Mark a task as done</li>
     *     <li>{@code unmark} - Mark a task as not done</li>
     *     <li>{@code delete} - Delete a task</li>
     *     <li>{@code find} - Search tasks by keyword</li>
     *     <li>{@code bye} - Exit the application</li>
     *     <li>Any unrecognized command will return an {@link UnknownCommand}</li>
     * </ul>
     *
     * @param fullCommand The full string entered by the user, including the command word and any arguments.
     * @return A {@link Command} object corresponding to the user input.
     * @throws FinchException If the input format is invalid for commands requiring arguments.
     */
    public static Command parse(String fullCommand) throws FinchException {
        if (fullCommand == null || fullCommand.isBlank()) {
            return new UnknownCommand(""); // or EmptyCommand
        }

        // Split into command word and optional arguments
        String[] parts = fullCommand.trim().split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        // Match command word to corresponding Command class
        return switch (commandWord) {
            case "todo" -> new AddTodoCommand(arguments);
            case "deadline" -> new AddDeadlineCommand(arguments);
            case "event" -> new AddEventCommand(arguments);
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(arguments);
            case "unmark" -> new UnmarkCommand(arguments);
            case "delete" -> new DeleteCommand(arguments);
            case "find" -> new FindCommand(arguments);
            case "bye" -> new ExitCommand();
            default -> new UnknownCommand(commandWord);
        };
    }
}
