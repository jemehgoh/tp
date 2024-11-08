package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditParticipantCommandTest {
    private EventList eventList;
    private DateTimeFormatter formatter;

    @BeforeEach
    public void testSetUp() {
        eventList = new EventList();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    @Test
    public void edit_participant_success() {
        eventList.addEvent(
                "Event 1",
                LocalDateTime.parse("2024-10-20 21:00", formatter),
                "Venue A",
                Priority.HIGH
        );
        eventList.addParticipantToEvent(
                "Tom",
                "89521252",
                "example@gmail.com",
                "Event 1"
        );

        EditParticipantCommand editParticipantCommand = new EditParticipantCommand(
                "Tom",
                "Tom",
                "9123 4567",
                "new_email@example.com",
                "Event 1"
        );
        editParticipantCommand.setData(eventList);
        editParticipantCommand.execute();

        assertEquals("9123 4567", eventList.getEvent(0).getParticipantList().get(0).getNumber());
        assertEquals("new_email@example.com", eventList.getEvent(0).getParticipantList().get(0).getEmail());
    }

    @Test
    public void editParticipant_invalidEvent_failure() {
        eventList.addEvent(
                "Event 1",
                LocalDateTime.parse("2024-10-20 21:00", formatter),
                "Venue A",
                Priority.HIGH
        );
        eventList.addParticipantToEvent(
                "Tom",
                "89521252",
                "example@gmail.com",
                "Event 1"
        );
        String expectedMessage = "Event/Participant not found!";
        EditParticipantCommand editParticipantCommand = new EditParticipantCommand(
                "Tom",
                "Tom",
                "9123 4567",
                "new_email@example.com",
                "Non-Existent Event"
        );
        editParticipantCommand.setData(eventList);
        editParticipantCommand.execute();

        assertEquals(expectedMessage, editParticipantCommand.getMessage());
    }

    @Test
    public void editParticipant_invalidParticipant_failure() {
        eventList.addEvent(
                "Event 1",
                LocalDateTime.parse("2024-10-20 21:00", formatter),
                "Venue A", Priority.HIGH
        );
        eventList.addParticipantToEvent(
                "Tom",
                "89521252",
                "example@gmail.com",
                "Event 1"
        );
        String expectedMessage = "Event/Participant not found!";
        EditParticipantCommand editParticipantCommand = new EditParticipantCommand(
                "Invalid Participant",
                "Invalid Participant",
                "9123 4567",
                "new_email@example.com",
                "Event 1"
        );
        editParticipantCommand.setData(eventList);
        editParticipantCommand.execute();

        assertEquals(expectedMessage, editParticipantCommand.getMessage());
    }
}
