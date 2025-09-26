package finch.parser;

import finch.exception.FinchException;

public class Parser {

    /**
     * Parses a full user input and extracts the command word.
     *
     * @param fullCommand the full line of user input
     * @return the first word (command) in lowercase
     * @throws FinchException if input is empty or null
     */
    public static String parseCommand(String fullCommand) throws FinchException {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            throw new FinchException("Please enter a command!");
        }

        String[] parts = fullCommand.trim().split("\\s+", 2);
        return parts[0].toLowerCase();
    }

    /**
     * Returns the arguments part of the input (everything after the command).
     *
     * @param fullCommand the full line of user input
     * @return the arguments string (maybe empty)
     * @throws FinchException if input is empty or null
     */
    public static String parseArguments(String fullCommand) throws FinchException {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            throw new FinchException("Please enter a command!");
        }

        String[] parts = fullCommand.trim().split("\\s+", 2);
        if (parts.length > 1) {
            return parts[1].trim();
        } else {
            return "";
        }
    }
}
