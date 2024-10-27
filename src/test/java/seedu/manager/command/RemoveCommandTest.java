package seedu.manager.command;

import org.junit.jupiter.api.Test;
import seedu.manager.event.EventList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveCommandTest {

    @Test
    public void remove_event_success() {
        EventList eventList = new EventList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        eventList.addEvent("Event 1", LocalDateTime.parse("2024-10-20 21:00", formatter),
                "Venue A");
        eventList.addEvent("Event 2", LocalDateTime.parse("2024-10-20 21:00", formatter),
                "Venue B");
        eventList.removeEvent("Event 2");
        assertEquals(1, eventList.getListSize());
    }
    
    @Test
    public void remove_oneParticipant_success() {
        EventList eventList = new EventList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        eventList.addEvent("Event 1", LocalDateTime.parse("2024-10-10 10:00", formatter),
                "Venue A");
        eventList.addParticipantToEvent("Tom", "Event 1");
        eventList.addParticipantToEvent("Harry", "Event 1");
        eventList.removeParticipantFromEvent("Tom", "Event 1");

        assertEquals(1, eventList.getEvent(0).getParticipantCount());
    }

    @Test
    public void remove_oneParticipantWrongly_success() {
        EventList eventList = new EventList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        eventList.addEvent("Event 1", LocalDateTime.parse("2024-10-10 10:00", formatter),
                "Venue A");
        eventList.addParticipantToEvent("Tom", "Event 1");
        eventList.addParticipantToEvent("Harry", "Event 1");
        eventList.removeParticipantFromEvent("Tom", "Event 2");

        assertEquals(2, eventList.getEvent(0).getParticipantCount());
    }
}
