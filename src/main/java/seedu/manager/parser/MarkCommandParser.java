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

            if (commandFlag.equalsIgnoreCase("-e")) {
                String[] inputParts = input.split("-e|-s");
                return getMarkEventCommand(inputParts[1].trim(), inputParts[2].trim());
            } else if (commandFlag.equalsIgnoreCase("-p")) {
                String[] inputParts = input.split("-p|-e|-s");
                return getMarkParticipantCommand(inputParts[1].trim(), inputParts[2].trim(), inputParts[3].trim());
            }

            LOGGER.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            LOGGER.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkEventCommand} with a given event name and status. If the given status is invalid,
     *     throws an {@link InvalidCommandException}.
     *
     * @param eventName the given event name.
     * @param status the given event status.
     * @return a MarkCommand with a given event name and status
     * @throws InvalidCommandException if the given status is invalid.
     */
    private MarkEventCommand getMarkEventCommand(String eventName, String status) throws InvalidCommandException {
        if (status.equalsIgnoreCase("done")) {
            return new MarkEventCommand(eventName, true);
        } else if (status.equalsIgnoreCase("undone")) {
            return new MarkEventCommand(eventName, false);
        } else {
            LOGGER.log(WARNING,"Invalid status keyword");
            throw new InvalidCommandException(INVALID_EVENT_STATUS_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkParticipantCommand} with a given participant name, event name and status. If the given status is
     *     invalid, throws an {@link InvalidCommandException}.
     *
     * @param participantName the given participant name.
     * @param eventName the given event name.
     * @param status the given event status.
     * @return a MarkCommand with a given event name and status
     * @throws InvalidCommandException if the given status is invalid.
     */
    private MarkParticipantCommand getMarkParticipantCommand(String participantName, String eventName, String status) {
        if (status.equalsIgnoreCase("present")) {
            return new MarkParticipantCommand(participantName, eventName, true);
        } else if (status.equalsIgnoreCase("absent")) {
            return new MarkParticipantCommand(participantName, eventName, false);
        } else {
            LOGGER.log(WARNING, "Invalid status keyword");
            throw new InvalidCommandException(INVALID_PARTICIPANT_STATUS_MESSAGE);
        }
    }
}
