package seedu.manager.parser;

import seedu.manager.command.Command;
import seedu.manager.command.CopyCommand;
import seedu.manager.exception.InvalidCommandException;

import static java.util.logging.Level.WARNING;

//@author LTK-1606
public class CopyCommandParser extends Parser {
    private static final String INVALID_COPY_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            copy FROM_EVENT > TO_EVENT
            """;

    /**
     * Parses the input command to create a {@code CopyCommand} object.
     * <p>
     * This method checks if the command input starts with the specified command word
     * and then removes it from the input. It splits the remaining input at the '>' character
     * to separate the source and destination parts. If the split does not yield exactly
     * two parts, an {@code InvalidCommandException} is thrown.
     * </p>
     *
     * @param input the full command input string to be parsed
     * @param commandParts the parts of the command, typically split by whitespace
     * @return a {@code CopyCommand} object with the parsed source and destination
     * @throws InvalidCommandException if the command is missing required parts or has an invalid format
     */
    public Command parse(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(CopyCommand.COMMAND_WORD);

        try {
            String commandInput = input.replaceFirst("^" + commandParts[0] + "\\s*", "");
            String[] inputParts = commandInput.split(">");

            if (inputParts.length != 2) {
                throw new InvalidCommandException(INVALID_COPY_MESSAGE);
            }

            return new CopyCommand(inputParts[0].trim(), inputParts[1].trim());

        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_COPY_MESSAGE);
        }
    }
}
