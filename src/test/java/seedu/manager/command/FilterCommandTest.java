package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FilterCommandTest {
    EventList eventList = new EventList();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    public void setUp(){
        eventList.addEvent("C-Event", LocalDateTime.parse("2024-10-23 21:00", formatter),
                "Venue C", Priority.HIGH);
        eventList.addEvent("B-Event", LocalDateTime.parse("2024-10-23 21:05", formatter),
                "Venue B", Priority.MEDIUM);
        eventList.addEvent("A-Event", LocalDateTime.parse("2023-10-23 21:00", formatter),
                "Venue A", Priority.LOW);
        assertEquals(3, eventList.getListSize());
    }

    @Test
    public void execute_filterByPriority_success(){
        FilterCommand filterCommand = new FilterCommand("-u", "high");

        filterCommand.setData(eventList);
        filterCommand.execute();

        String expectedMessage = "Events successfully filtered by priority!\n" +
                "1. Event name: C-Event / Event time: 2024-10-23 21:00 / Event venue: Venue C /" +
                " Event Priority: HIGH / Done: N\n";

        assertEquals(expectedMessage, filterCommand.getMessage());
        assertFalse(filterCommand.getCanExit());
    }

}
