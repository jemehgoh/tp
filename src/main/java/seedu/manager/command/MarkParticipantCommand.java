package seedu.manager.command;

import seedu.manager.event.Event;

import java.util.Optional;

//@@author jemehgoh
/**
 * Represents an executable mark command for marking participants.
 */
public class MarkParticipantCommand extends MarkCommand {
    private static final String PARTICIPANT_MARK_MESSAGE = "Participant marked present.";
    private static final String PARTICIPANT_UNMARK_MESSAGE = "Participant marked absent.";
    private static final String INVALID_PARTICIPANT_MESSAGE = "Participant not found!";

    private final String participantName;

    /**
     * Constructs a new MarkParticipantCommand with a given participant name, event name and whether to mark
     * or unmark the participant.
     *
     * @param participantName the name of the participant.
     * @param eventName the name of the event.
     * @param toMark true if the participant is to be marked present, false if he is to be marked absent.
     */
    public MarkParticipantCommand(String participantName, String eventName, boolean toMark) {
        super(eventName, toMark);
        this.participantName = participantName;
    }

    /**
     * Executes the mark participant command, by marking the participant as present or absent
     */
    @Override
    public void execute() {
        Optional<Event> event = eventList.getEventByName(eventName);

        if (event.isEmpty()) {
            message = INVALID_EVENT_MESSAGE;
            return;
        }

        boolean isMarked = event.get().markParticipantByName(participantName, toMark);

        if (isMarked) {
            message = (toMark) ? PARTICIPANT_MARK_MESSAGE : PARTICIPANT_UNMARK_MESSAGE;
        } else {
            message = INVALID_PARTICIPANT_MESSAGE;
        }
    }
}
