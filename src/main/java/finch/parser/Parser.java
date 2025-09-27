package finch.parser;

import finch.command.*;
import finch.exception.FinchException;

/**
 * Parses user input into commands that can be executed by the Finch application.
 */
public class Parser {

    // Parses a full command string entered by the user and returns the corresponding Command object.
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
            case "find" -> new FindCommand(arguments); // <-- NEW
            case "bye" -> new ExitCommand();
            default -> new UnknownCommand(commandWord);
        };
    }
}
