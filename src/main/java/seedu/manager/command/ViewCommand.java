package seedu.manager.command;

import seedu.manager.event.Event;
import seedu.manager.exception.ItemNotFoundException;

/**
 * Represents a command to view the list of participants in an event.
 * The view command will search for an event by its name and display all its participants if found.
 */
public class ViewCommand extends Command{
    public static final String COMMAND_WORD = "view";
    private static final String VIEW_MESSAGE = "There are %d participants in %s! " +
            "Here are your participants:";
    protected String eventName;

    /**
     * Constructs an ViewCommand object with the for the specified event.
     *
     * @param eventName The name of the event to be viewed.
     */
    public ViewCommand(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Returns a command output with a list message
     *
     * @return The command output with a list message
     */
    public CommandOutput execute() {
        try {
            Event eventToView = eventList.getEventByName(this.eventName);

            StringBuilder outputMessage = new StringBuilder(
                    String.format(VIEW_MESSAGE, eventToView.getParticipantCount(), eventName) + "\n");
            int count = 1;
            for (String participant : eventToView.getParticipantList()) {
                outputMessage.append(String.format("%d. %s\n", count, participant));
                count++;
            }

            return new CommandOutput(outputMessage.toString(), false);
        } catch (ItemNotFoundException exception) {
            return new CommandOutput(exception.getMessage(), false);
        }
    }
}
