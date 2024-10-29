package seedu.manager.parser;

import seedu.manager.command.Command;
import seedu.manager.command.FindCommand;
import seedu.manager.exception.InvalidCommandException;

import static java.util.logging.Level.WARNING;

//@author LTK-1606
public class FindCommandParser extends Parser {
    private static final String INVALID_FIND_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            find -e EVENT -p NAME
            """;
    private static final String INVALID_FIND_FLAG_MESSAGE = """
            Invalid find flag!
            Please set the find flag using "-e" and "-p""
            """;
    private static final String FIND_REGEX = "\\s*(-e|-p)\\s*";

    /**
     * Parses the input command to create a {@code FindCommand} object.
     * <p>
     * This method checks if the input contains the required flags (-e for event and -p for person).
     * It splits the input into parts based on these flags and validates the resulting segments.
     * If valid, it constructs and returns a new {@code FindCommand} with the specified event name
     * and participant name. If the command format is invalid or the required flags are missing,
     * an {@code InvalidCommandException} is thrown.
     * </p>
     *
     * @param input the full command input string to be parsed
     * @param commandParts the parts of the command, typically split by whitespace
     * @return a {@code FindCommand} object with the parsed event and person names
     * @throws InvalidCommandException if the command is missing required flags or has an invalid format
     */
    public Command parse(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(FindCommand.COMMAND_WORD);
        try {
            if (!input.contains("-e") || !input.contains("-p")) {
                throw new InvalidCommandException(INVALID_FIND_FLAG_MESSAGE);
            }

            String[] inputParts = input.split(FIND_REGEX);
            if (inputParts.length < 3 || inputParts[1].isBlank()) {
                throw new InvalidCommandException(INVALID_FIND_MESSAGE);
            }

            return new FindCommand(inputParts[1].trim(), inputParts[2].trim());
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_FIND_MESSAGE);
        }
    }
}
