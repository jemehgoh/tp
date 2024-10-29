package seedu.manager.parser;

import seedu.manager.command.Command;
import seedu.manager.command.FilterCommand;
import seedu.manager.exception.InvalidCommandException;

import java.util.Set;

import static java.util.logging.Level.WARNING;

//@@author LTK-1606
public class FilterCommandParser extends Parser {
    private static final String INVALID_FILTER_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            filter -e/-t/-u FILTER_DESCRIPTION
            """;
    private static final String INVALID_FILTER_FLAG_MESSAGE = """
            Invalid filter flag!
            Please set the filter flag as either "-e/-t/-u"
            """;

    /**
     * Parses the input string and command parts to create a {@code FilterCommand} object.
     * <p>
     * This method verifies that the first part of {@code commandParts} matches the expected filter command
     * and then checks if a valid flag is provided. The filter flag should be one of <code>"-e"</code>,
     * <code>"-t"</code>, or <code>"-u"</code>, representing different filter types.
     * If the flag is valid and additional input is provided, a new {@code FilterCommand} is created.
     * <p>
     * If the input format is incorrect, or an invalid flag is provided,
     * this method throws an {@code InvalidCommandException}.
     *
     * @param input        the full user input string
     * @param commandParts the split parts of the command, with the first element expected to be the filter command word
     * @return a {@code FilterCommand} object initialized with the specified flag and filter criteria
     * @throws InvalidCommandException if the command format is invalid or an invalid flag is provided
     */
    public Command parse(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(FilterCommand.COMMAND_WORD);
        try {
            String[] inputParts = input.split("(-e|-t|-u)");
            if (inputParts.length < 2) {
                throw new InvalidCommandException(INVALID_FILTER_MESSAGE);
            }

            Set<String> validFlags = Set.of("-e", "-t", "-u");
            if (validFlags.contains(commandParts[1].trim().toLowerCase())) {
                return new FilterCommand(commandParts[1].trim().toLowerCase(), inputParts[1].trim());
            }
            throw new InvalidCommandException(INVALID_FILTER_FLAG_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_FILTER_MESSAGE);
        }
    }
}
