package seedu.manager.command;

import seedu.manager.enumeration.Priority;
import seedu.manager.exception.DuplicateDataException;

import java.time.LocalDateTime;

/**
 * Represents a command to edit an event to the event list.
 * The edit command will store the event's name, time, and venue.
 */
public class EditEventCommand extends Command{
    public static final String COMMAND_WORD = "edit";
    private static final String EDIT_EVENT_MESSAGE = "Event information successfully updated";
    private static final String EDIT_FAILURE_MESSAGE = "Event not found!";
    protected String eventName;
    protected String eventNewName;
    protected LocalDateTime eventTime;
    protected String eventVenue;
    protected Priority eventPriority;


    //@@author MatchaRRR
    /**
     * Constructs an EditEventCommand object with the specified event name, event time, event venue and event priority.
     *
     * @param eventName The name of the event to be edited.
     * @param eventNewName The new name of the event.
     * @param eventTime The new time of the event.
     * @param eventVenue The new venue of the event.
     * @param eventPriority The new priority of the event.
     */
    public EditEventCommand(String eventName, String eventNewName, LocalDateTime eventTime, String eventVenue,
                            Priority eventPriority) {
        super(false);
        this.eventName = eventName;
        this.eventNewName = eventNewName;
        this.eventTime = eventTime;
        this.eventVenue = eventVenue;
        this.eventPriority = eventPriority;
    }


    /**
     * Executes the edit operation, updating the event's information if the event exits.
     * If successful, a confirmation message
     * is set; otherwise, an error message is set.
     *
     * @throws DuplicateDataException if an event with the new event name is already present in the list.
     */
    @Override
    public void execute() throws DuplicateDataException {
        boolean isEdited = this.eventList.editEvent(eventName, eventNewName, eventTime, eventVenue, eventPriority);
        this.message = (isEdited) ? EDIT_EVENT_MESSAGE : EDIT_FAILURE_MESSAGE;
    }
}
