package seedu.manager.parser;

import seedu.manager.command.Command;
import seedu.manager.command.SortCommand;
import seedu.manager.exception.InvalidCommandException;

import java.util.Set;

import static java.util.logging.Level.WARNING;

//@@author MatchaRRR
public class SortCommandParser extends Parser {
    private static final String INVALID_SORT_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            sort -e EVENT -by name/time/priority
            """;
    private static final String INVALID_SORT_KEYWORD_MESSAGE = """
            Invalid sort keyword!
            Please set the sort keyword as either "name"/"time"/"priority"
            """;

    /**
     * Parses the input string to create a {@link Command} based on the provided command parts.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-by"}, it splits the input string to create a {@link SortCommand}
     * Otherwise, it throws an {@link InvalidCommandException} with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException if the flag is not matched.
     */
    public Command parse(String input, String[] commandParts) throws InvalidCommandException{
        assert commandParts[0].equalsIgnoreCase(SortCommand.COMMAND_WORD);
        try {
            String[] inputParts = input.split("-by", 2);
            if (inputParts.length < 2) {
                throw new InvalidCommandException(INVALID_SORT_MESSAGE);
            }

            String keyword = inputParts[1].trim();
            System.out.println(keyword);
            Set<String> validKeywords = Set.of("name", "time", "priority");
            if (validKeywords.contains(keyword.toLowerCase())) {
                return new SortCommand(keyword);
            }
            throw new InvalidCommandException(INVALID_SORT_KEYWORD_MESSAGE);

        } catch (IndexOutOfBoundsException exception) {
            LOGGER.log(WARNING, "Invalid command format");
            throw new InvalidCommandException(INVALID_SORT_MESSAGE);
        }
    }

}
