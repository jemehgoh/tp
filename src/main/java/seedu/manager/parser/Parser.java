package seedu.manager.parser;

import seedu.manager.command.Command;

import java.util.logging.Logger;

/**
 * Represents the command parser for EventManagerCLI.
 */
public abstract class Parser {
    protected static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    /**
     * Returns a {@link Command} based on the given input string and command parts.
     *
     * @param input        the input string.
     * @param commandParts the command parts.
     * @return a {@link Command} based on the values of input and commandParts
     */
    public abstract Command parse(String input, String[] commandParts);
}
