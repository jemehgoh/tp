package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;
import seedu.manager.exception.DuplicateDataException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddCommandTest {
    //@@author jemehgoh
    private EventList eventList;
    private DateTimeFormatter formatter;

    @BeforeEach
    public void testSetUp() {
        eventList = new EventList();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    //@@author
    @Test
    public void add_event_success() {
        eventList.addEvent("Event 1",
                LocalDateTime.parse("2024-10-20 21:00", formatter),
                "Venue A", Priority.HIGH);
        assertEquals(1, eventList.getListSize());
    }

    @Test
    public void add_twoParticipant_success() {
        eventList.addEvent("Event 1",
                LocalDateTime.parse("2024-10-10 10:00", formatter),
                "Venue A", Priority.HIGH);
        eventList.addParticipantToEvent(
                "Tom",
                "89521252",
                "example@gmail.com",
                "Event 1"
        );
        eventList.addParticipantToEvent(
                "Harry",
                "89521252",
                "example@gmail.com",
                "Event 1"
        );


        assertEquals(2, eventList.getEvent(0).getParticipantCount());
    }

    @Test
    public void add_oneParticipantWrongly_success() {
        eventList.addEvent("Event 1", LocalDateTime.parse("2024-10-10 10:00", formatter),
                "Venue A", Priority.HIGH);
        eventList.addParticipantToEvent(
                "Tom",
                "89521252",
                "example@gmail.com",
                "Event 1"
        );
        eventList.addParticipantToEvent(
                "Harry",
                "89521252",
                "example@gmail.com",
                "Event 2"
        );

        assertEquals(1, eventList.getEvent(0).getParticipantCount());
    }

    @Test
    public void add_oneParticipantInvalidEvent_failure() {
        String expectedMessage = "Event not found!";

        AddCommand addCommand = new AddCommand("Tom","89521252", "example@gmail.com", "Event 1");
        addCommand.setData(eventList);
        addCommand.execute();

        assertEquals(expectedMessage, addCommand.getMessage());
    }

    //@@author jemehgoh
    @Test
    public void add_duplicateEvent_throwsException() {
        EventList eventList = new EventList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        eventList.addEvent("Event 1", LocalDateTime.parse("2024-10-10 10:00", formatter),
                "Venue A", Priority.HIGH);

        AddCommand addCommand = new AddCommand("Event 1", LocalDateTime.parse("2024-10-10 10:00",
                formatter), "Venue A", Priority.HIGH);
        addCommand.setData(eventList);

        assertThrows(DuplicateDataException.class, addCommand::execute);
    }

    @Test
    public void add_duplicateParticipant_throwsException() {
        EventList eventList = new EventList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        eventList.addEvent("Event 1", LocalDateTime.parse("2024-10-10 10:00", formatter),
                "Venue A", Priority.HIGH);
        eventList.addParticipantToEvent("John", "9123 8321", "example1@gmail.com", "Event 1");

        AddCommand addCommand = new AddCommand("Event 1", LocalDateTime.parse("2024-10-10 10:00",
                formatter), "Venue A", Priority.HIGH);
        addCommand.setData(eventList);

        assertThrows(DuplicateDataException.class, addCommand::execute);
    }
}

