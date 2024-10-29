package seedu.manager.parser;

import seedu.manager.command.AddCommand;
import seedu.manager.command.Command;
import seedu.manager.enumeration.Priority;
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
    private static final String FLAGS_ADD_EVENT = String.format("(%s|%s|%s|%s)", ParameterFlags.EVENT_FLAG,
            ParameterFlags.TIME_FLAG, ParameterFlags.VENUE_FLAG, ParameterFlags.PRIORITY_FLAG);
    private static final String FLAGS_ADD_PARTICIPANT = String.format("(%s|%s|%s|%s)", ParameterFlags.PARTICIPANT_FLAG,
            ParameterFlags.NUMBER_FLAG, ParameterFlags.EMAIL_FLAG, ParameterFlags.EVENT_FLAG);
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
    private static final String INVALID_PRIORITY_MESSAGE = """
            Invalid priority level status!
            Please use the following format for priority level:
            high/medium/low
            """;

    /**
     * Returns an {@link AddCommand} based on the provided command parts and input string.
     *
     * <p>
     * The returned {@link AddCommand} either adds an event or a participant based on the
     *         command flag in the command parts.
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

            if (commandFlag.equals(ParameterFlags.EVENT_FLAG)) {
                return getAddEventCommand(input);
            } else if (commandFlag.equals(ParameterFlags.PARTICIPANT_FLAG)) {
                return getAddParticipantCommand(input);
            }

            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        } catch (DateTimeParseException exception) {
            logger.log(WARNING,"Invalid date-time format");
            throw new InvalidCommandException(INVALID_DATE_TIME_MESSAGE);
        } catch (IllegalArgumentException exception) {
            throw new InvalidCommandException(INVALID_PRIORITY_MESSAGE);
        }
    }

    /**
     * Returns an {@link AddCommand} to add an event based on the given input.
     *
     * @param input the given input.
     * @return an {@link AddCommand} with attributes based on the fields in the input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws DateTimeParseException if the date-time data cannot be parsed.
     */
    private AddCommand getAddEventCommand(String input) throws IndexOutOfBoundsException, DateTimeParseException {
        String[] inputParts = input.split(FLAGS_ADD_EVENT);
        String eventName = inputParts[1].trim();
        LocalDateTime eventTime = LocalDateTime.parse(inputParts[2].trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String eventVenue = inputParts[3].trim();
        Priority eventPriority = Priority.valueOf(inputParts[4].trim().toUpperCase());

        logger.info("Creating AddCommand for event with details: " +
                eventName + ", " + inputParts[2].trim() + ", " + eventVenue);
        return new AddCommand(eventName, eventTime, eventVenue, eventPriority);
    }

    /**
     * Returns an {@link AddCommand} to add a participant to an event based on the given input.
     *
     * @param input the given input.
     * @return an {@link AddCommand} with attributes based on the fields in the input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private AddCommand getAddParticipantCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(FLAGS_ADD_PARTICIPANT);
        logger.info("Creating AddCommand for participant with details: " +
                inputParts[1].trim() + ", " + inputParts[2].trim());
        return new AddCommand(inputParts[1].trim(), inputParts[2].trim(), inputParts[3].trim(), inputParts[4].trim());
    }
}
