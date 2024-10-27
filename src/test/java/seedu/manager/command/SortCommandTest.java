package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
                "Venue C");
        eventList.addEvent("B-Event", LocalDateTime.parse("2024-10-23 21:05", formatter),
                "Venue B");
        eventList.addEvent("A-Event", LocalDateTime.parse("2023-10-23 21:00", formatter),
                "Venue A");
        assertEquals(3, eventList.getListSize());
    }

    @Test
    public void execute_sortByName_success() {

        SortCommand sortCommand = new SortCommand("name");

        sortCommand.setData(eventList);
        sortCommand.execute();

        String expectedMessage = """
                Events successfully sorted by name!
                1. Event name: A-Event / Event time: 2023-10-23 21:00 / Event venue: Venue A / Done: N
                2. Event name: B-Event / Event time: 2024-10-23 21:05 / Event venue: Venue B / Done: N
                3. Event name: C-Event / Event time: 2024-10-23 21:00 / Event venue: Venue C / Done: N
                """;

        assertEquals(expectedMessage, sortCommand.getMessage());
        assertFalse(sortCommand.getCanExit());
    }

    @Test public void execute_sortByTime_success() {
        SortCommand sortCommand = new SortCommand("time");

        sortCommand.setData(eventList);
        sortCommand.execute();

        String expectedMessage = """
                Events successfully sorted by time!
                1. Event name: A-Event / Event time: 2023-10-23 21:00 / Event venue: Venue A / Done: N
                2. Event name: C-Event / Event time: 2024-10-23 21:00 / Event venue: Venue C / Done: N
                3. Event name: B-Event / Event time: 2024-10-23 21:05 / Event venue: Venue B / Done: N
                """;
        assertEquals(expectedMessage, sortCommand.getMessage());
        assertFalse(sortCommand.getCanExit());
    }

}
