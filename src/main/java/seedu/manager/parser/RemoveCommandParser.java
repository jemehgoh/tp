package seedu.manager.parser;

import seedu.manager.command.Command;
import seedu.manager.command.RemoveCommand;
import seedu.manager.exception.InvalidCommandException;

import static java.util.logging.Level.WARNING;

/**
 * Represents the parser for the remove command.
 */
public class RemoveCommandParser extends Parser {
    private static final String FLAGS_REMOVE_PARTICIPANT = String.format("(%s|%s)", ParameterFlags.PARTICIPANT_FLAG,
            ParameterFlags.EVENT_FLAG);
    private static final String INVALID_REMOVE_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            remove -e EVENT
            remove -p PARTICIPANT -e EVENT""";

    //@@author LTK-1606
    /**
     * Returns an {@link RemoveCommand} based on the provided command parts and input string.
     *
     * <p>
     * The returned {@link RemoveCommand} either removes an event or a participant, based on the
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
        assert commandParts[0].equalsIgnoreCase(RemoveCommand.COMMAND_WORD);
        try {
            String commandFlag = commandParts[1];

            if (commandFlag.equals(ParameterFlags.EVENT_FLAG)) {
                return getRemoveEventCommand(input);
            } else if (commandFlag.equals(ParameterFlags.PARTICIPANT_FLAG)) {
                return getRemoveParticipantCommand(input);
            }

            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_REMOVE_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_REMOVE_MESSAGE);
        }
    }

    /**
     * Returns a {@link RemoveCommand} to remove an event based on the given input string.
     *
     * @param input the given input string.
     * @return a {@link RemoveCommand} to remove an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in put.
     */
    private RemoveCommand getRemoveEventCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(ParameterFlags.EVENT_FLAG);
        return new RemoveCommand(inputParts[1].trim());
    }

    /**
     * Returns a {@link RemoveCommand} to remove a participant based on the given input string.
     *
     * @param input the given input string.
     * @return a {@link RemoveCommand} to remove a participant with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     */
    private RemoveCommand getRemoveParticipantCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(FLAGS_REMOVE_PARTICIPANT);
        return new RemoveCommand(inputParts[1].trim(), inputParts[2].trim());
    }
}
