package seedu.manager.command;

import seedu.manager.event.Event;
import seedu.manager.item.Participant;

import java.util.ArrayList;
import java.util.Optional;

//@author LTK-1606
/**
 * Represents a command to find all participants in an event with a certain name.
 */
public class FindCommand extends Command{
    public static final String COMMAND_WORD = "find";

    private static final String FIND_SUCCESS_MESSAGE = "Person(s) found!";
    private static final String FIND_EVENT_FAILURE_MESSAGE = "Event not found!";
    private static final String FIND_FAILURE_MESSAGE = "Person not found!";

    protected String personName;
    protected String eventName;
    protected Optional<Event> event;

    /**
     * Creates a {@code FindCommand} to find participants by event and person name.
     *
     * @param eventName the name of the event to search for participants
     * @param personName the name of the participant to search for within the event
     */
    public FindCommand(String eventName, String personName) {
        super(false);
        this.eventName = eventName;
        this.personName = personName;
    }

    /**
     * Executes the command to find participants in the specified event by the given person name.
     * <p>
     * This method retrieves the event corresponding to the provided {@code eventName} and
     * searches for participants with the specified {@code personName}. It builds an output message
     * containing either the list of found participants or an appropriate failure message if
     * the event is not found or no participants match the criteria.
     * </p>
     *
     * <p>
     * The output message is stored in the {@code message} field of the command for later retrieval.
     * </p>
     */
    @Override
    public void execute() {
        StringBuilder outputMessage = new StringBuilder();
        ArrayList<Participant> participantsFound;

        event = eventList.getEventByName(this.eventName);
        if (event.isPresent()) {
            participantsFound = event.get().findParticipants(personName);

            if (!participantsFound.isEmpty()) {
                outputMessage.append(FIND_SUCCESS_MESSAGE + "\n");

                // print out the list of people.
                for (int i = 0; i < participantsFound.size(); i++) {
                    outputMessage.append(String.format("%d. %s\n", i + 1, participantsFound.get(i).toString()));
                }

            } else {
                outputMessage.append(FIND_FAILURE_MESSAGE);
            }

        } else {
            outputMessage.append(FIND_EVENT_FAILURE_MESSAGE);
        }

        this.message = outputMessage.toString();
    }

}
