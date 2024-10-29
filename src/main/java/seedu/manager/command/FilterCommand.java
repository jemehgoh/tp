package seedu.manager.command;

import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;

//@@author LTK-1606
/**
 * Represents a command to filter out events from the event list.
 * The filter command will filter out and display all events with the specified details
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    private static final String FILTER_BY_PRIORITY_MESSAGE = "Events successfully filtered by priority!";

    protected String flag;
    protected String filterWord;

    /**
     * Constructs a {@code FilterCommand} with the specified flag and filter word.
     *
     * @param flag       the filter flag that determines the type of filtering to be applied.
     * @param filterWord the word to filter by, based on the specified flag.
     */
    public FilterCommand(String flag, String filterWord) {
        super(false);
        this.flag = flag;
        this.filterWord = filterWord;
    }

    /**
     * Executes a filter command by filtering events in different ways,
     * depending on the flag.
     */
    @Override
    public void execute() {
        StringBuilder outputMessage = new StringBuilder();
        EventList filteredEvents = new EventList();

        switch (flag) {
        case "-e":
            break;
        case "-t":
            break;
        case "-u":
            Priority priority = Priority.valueOf(filterWord.trim().toUpperCase());
            filteredEvents = eventList.filterByPriority(priority);
            outputMessage.append(FILTER_BY_PRIORITY_MESSAGE + "\n");
            break;
        default:
            break;
        }

        for (int i = 0; i < filteredEvents.getListSize(); i++) {
            outputMessage.append(String.format("%d. %s\n", i + 1, filteredEvents.getEvent(i).toString()));
        }
        this.message = outputMessage.toString();
    }
}
