package seedu.manager.command;

//@@author glenn-chew
/**
 * Represents an executable menu command
 */
public class MenuCommand extends Command {
    public static final String COMMAND_WORD = "menu";
    private static final String MENU_MESSAGE = """
            Here are the possible commands:
           
            add -e EVENT -t TIME -v VENUE -u PRIORITY: Add an event to the event list.
            list: List events.
            remove -e EVENT: Remove an event from the event list.
            add -p PARTICIPANT -n NUMBER -email EMAIL -e EVENT: Add a participant to an event.
            view -e EVENT: View the list of participants of an event.
            remove -p PARTICIPANT -e EVENT: Remove a participant from an event.
            mark -e EVENT -s STATUS: Mark an event as done or not done.
            mark -p PARTICIPANT -e EVENT -s STATUS: Mark a participant as present or absent.
            sort -by KEYWORD: Sorts events by name/time/priority.
            copy FROM_EVENT > TO_EVENT: Copies participant list from one event to another.
            filter -e/-t/-u FILTER_DESCRIPTION: Filters events by name/time/priority.
            find -e EVENT -p NAME: Finds all participants with specified name in an event.
            exit: Exit program""";

    /**
     * Constructs a new MenuCommand
     */
    public MenuCommand() {
        super(false);
    }

    /**
     * Executes the menu command
     */
    @Override
    public void execute() {
        this.message = MENU_MESSAGE;
    }
}
