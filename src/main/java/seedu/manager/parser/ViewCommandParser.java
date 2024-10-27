package seedu.manager.parser;

import seedu.manager.command.Command;
import seedu.manager.command.ViewCommand;
import seedu.manager.exception.InvalidCommandException;

import static java.util.logging.Level.WARNING;

//@@author glenn-chew
/**
 * Represents the parser for the view command.
 */
public class ViewCommandParser extends Parser {
    private static final String INVALID_VIEW_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            view -e EVENT""";

    /**
     * Returns a {@link ViewCommand} based on the provided command parts and input string.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create a {@link ViewCommand}
     * for viewing the participants in the event.
     * Otherwise, it throws an {@link InvalidCommandException} with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException if the flag is not matched.
     */
    @Override
    public Command parse(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(ViewCommand.COMMAND_WORD);
        try {
            String commandFlag = commandParts[1];

            if (commandFlag.equals("-e")) {
                String [] inputParts = input.split("-e");
                return new ViewCommand(inputParts[1].trim());
            }

            LOGGER.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_VIEW_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            LOGGER.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_VIEW_MESSAGE);
        }
    }
}
