package seedu.manager.command;

import seedu.manager.exception.DuplicateDataException;

/**
 * Represents a command to edit an event to the event list.
 * The edit command will store the event's name, the participant's information.
 */
public class EditParticipantCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    private static final String EDIT_PARTICIPANT_MESSAGE = "Participant contact information successfully updated";
    private static final String EDIT_FAILURE_MESSAGE = "Event/Participant not found!";
    protected String eventName;
    protected String participantName;
    protected String newParticipantName;
    protected String participantNumber;
    protected String participantEmail;


    //@@author KuanHsienn
    /**
     * Constructs an EditParticipantCommand object with the specified participant name, participant number,
     * participant email, and event name.
     *
     * @param participantName The name of the participant.
     * @param participantNumber The new phone number of the participant.
     * @param participantEmail The new email address of the participant.
     * @param eventName The name of the event associated with the participant.
     */
    public EditParticipantCommand(String participantName, String newParticipantName, String participantNumber,
            String participantEmail, String eventName) {
        super(false);
        this.participantName = participantName;
        this.newParticipantName = newParticipantName;
        this.participantNumber = participantNumber;
        this.participantEmail = participantEmail;
        this.eventName = eventName;
    }


    /**
     * Executes the edit operation, updating the participant's contact information if the
     * participant is associated with the specified event. If successful, a confirmation message
     * is set; otherwise, an error message is set.
     */
    @Override
    public void execute() throws DuplicateDataException {
        if (participantName != null) {
            boolean isEdited = eventList.editParticipant(participantName, newParticipantName,
                    participantNumber, participantEmail, eventName);
            message = (isEdited) ? EDIT_PARTICIPANT_MESSAGE : EDIT_FAILURE_MESSAGE;
        }
    }
}
