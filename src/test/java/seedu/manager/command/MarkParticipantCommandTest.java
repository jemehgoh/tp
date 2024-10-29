package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkParticipantCommandTest {
    private EventList eventList;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    public void testSetUp() {
        eventList = new EventList();
        eventList.addEvent("Event 1", LocalDateTime.parse("2024-10-10 16:00", formatter),
                           "Venue 1", Priority.HIGH);
        eventList.addParticipantToEvent(
                "John Doe",
                "89521252",
                "example@gmail.com",
                "Event 1"
        );
    }

    @Test
    public void execute_validParticipantMarkTrue_success() {
        String expectedMessage = "Participant marked present.";

        MarkParticipantCommand command = new MarkParticipantCommand(
                "John Doe",
                "Event 1",
                true);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }

    @Test
    public void execute_validParticipantMarkFalse_success() {
        String expectedMessage = "Participant marked absent.";

        MarkParticipantCommand command = new MarkParticipantCommand("John Doe",
                "Event 1", false);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }

    @Test
    public void execute_invalidParticipant_failure() {
        String expectedMessage = "Participant not found!";

        MarkParticipantCommand command = new MarkParticipantCommand("Jane Doe",
                "Event 1", true);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }

    @Test
    public void execute_invalidEvent_failure() {
        String expectedMessage = "Event not found!";

        MarkParticipantCommand command = new MarkParticipantCommand("Jane Doe",
                "Event 2", true);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }
}
