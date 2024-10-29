package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.event.EventList;
import seedu.manager.parser.ParserManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ViewCommandTest {

    private static EventList eventList = new EventList();
    private Command viewCommand;

    @BeforeEach
    public void setUp() {
        Command command;
        command = new ParserManager().parseCommand("add -e Event 1 -t 2024-10-10 10:00 -v Venue A -u high");
        command.setData(eventList);
        command.execute();

        command = new ParserManager().parseCommand("add -p Tom -n 95015025 -email example@gmail.com -e Event 1");
        command.setData(eventList);
        command.execute();

        viewCommand = new ParserManager().parseCommand("view -e Event 1");
        viewCommand.setData(eventList);
        viewCommand.execute();
    }

    @Test
    public void execute_twoEvents_success() {
        String expectedMessage = "There are 1 participants in Event 1! Here are your participants:\n"
                + "1. Tom [ ]\n";

        assertEquals(expectedMessage, viewCommand.getMessage());
        assertFalse(viewCommand.getCanExit());
    }
}
