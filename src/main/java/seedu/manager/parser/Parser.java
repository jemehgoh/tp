package seedu.manager.parser;

import seedu.manager.command.AddCommand;
import seedu.manager.command.Command;
import seedu.manager.command.CopyCommand;
import seedu.manager.command.ExitCommand;
import seedu.manager.command.FilterCommand;
import seedu.manager.command.ListCommand;
import seedu.manager.command.MarkCommand;
import seedu.manager.command.MarkEventCommand;
import seedu.manager.command.MarkItemCommand;
import seedu.manager.command.MarkParticipantCommand;
import seedu.manager.command.MenuCommand;
import seedu.manager.command.RemoveCommand;
import seedu.manager.command.EditParticipantCommand;
import seedu.manager.command.EditEventCommand;
import seedu.manager.command.EditItemCommand;
import seedu.manager.command.SortCommand;
import seedu.manager.command.ViewCommand;
import seedu.manager.command.FindCommand;
import seedu.manager.enumeration.Priority;
import seedu.manager.exception.InvalidCommandException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.util.logging.Level.WARNING;

/**
 * Represents the command parser for EventManagerCLI
 */
public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command!";
    private static final String INVALID_ADD_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            add -e EVENT -t TIME -v VENUE -u PRIORITY
            add -p PARTICIPANT -n NUMBER -email EMAIL -e EVENT
            add -m ITEM -e EVENT
            """;
    private static final String INVALID_REMOVE_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            remove -e EVENT
            remove -p PARTICIPANT -e EVENT
            remove -m ITEM -e EVENT
            """;
    private static final String INVALID_EDIT_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            edit -e OLD_EVENT -name NEW_EVENT -t TIME -v VENUE -u PRIORITY: Edit event info.
            edit -m ITEM > NEW_ITEM -e EVENT: Edit an item from an event.
            edit -p OLD_PARTICIPANT -name NEW_PARTICIPANT -n NUMBER -email EMAIL -e EVENT: Edit participant info.
            """;
    private static final String INVALID_VIEW_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            view -e EVENT -y TYPE
            """;
    private static final String INVALID_MARK_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            mark -e EVENT -s STATUS
            mark -p PARTICIPANT -e EVENT -s STATUS
            mark -m ITEM -e EVENT -s STATUS
            """;
    private static final String INVALID_COPY_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            copy FROM_EVENT > TO_EVENT
            """;
    private static final String INVALID_SORT_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            sort -e EVENT -by name/time/priority
            """;
    private static final String INVALID_FILTER_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            filter -e/-d/-t/-x/-u FILTER_DESCRIPTION
            """;
    private static final String INVALID_FIND_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            find -e EVENT -p NAME
            """;
    private static final String INVALID_DATE_TIME_MESSAGE = """
            Invalid date-time format!
            Please use the following format for event time:
            YYYY-MM-DD HH:mm
            
            MM-DD has to be between 01-01 and 12-31, and HH:mm has to be between 00:00 and 23:59.
            """;
    private static final String INVALID_PRIORITY_MESSAGE = """
            Invalid priority level status!
            Please use the following format for priority level:
            high/medium/low
            """;
    private static final String INVALID_PHONE_NUMBER_MESSAGE = """
            Invalid phone number!
            Please enter a valid phone number with digits only.
            """;
    private static final String INVALID_EMAIL_MESSAGE = """
            Invalid email format!
            Please enter a valid email address.
            """;
    private static final String INVALID_TYPE_MESSAGE = """
            Invalid type!
            Please set the type as either "participant" or "item"
            """;
    private static final String INVALID_EVENT_STATUS_MESSAGE = """
            Invalid event status!
            Please set the event status as either "done" or "undone"
            """;
    private static final String INVALID_PARTICIPANT_STATUS_MESSAGE = """
            Invalid participant status!
            Please set the event status as either "present" or "absent"
            """;
    private static final String INVALID_ITEM_STATUS_MESSAGE = """
            Invalid mark status!
            Please set the event status as either "accounted" or "unaccounted"
            """;
    private static final String INVALID_SORT_KEYWORD_MESSAGE = """
            Invalid sort keyword!
            Please set the sort keyword as either "name"/"time"/"priority"
            """;
    private static final String INVALID_FILTER_FLAG_MESSAGE = """
            Invalid filter flag!
            Please set the filter flag as either "-e/-t/-u"
            """;
    private static final String INVALID_FIND_FLAG_MESSAGE = """
            Invalid find flag!
            Please set the find flag using "-e" and "-p""
            """;

    private static final String EVENT_FLAG = "-e";
    private static final String PARTICIPANT_FLAG = "-p";
    private static final String ITEM_FLAG = "-m";

    private static final String SPACE = " ";
    private static final String ARROW = ">";

    private static final String EVENT_REGEX = "(-e|-t|-v|-u)";
    private static final String EVENT_ATTRIBUTE_REGEX = "(-e|-name|-t|-v|-u)";
    private static final String PARTICIPANT_REGEX = "(-p|-name|-n|-email|-e)";
    private static final String ITEM_REGEX = "(-m|-e)";
    private static final String REMOVE_PARTICIPANT_REGEX = "(-p|-e)";
    private static final String MARK_EVENT_REGEX = "-e|-s";
    private static final String MARK_PARTICIPANT_REGEX = "-p|-e|-s";
    private static final String FIND_REGEX = "\\s*(-e|-p)\\s*";
    private static final String VIEW_REGEX = "(-e|-y)";
    private static final String MARK_ITEM_REGEX = "-m|-e|-s";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{8}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-]+$");

    /**
     * Returns a command based on the given user command string.
     *
     * @param command The given command string from the user.
     * @throws InvalidCommandException if the given command string cannot be parsed to a valid command.
     */
    public Command parseCommand(String command) throws InvalidCommandException {
        String[] commandParts = command.trim().split(SPACE);
        String commandWord = commandParts[0].toLowerCase();
        try {
            switch (commandWord) {
            case MenuCommand.COMMAND_WORD:
                return new MenuCommand();
            case ListCommand.COMMAND_WORD:
                return new ListCommand();
            case AddCommand.COMMAND_WORD:
                return parseAddCommand(command, commandParts);
            case RemoveCommand.COMMAND_WORD:
                return parseRemoveCommand(command, commandParts);
            case EditParticipantCommand.COMMAND_WORD:
                return parseEditCommand(command, commandParts);
            case ViewCommand.COMMAND_WORD:
                return parseViewCommand(command, commandParts);
            case MarkCommand.COMMAND_WORD:
                return parseMarkCommand(command, commandParts);
            case CopyCommand.COMMAND_WORD:
                return parseCopyCommand(command, commandParts);
            case SortCommand.COMMAND_WORD:
                return parseSortCommand(command, commandParts);
            case FilterCommand.COMMAND_WORD:
                return parseFilterCommand(command, commandParts);
            case FindCommand.COMMAND_WORD:
                return parseFindCommand(command, commandParts);
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();
            default:
                throw new InvalidCommandException(INVALID_COMMAND_MESSAGE);
            }
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING, "Invalid command format");
            String errorMessage = getErrorMessage(commandWord);
            throw new InvalidCommandException(errorMessage);
        } catch (DateTimeParseException exception) {
            logger.log(WARNING, "Invalid date-time format");
            throw new InvalidCommandException(INVALID_DATE_TIME_MESSAGE);
        } catch (IllegalArgumentException exception) {
            logger.log(WARNING, "Invalid priority level status");
            throw new InvalidCommandException(INVALID_PRIORITY_MESSAGE);
        }
    }

    //@@author LTK-1606
    /**
     * Parses the input string to create an {@link Command} object based on the provided command parts.
     * <p>
     * This method examines the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create an {@link AddCommand} for adding an event
     * with the specified details (event name, time, and venue). If the command flag is {@code "-p"},
     * it creates an {@link AddCommand} for adding a participant to an event, including the participant's
     * name, contact number, email, and the event name. If the command flag does not match either,
     * an {@link InvalidCommandException} is thrown with an error message.
     * </p>
     *
     * @param input        the input string containing the command details
     * @param commandParts an array of strings representing the parsed command parts, where the second element
     *                     is the command flag, indicating the type of command
     * @return a {@link Command} object representing the parsed command
     * @throws InvalidCommandException   if the command flag is invalid, or if there are improperly
     *                                   formatted input details
     * @throws IndexOutOfBoundsException if not all parameters are present.
     * @throws DateTimeParseException    if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException  if the priority parameter is not valid.
     */
    public Command parseAddCommand(String input, String[] commandParts) throws InvalidCommandException,
            IndexOutOfBoundsException, DateTimeParseException, IllegalArgumentException {
        assert commandParts[0].equalsIgnoreCase(AddCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        switch (commandFlag) {
        case EVENT_FLAG:
            return getAddEventCommand(input);
        case PARTICIPANT_FLAG:
            return getAddParticipantCommand(input);
        case ITEM_FLAG:
            return getAddItemCommand(input);
        default:
            logger.log(WARNING, "Invalid command format");
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        }
    }

    /**
     * Returns an {@link AddCommand} that adds an event with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws DateTimeParseException    if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException  if the priority parameter is not valid.
     */
    private Command getAddEventCommand(String input) throws IndexOutOfBoundsException, DateTimeParseException,
            IllegalArgumentException {
        String[] inputParts = input.split(EVENT_REGEX);
        logger.info("Creating AddCommand for event with details: " +
                inputParts[1].trim() + ", " + inputParts[2].trim() + ", " + inputParts[3].trim());
        String eventName = inputParts[1].trim();
        LocalDateTime eventTime = LocalDateTime.parse(inputParts[2].trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String venue = inputParts[3].trim();
        Priority eventPriority = Priority.valueOf(inputParts[4].trim().toUpperCase());
        return new AddCommand(eventName, eventTime, venue, eventPriority);
    }

    //@@author LTK-1606
    /**
     * Returns an {@link AddCommand} that adds a participant with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds a participant with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws InvalidCommandException   if the input phone number and email are not in the correct format.
     */
    private Command getAddParticipantCommand(String input) throws IndexOutOfBoundsException, InvalidCommandException {
        String[] inputParts = input.split(PARTICIPANT_REGEX);
        logger.info("Creating AddCommand for participant with details: " +
                inputParts[1].trim() + ", " + inputParts[2].trim());
        String participantName = inputParts[1].trim();
        String participantNumber = inputParts[2].trim();
        String participantEmail = inputParts[3].trim();
        String eventName = inputParts[4].trim();

        if (!isValidPhoneNumber(participantNumber)) {
            logger.log(WARNING, "Invalid phone number format");
            throw new InvalidCommandException(INVALID_PHONE_NUMBER_MESSAGE);
        }

        if (!isValidEmail(participantEmail)) {
            logger.log(WARNING, "Invalid email format");
            throw new InvalidCommandException(INVALID_EMAIL_MESSAGE);
        }

        return new AddCommand(participantName, participantNumber, participantEmail, eventName);
    }

    //@@author jemehgoh
    /**
     * Returns an {@link AddCommand} that adds an item with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds an item with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getAddItemCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(ITEM_REGEX);
        String itemName = inputParts[1].trim();
        String eventName = inputParts[2].trim();
        logger.info(String.format("Creating AddCommand for item with details: %s, %s", itemName,
                eventName));
        return new AddCommand(itemName, eventName);
    }

    //@@author LTK-1606
    /**
     * Parses the input string to create a {@link Command} based on the provided command parts.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create a {@link RemoveCommand}
     * for removing an event. If the command flag is {@code "-p"}, it creates a
     * {@link RemoveCommand} for removing a participant from an event. If neither flag
     * is matched, it throws an {@link InvalidCommandException} with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException   if the flags are not matched in the command parts.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command parseRemoveCommand(String input, String[] commandParts) throws InvalidCommandException,
            IndexOutOfBoundsException {
        assert commandParts[0].equalsIgnoreCase(RemoveCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        switch (commandFlag) {
        case EVENT_FLAG:
            return getRemoveEventCommand(input);
        case PARTICIPANT_FLAG:
            return getRemoveParticipantCommand(input);
        case ITEM_FLAG:
            return getRemoveItemCommand(input);
        default:
            logger.log(WARNING, "Invalid command format");
            throw new InvalidCommandException(INVALID_REMOVE_MESSAGE);
        }
    }

    //@@author KuanHsienn

    /**
     * Returns a {@link RemoveCommand} that removes an event, with a given user input.
     *
     * @param input the user input to be parsed.
     * @return a {@link RemoveCommand} that removes an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     */
    private RemoveCommand getRemoveEventCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(EVENT_FLAG);
        return new RemoveCommand(inputParts[1].trim());
    }

    //@@author LTK-1606
    /**
     * Returns a {@link RemoveCommand} that removes a participant, with fields from a given user input.
     *
     * @param input the user input to be parsed.
     * @return a {@link RemoveCommand} that removes a participant with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     */
    private RemoveCommand getRemoveParticipantCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(REMOVE_PARTICIPANT_REGEX);
        return new RemoveCommand(inputParts[1].trim(), inputParts[2].trim(), true);
    }

    //@@author jemehgoh
    /**
     * Returns a {@link RemoveCommand} that removes an item, with fields from a given user input.
     *
     * @param input the user input to be parsed.
     * @return a {@link RemoveCommand} that removes an item with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     */
    private RemoveCommand getRemoveItemCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(ITEM_REGEX);
        return new RemoveCommand(inputParts[1].trim(), inputParts[2].trim(), false);
    }

    /**
     * Parses the input string to create a Command object based on the provided command parts.
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is "-e", it splits the input string to create an EditCommand
     *
     * @return a Command object representing the parsed command.
     * @throws InvalidCommandException if the flags are not matched in the command parts.
     * @throws DateTimeParseException    if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException  if the priority parameter is not valid.
     */
    private Command parseEditCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(EditParticipantCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        switch (commandFlag) {
        case EVENT_FLAG:
            return getEditEventCommand(input);
        case PARTICIPANT_FLAG:
            return getEditParticipantCommand(input);
        case ITEM_FLAG:
            return getEditItemCommand(input);
        default:
            logger.log(WARNING, "Invalid command format");
            throw new InvalidCommandException(INVALID_EDIT_MESSAGE);
        }
    }

    //@@author KuanHsienn
    /**
     * Returns an {@link EditParticipantCommand} that edits a participant with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link EditParticipantCommand} that edits a participant with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getEditParticipantCommand(String input) throws IndexOutOfBoundsException, InvalidCommandException {
        String[] inputParts = input.split(PARTICIPANT_REGEX);
        String participantName = inputParts[1].trim();
        String newName = inputParts[2].trim();
        String newNumber = inputParts[3].trim();
        String newEmail = inputParts[4].trim();
        String eventName = inputParts[5].trim();

        if (!isValidPhoneNumber(newNumber)) {
            logger.log(WARNING, "Invalid phone number format");
            throw new InvalidCommandException(INVALID_PHONE_NUMBER_MESSAGE);
        }

        if (!isValidEmail(newEmail)) {
            logger.log(WARNING, "Invalid email format");
            throw new InvalidCommandException(INVALID_EMAIL_MESSAGE);
        }

        return new EditParticipantCommand(participantName, newName, newNumber, newEmail, eventName);
    }

    //@@author MatchaRRR
    /**
     * Returns an {@link EditEventCommand} that edits an event with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link EditEventCommand} that edits an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws DateTimeParseException    if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException  if the priority parameter is not valid.
     */
    private Command getEditEventCommand(String input) throws IndexOutOfBoundsException, DateTimeParseException,
            IllegalArgumentException {
        String[] inputParts = input.split(EVENT_ATTRIBUTE_REGEX);

        String eventName = inputParts[1].trim();
        String eventNewName = inputParts[2].trim();
        LocalDateTime eventTime = LocalDateTime.parse(inputParts[3].trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String eventVenue = inputParts[4].trim();
        Priority eventPriority = Priority.valueOf(inputParts[5].trim().toUpperCase());

        return new EditEventCommand(eventName, eventNewName, eventTime, eventVenue, eventPriority);
    }

    //@@author MatchaRRR
    /**
     * Returns an {@link EditItemCommand} that edits an event with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link EditEventCommand} that edits an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getEditItemCommand(String input) {
        String[] inputParts = input.split(ITEM_REGEX);
        String itemName = inputParts[1].split(ARROW)[0].trim();
        String itemNewName = inputParts[1].split(ARROW)[1].trim();
        String eventName = inputParts[2].trim();
        return new EditItemCommand(itemName, itemNewName, eventName);
    }

    //@@author KuanHsienn
    /**
     * Checks if the phone number is valid.
     *
     * @param phoneNumber the phone number to validate.
     * @return true if the phone number is valid, false otherwise.
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    //@@author KuanHsienn
    /**
     * Checks if the email address is valid.
     *
     * @param email the email address to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    //@@author glenn-chew
    /**
     * Parses the input string to create a {@link Command} based on the provided command parts.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create a {@link ViewCommand}
     * for viewing the participants in the event.
     * Otherwise, it throws an {@link InvalidCommandException} with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException   if the flag is not matched.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command parseViewCommand(String input, String[] commandParts) throws InvalidCommandException,
            IndexOutOfBoundsException {
        assert commandParts[0].equalsIgnoreCase(ViewCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        if (commandFlag.equals(EVENT_FLAG)) {
            return getViewCommand(input);
        }

        logger.log(WARNING, "Invalid command format");
        throw new InvalidCommandException(INVALID_VIEW_MESSAGE);
    }

    //@@author jemehgoh
    /**
     * Returns a {@link ViewCommand} with fields parsed from a given user input.
     *
     * @param input the user input to be parsed.
     * @return a {@link ViewCommand} with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     * @throws InvalidCommandException   if the status parameter in input is invalid.
     */
    private ViewCommand getViewCommand(String input) throws IndexOutOfBoundsException, InvalidCommandException {
        String[] inputParts = input.split(VIEW_REGEX);
        String eventName = inputParts[1].trim();
        String viewType = inputParts[2].trim();
        if (viewType.equalsIgnoreCase("participant")) {
            return new ViewCommand(eventName, true);
        } else if (viewType.equalsIgnoreCase("item")) {
            return new ViewCommand(eventName, false);
        } else {
            throw new InvalidCommandException(INVALID_TYPE_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkCommand} to mark an event, participant or item based on a given input string
     * and command parts.
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link MarkCommand} with fields parsed from input.
     * @throws InvalidCommandException   if the flag is not matched, or if the mark status is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command parseMarkCommand(String input, String[] commandParts) throws InvalidCommandException,
            IndexOutOfBoundsException {
        assert commandParts[0].equalsIgnoreCase(MarkCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        switch (commandFlag) {
        case EVENT_FLAG:
            return getMarkEventCommand(input);
        case PARTICIPANT_FLAG:
            return getMarkParticipantCommand(input);
        case ITEM_FLAG:
            return getMarkItemCommand(input);
        default:
            logger.log(WARNING, "Invalid command format");
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkEventCommand} with fields from a given user input.
     *
     * @param input the given user input.
     * @return a {@link MarkEventCommand} with fields from input.
     * @throws InvalidCommandException   if the status parameter is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getMarkEventCommand(String input) throws InvalidCommandException, IndexOutOfBoundsException {
        String[] inputParts = input.split(MARK_EVENT_REGEX);
        String eventName = inputParts[1].trim();
        boolean isToMark = toMarkEvent(inputParts[2].trim());

        return new MarkEventCommand(eventName, isToMark);
    }

    /**
     * Returns true if status is to mark, returns false if status is to unmark.
     *
     * @param status the status parameter.
     * @return true if status is to mark, returns false if status is to unmark.
     * @throws InvalidCommandException if status is invalid.
     */
    private boolean toMarkEvent(String status) throws InvalidCommandException {
        if (status.equalsIgnoreCase(MarkEventCommand.EVENT_MARK_STATUS)) {
            return true;
        } else if (status.equalsIgnoreCase(MarkEventCommand.EVENT_UNMARK_STATUS)) {
            return false;
        } else {
            logger.log(WARNING, "Invalid status keyword");
            throw new InvalidCommandException(INVALID_EVENT_STATUS_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkParticipantCommand} with fields from a given user input.
     *
     * @param input the given user input.
     * @return a {@link MarkParticipantCommand} with fields from input.
     * @throws InvalidCommandException   if the status parameter is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getMarkParticipantCommand(String input) throws InvalidCommandException, IndexOutOfBoundsException {
        String[] inputParts = input.split(MARK_PARTICIPANT_REGEX);
        String participantName = inputParts[1].trim();
        String eventName = inputParts[2].trim();
        boolean isToMark = toMarkParticipant(inputParts[3].trim());

        return new MarkParticipantCommand(participantName, eventName, isToMark);
    }

    /**
     * Returns true if status is to mark, returns false if status is to unmark.
     *
     * @param status the status parameter.
     * @return true if status is to mark, returns false if status is to unmark.
     * @throws InvalidCommandException if status is invalid.
     */
    private boolean toMarkParticipant(String status) throws InvalidCommandException {
        if (status.equalsIgnoreCase(MarkParticipantCommand.PARTICIPANT_MARK_STATUS)) {
            return true;
        } else if (status.equalsIgnoreCase(MarkParticipantCommand.PARTICIPANT_UNMARK_STATUS)) {
            return false;
        } else {
            logger.log(WARNING, "Invalid status keyword");
            throw new InvalidCommandException(INVALID_PARTICIPANT_STATUS_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkItemCommand} with fields from a given user input.
     *
     * @param input the given user input.
     * @return a {@link MarkItemCommand} with fields from input.
     * @throws InvalidCommandException   if the status parameter is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getMarkItemCommand(String input) throws InvalidCommandException, IndexOutOfBoundsException {
        String[] inputParts = input.split(MARK_ITEM_REGEX);
        String itemName = inputParts[1].trim();
        String eventName = inputParts[2].trim();
        boolean isToMark = toMarkItem(inputParts[3].trim());

        return new MarkItemCommand(itemName, eventName, isToMark);
    }

    /**
     * Returns true if status is "accounted", returns false if status is "unaccounted".
     *
     * @param status the status parameter.
     * @return true if status is "accounted", returns false if status is "unaccounted".
     * @throws InvalidCommandException if status is invalid.
     */
    private boolean toMarkItem(String status) throws InvalidCommandException {
        if (status.equalsIgnoreCase(MarkItemCommand.ITEM_MARK_STATUS)) {
            return true;
        } else if (status.equalsIgnoreCase(MarkItemCommand.ITEM_UNMARK_STATUS)) {
            return false;
        } else {
            logger.log(WARNING, "Invalid status keyword");
            throw new InvalidCommandException(INVALID_ITEM_STATUS_MESSAGE);
        }
    }

    /**
     * Parses the input command to create a {@code CopyCommand} object.
     * <p>
     * This method checks if the command input starts with the specified command word
     * and then removes it from the input. It splits the remaining input at the '>' character
     * to separate the source and destination parts. If the split does not yield exactly
     * two parts, an {@code InvalidCommandException} is thrown.
     * </p>
     *
     * @param input        the full command input string to be parsed
     * @param commandParts the parts of the command, typically split by whitespace
     * @return a {@code CopyCommand} object with the parsed source and destination
     * @throws InvalidCommandException if the command is missing required parts or has an invalid format
     */
    private Command parseCopyCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(CopyCommand.COMMAND_WORD);
        String commandInput = input.replaceFirst("^" + commandParts[0] + "\\s*", "");
        String[] inputParts = commandInput.split(ARROW);

        if (inputParts.length != 2) {
            throw new InvalidCommandException(INVALID_COPY_MESSAGE);
        }

        return new CopyCommand(inputParts[0].trim(), inputParts[1].trim());
    }

    //@@author MatchaRRR

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
    private Command parseSortCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(SortCommand.COMMAND_WORD);
        String[] inputParts = input.split("-by", 2);
        if (inputParts.length < 2) {
            throw new InvalidCommandException(INVALID_SORT_MESSAGE);
        }

        String keyword = inputParts[1].trim();
        Set<String> validKeywords = Set.of("name", "time", "priority");
        if (validKeywords.contains(keyword.toLowerCase())) {
            return new SortCommand(keyword);
        }
        throw new InvalidCommandException(INVALID_SORT_KEYWORD_MESSAGE);
    }

    //@@author LTK-1606
    /**
     * Parses the input string and command parts to create a {@code FilterCommand} object.
     * <p>
     * This method verifies that the first part of {@code commandParts} matches the expected filter command
     * and then checks if a valid flag is provided. The filter flag should be one of <code>"-e"</code>,
     * <code>"-t"</code>, or <code>"-u"</code>, representing different filter types.
     * If the flag is valid and additional input is provided, a new {@code FilterCommand} is created.
     * <p>
     * If the input format is incorrect, or an invalid flag is provided,
     * this method throws an {@code InvalidCommandException}.
     *
     * @param input        the full user input string
     * @param commandParts the split parts of the command, with the first element expected to be the filter command word
     * @return a {@code FilterCommand} object initialized with the specified flag and filter criteria
     * @throws InvalidCommandException if the command format is invalid or an invalid flag is provided
     */
    private Command parseFilterCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(FilterCommand.COMMAND_WORD);
        String[] inputParts = input.split("(-e|-d|-t|-x|-u)");
        if (inputParts.length < 2) {
            throw new InvalidCommandException(INVALID_FILTER_MESSAGE);
        }

        Set<String> validFlags = Set.of(EVENT_FLAG, "-d", "-t", "-x", "-u");
        if (validFlags.contains(commandParts[1].trim().toLowerCase())) {
            return new FilterCommand(commandParts[1].trim().toLowerCase(), inputParts[1].trim());
        }
        throw new InvalidCommandException(INVALID_FILTER_FLAG_MESSAGE);
    }

    /**
     * Parses the input command to create a {@code FindCommand} object.
     * <p>
     * This method checks if the input contains the required flags (-e for event and -p for person).
     * It splits the input into parts based on these flags and validates the resulting segments.
     * If valid, it constructs and returns a new {@code FindCommand} with the specified event name
     * and participant name. If the command format is invalid or the required flags are missing,
     * an {@code InvalidCommandException} is thrown.
     * </p>
     *
     * @param input        the full command input string to be parsed
     * @param commandParts the parts of the command, typically split by whitespace
     * @return a {@code FindCommand} object with the parsed event and person names
     * @throws InvalidCommandException if the command is missing required flags or has an invalid format
     */
    private Command parseFindCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(FindCommand.COMMAND_WORD);
        if (!input.contains(EVENT_FLAG) || !input.contains(PARTICIPANT_FLAG)) {
            throw new InvalidCommandException(INVALID_FIND_FLAG_MESSAGE);
        }

        String[] inputParts = input.split(FIND_REGEX);
        if (inputParts.length < 3 || inputParts[1].isBlank()) {
            throw new InvalidCommandException(INVALID_FIND_MESSAGE);
        }

        return new FindCommand(inputParts[1].trim(), inputParts[2].trim());
    }

    //@@author jemehgoh
    /**
     * Returns an error message corresponding to the given command word.
     *
     * @param commandWord the command word entered.
     * @return an error message corresponding to commandWord.
     */
    private String getErrorMessage(String commandWord) {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return INVALID_ADD_MESSAGE;
        case RemoveCommand.COMMAND_WORD:
            return INVALID_REMOVE_MESSAGE;
        case EditEventCommand.COMMAND_WORD:
            return INVALID_EDIT_MESSAGE;
        case ViewCommand.COMMAND_WORD:
            return INVALID_VIEW_MESSAGE;
        case MarkCommand.COMMAND_WORD:
            return INVALID_MARK_MESSAGE;
        default:
            return INVALID_COMMAND_MESSAGE;
        }
    }
}
