package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SortCommandTest {
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
    public void execute_sortByName_success() {

        SortCommand sortCommand = new SortCommand("name");

        sortCommand.setData(eventList);
        sortCommand.execute();

        String expectedMessage = "Events successfully sorted by name!\n" +
                "1. Event name: A-Event / Event time: 2023-10-23 21:00 / Event venue: Venue A /" +
                " Event Priority: LOW / Done: N\n" +
                "2. Event name: B-Event / Event time: 2024-10-23 21:05 / Event venue: Venue B /" +
                " Event Priority: MEDIUM / Done: N\n" +
                "3. Event name: C-Event / Event time: 2024-10-23 21:00 / Event venue: Venue C /" +
                " Event Priority: HIGH / Done: N\n";

        assertEquals(expectedMessage, sortCommand.getMessage());
        assertFalse(sortCommand.getCanExit());
    }

    @Test public void execute_sortByTime_success() {
        SortCommand sortCommand = new SortCommand("time");

        sortCommand.setData(eventList);
        sortCommand.execute();

        String expectedMessage = "Events successfully sorted by time!\n" +
                "1. Event name: A-Event / Event time: 2023-10-23 21:00 / Event venue: Venue A /" +
                " Event Priority: LOW / Done: N\n" +
                "2. Event name: C-Event / Event time: 2024-10-23 21:00 / Event venue: Venue C /" +
                " Event Priority: HIGH / Done: N\n" +
                "3. Event name: B-Event / Event time: 2024-10-23 21:05 / Event venue: Venue B /" +
                " Event Priority: MEDIUM / Done: N\n";
        assertEquals(expectedMessage, sortCommand.getMessage());
        assertFalse(sortCommand.getCanExit());
    }

    @Test public void execute_sortByPriority_success() {
        SortCommand sortCommand = new SortCommand("priority");

        sortCommand.setData(eventList);
        sortCommand.execute();

        String expectedMessage = "Events successfully sorted by priority level!\n" +
                "1. Event name: C-Event / Event time: 2024-10-23 21:00 / Event venue: Venue C /" +
                " Event Priority: HIGH / Done: N\n" +
                "2. Event name: B-Event / Event time: 2024-10-23 21:05 / Event venue: Venue B /" +
                " Event Priority: MEDIUM / Done: N\n" +
                "3. Event name: A-Event / Event time: 2023-10-23 21:00 / Event venue: Venue A /" +
                " Event Priority: LOW / Done: N\n";
        assertEquals(expectedMessage, sortCommand.getMessage());
        assertFalse(sortCommand.getCanExit());
    }

}
