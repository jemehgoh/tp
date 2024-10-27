package seedu.manager.parser;

import seedu.manager.command.AddCommand;
import seedu.manager.command.Command;
import seedu.manager.exception.InvalidCommandException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.logging.Level.WARNING;

//@@author KuanHsienn
/**
 * Represents the parser for the add command.
 */
public class AddCommandParser extends Parser {
    private static final String FLAGS_ADD_EVENT = String.format("(%s|%s|%s)", ParameterFlags.EVENT_FLAG,
            ParameterFlags.TIME_FLAG, ParameterFlags.VENUE_FLAG);
    private static final String FLAGS_ADD_PARTICIPANT = String.format("(%s|%s)", ParameterFlags.PARTICIPANT_FLAG,
            ParameterFlags.EVENT_FLAG);
    private static final String INVALID_ADD_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            add -e EVENT -t TIME -v VENUE
            add -p PARTICIPANT -e EVENT""";
    private static final String INVALID_DATE_TIME_MESSAGE = """
            Invalid date-time format!
            Please use the following format for event time:
            YYYY-MM-DD HH:mm
            """;

    /**
     * Returns an {@link AddCommand} based on the provided command parts and input string.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string into parts to create an
     * {@link AddCommand} for adding an event. If the command flag is {@code "-p"},
     * it creates an {@link AddCommand} for adding a participant to an event. If neither
     * flag is matched, it throws a {@link InvalidCommandException} with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException if the flags are not matched in the command parts.
     */
    @Override
    public Command parse(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(AddCommand.COMMAND_WORD);
        try {
            String commandFlag = commandParts[1];
            String[] inputParts;

            if (commandFlag.equals(ParameterFlags.EVENT_FLAG)) {
                inputParts = input.split(FLAGS_ADD_EVENT);
                LOGGER.info("Creating AddCommand for event with details: " +
                        inputParts[1].trim() + ", " + inputParts[2].trim() + ", " + inputParts[3].trim());
                LocalDateTime eventTime = LocalDateTime.parse(inputParts[2].trim(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                return new AddCommand(inputParts[1].trim(), eventTime, inputParts[3].trim());
            } else if (commandFlag.equals(ParameterFlags.PARTICIPANT_FLAG)) {
                inputParts = input.split(FLAGS_ADD_PARTICIPANT);
                LOGGER.info("Creating AddCommand for participant with details: " +
                        inputParts[1].trim() + ", " + inputParts[2].trim());
                return new AddCommand(inputParts[1].trim(), inputParts[2].trim());
            }

            LOGGER.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            LOGGER.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        }  catch (DateTimeParseException exception) {
            LOGGER.log(WARNING,"Invalid date-time format");
            throw new InvalidCommandException(INVALID_DATE_TIME_MESSAGE);
        }
    }
}
