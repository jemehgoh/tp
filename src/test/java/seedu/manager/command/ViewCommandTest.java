package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.event.EventList;
import seedu.manager.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ViewCommandTest {

    private static EventList eventList = new EventList();
    private Command viewCommand;

    @BeforeEach
    public void setUp() {
        Command command;

        command = new Parser().parseCommand("add -e Event 1 -t 2024-10-10 10:00 -v Venue A");
        command.setData(eventList);
        command.execute();

        command = new Parser().parseCommand("add -p Tom -e Event 1");
        command.setData(eventList);
        command.execute();

        viewCommand = new Parser().parseCommand("view -e Event 1");
        viewCommand.setData(eventList);
    }

    @Test
    public void execute_twoEvents_success() {
        CommandOutput result = viewCommand.execute();

        String expectedMessage = "There are 1 participants in Event 1! Here are your participants:\n"
                + "1. Tom\n";

        assertEquals(expectedMessage, result.getMessage());
        assertFalse(result.getCanExit());
    }
}
