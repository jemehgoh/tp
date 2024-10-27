package seedu.manager.parser;

import seedu.manager.command.Command;
import seedu.manager.command.MarkCommand;
import seedu.manager.command.MarkEventCommand;
import seedu.manager.command.MarkParticipantCommand;
import seedu.manager.exception.InvalidCommandException;

import static java.util.logging.Level.WARNING;

//@@author jemehgoh
/**
 * Represents the parser for the mark command.
 */
public class MarkCommandParser extends Parser {
    private static final String FLAGS_MARK_EVENT = String.format("%s|%s", ParameterFlags.EVENT_FLAG,
            ParameterFlags.STATUS_FLAG);
    private static final String FLAGS_MARK_PARTICIPANT = String.format("%s|%s|%s", ParameterFlags.PARTICIPANT_FLAG,
            ParameterFlags.EVENT_FLAG, ParameterFlags.STATUS_FLAG);
    private static final String INVALID_MARK_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            mark -e EVENT -s STATUS
            mark -p PARTICIPANT -e EVENT -s STATUS""";
    private static final String INVALID_EVENT_STATUS_MESSAGE = """
            Invalid event status!
            Please set the event status as either "done" or "undone"
            """;
    private static final String INVALID_PARTICIPANT_STATUS_MESSAGE = """
            Invalid participant status!
            Please set the event status as either "present" or "absent"
            """;

    /**
     * Returns a {@link Command} based on the provided input string and command parts.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create a {@link MarkCommand}
     * to mark an event done or undone. Otherwise, it throws an {@link InvalidCommandException}
     * with an error message.
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
        assert commandParts[0].equalsIgnoreCase(MarkCommand.COMMAND_WORD);
        try {
            String commandFlag = commandParts[1];

            if (commandFlag.equalsIgnoreCase(ParameterFlags.EVENT_FLAG)) {
                return getMarkEventCommand(input);
            } else if (commandFlag.equalsIgnoreCase(ParameterFlags.PARTICIPANT_FLAG)) {
                return getMarkParticipantCommand(input);
            }

            LOGGER.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            LOGGER.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkEventCommand} with a given command input.
     *
     * @param input the command input.
     * @return a {@link MarkEventCommand}, with attributes parsed from fields in input.
     * @throws InvalidCommandException if the status field in input is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private MarkEventCommand getMarkEventCommand(String input) throws InvalidCommandException,
            IndexOutOfBoundsException {
        String[] inputParts = input.split(FLAGS_MARK_EVENT);
        boolean toMark = getMarkEventStatus(inputParts[2].trim());
        return new MarkEventCommand(inputParts[1].trim(), toMark);
    }

    /**
     * Returns a {@link MarkParticipantCommand} with a given command input.
     *
     * @param input the command input.
     * @return a {@link MarkParticipantCommand}, with attributes parsed from fields in input.
     * @throws InvalidCommandException if the status field in input is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private MarkParticipantCommand getMarkParticipantCommand(String input) throws InvalidCommandException,
            IndexOutOfBoundsException {
        String[] inputParts = input.split(FLAGS_MARK_PARTICIPANT);
        boolean toMark = getMarkParticipantStatus(inputParts[3].trim());
        return new MarkParticipantCommand(inputParts[1].trim(), inputParts[2].trim(), toMark);
    }

    /**
     * Returns true if status is "done", false if status is "undone". Throws an InvalidCommandException otherwise.
     *
     * @param status the status keyword
     * @return true if status is "done", false if status is "undone".
     * @throws InvalidCommandException if status is neither "done" nor "undone".
     */
    private boolean getMarkEventStatus(String status) throws InvalidCommandException {
        if (status.equalsIgnoreCase("done")) {
            return true;
        } else if (status.equalsIgnoreCase("undone")) {
            return false;
        } else {
            LOGGER.log(WARNING, "Invalid status keyword");
            throw new InvalidCommandException(INVALID_EVENT_STATUS_MESSAGE);
        }
    }

    /**
     * Returns true if status is "present", false if status is "absent". Throws an InvalidCommandException otherwise.
     *
     * @param status the status keyword
     * @return true if status is "present", false if status is "absent".
     * @throws InvalidCommandException if status is neither "present" nor "absent".
     */
    private boolean getMarkParticipantStatus(String status) throws InvalidCommandException {
        if (status.equalsIgnoreCase("present")) {
            return true;
        } else if (status.equalsIgnoreCase("absent")) {
            return false;
        } else {
            LOGGER.log(WARNING, "Invalid status keyword");
            throw new InvalidCommandException(INVALID_PARTICIPANT_STATUS_MESSAGE);
        }
    }
}
