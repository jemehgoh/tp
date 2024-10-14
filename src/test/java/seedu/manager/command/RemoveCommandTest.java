package seedu.manager.command;

import org.junit.jupiter.api.Test;
import seedu.manager.event.EventList;
import seedu.manager.exception.ItemNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveCommandTest {

    @Test
    public void remove_oneParticipant_success() throws ItemNotFoundException {
        EventList eventList = new EventList();

        eventList.addEvent("Event 1", "2024-10-10 10:00", "Venue A");
        eventList.addParticipantToEvent("Tom", "Event 1");
        eventList.addParticipantToEvent("Harry", "Event 1");
        eventList.removeParticipantFromEvent("Tom", "Event 1");

        assertEquals(1, eventList.getEvent(0).getParticipantCount());
    }

    @Test
    public void remove_oneParticipantWrongly_throwException() throws ItemNotFoundException {
        EventList eventList = new EventList();

        eventList.addEvent("Event 1", "2024-10-10 10:00", "Venue A");
        eventList.addParticipantToEvent("Tom", "Event 1");
        eventList.addParticipantToEvent("Harry", "Event 1");

        assertThrows(ItemNotFoundException.class, () -> {eventList.removeParticipantFromEvent("Tom", "Event 2");});
    }

    @Test
    public void remove_oneEvent_success() throws ItemNotFoundException {
        EventList eventList = new EventList();

        eventList.addEvent("Event 1", "2024-10-10 10:00", "Venue A");
        eventList.addEvent("Event 2", "2024-10-31 21:00", "Venue B");
        eventList.removeEvent("Event 2");

        assertEquals(eventList.getListSize(), 1);
    }

    @Test
    public void remove_invalidEvent_throwsException() throws ItemNotFoundException {
        EventList eventList = new EventList();

        assertThrows(ItemNotFoundException.class, () -> {eventList.removeEvent("Event 2");});
    }
}
