package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ListCommandTest {

    private ListCommand listCommand;

    @BeforeEach
    public void setUp() {
        EventList eventList = new EventList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        eventList.addEvent("Event 1", LocalDateTime.parse("2024-10-10 10:00", formatter),
                "Venue A", Priority.HIGH);
        eventList.addEvent("Event 2", LocalDateTime.parse("2024-11-11 12:00", formatter),
                "Venue B", Priority.MEDIUM);

        listCommand = new ListCommand();
        listCommand.setData(eventList);
        listCommand.execute();
    }

    @Test
    public void execute_twoEvents_success() {
        String expectedMessage = "There are 2 events in your list! Here are your scheduled events:\n"
                + "1. Event name: Event 1 / Event time: 2024-10-10 10:00 / Event venue: Venue A / " +
                "Event Priority: HIGH / Done: N\n"
                + "2. Event name: Event 2 / Event time: 2024-11-11 12:00 / Event venue: Venue B / " +
                "Event Priority: MEDIUM / Done: N\n";

        assertEquals(expectedMessage, listCommand.getMessage());
        assertFalse(listCommand.getCanExit());
    }
}
