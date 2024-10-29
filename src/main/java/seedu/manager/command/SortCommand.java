package seedu.manager.command;

import seedu.manager.event.EventList;

/**
 * Represents a command to sort the events in different ways.
 * The sort command will store the sorting keyword.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    private static final String SORT_BY_NAME_MESSAGE = "Events successfully sorted by name!";
    private static final String SORT_BY_TIME_MESSAGE = "Events successfully sorted by time!";
    private static final String SORT_BY_PRIORITY_MESSAGE = "Events successfully sorted by priority level!";

    protected String keyword;

    /**
     * Constructs a new SortCommand with the given keyword
     *
     * @param keyword the keyword of sorting
     */
    public SortCommand(String keyword) {
        super(false);
        this.keyword = keyword;
    }

    /**
     * Executes a sort command by sorting events in different ways,
     * depending on the keyword.
     */
    @Override
    public void execute() {
        StringBuilder outputMessage = new StringBuilder();
        EventList sortedEventList = new EventList(eventList);  // deep copy

        switch(keyword){
        case "name":
            sortedEventList.sortByName();
            outputMessage.append(SORT_BY_NAME_MESSAGE + "\n");
            break;
        case "time":
            sortedEventList.sortByTime();
            outputMessage.append(SORT_BY_TIME_MESSAGE + "\n");
            break;
        case "priority":
            sortedEventList.sortByPriority();
            outputMessage.append(SORT_BY_PRIORITY_MESSAGE + "\n");
            break;
        default:
        }

        // print out the sorted new list.
        for (int i = 0; i < sortedEventList.getListSize(); i++) {
            outputMessage.append(String.format("%d. %s\n", i + 1, sortedEventList.getEvent(i).toString()));
        }
        this.message = outputMessage.toString();
    }
}
