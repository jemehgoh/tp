package seedu.manager.parser;

import seedu.manager.command.AddCommand;
import seedu.manager.command.Command;
import seedu.manager.command.ExitCommand;
import seedu.manager.command.ListCommand;
import seedu.manager.command.MarkCommand;
import seedu.manager.command.MenuCommand;
import seedu.manager.command.RemoveCommand;
import seedu.manager.command.ViewCommand;
import seedu.manager.command.SortCommand;
import seedu.manager.exception.InvalidCommandException;

/**
 * Represents the manager for the command parsers in EventManagerCLI.
 */
public class ParserManager {
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command!";

    /**
     * Returns a command based on the given user command string.
     *
     * @param command The given command string from the user.
     * @throws InvalidCommandException if the given command string cannot be parsed to a valid command.
     */
    public Command parseCommand(String command) throws InvalidCommandException {
        String[] commandParts = command.split(" ");
        String commandWord = commandParts[0];

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(command, commandParts);
        case RemoveCommand.COMMAND_WORD:
            return new RemoveCommandParser().parse(command, commandParts);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(command, commandParts);
        case MenuCommand.COMMAND_WORD:
            return new MenuCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(command, commandParts);
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(command, commandParts);
        default:
            throw new InvalidCommandException(INVALID_COMMAND_MESSAGE);
        }
    }
}
