package seedu.manager.parser;

import seedu.manager.command.Command;

import java.util.logging.Logger;

/**
 * Represents the command parser for EventManagerCLI
 */
public abstract class Parser {
    protected static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    public abstract Command parse(String input, String[] commandParts);
}
