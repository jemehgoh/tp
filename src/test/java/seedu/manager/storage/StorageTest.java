package seedu.manager.storage;

import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;
import seedu.manager.event.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class StorageTest {
    private static final String TEST_FILE_PATH = "test_events.txt";
    private static final String NON_EXISTENT_FILE_PATH = "non_existent_file.txt";
    private Storage storage;
    private EventList eventList;

    @BeforeEach
    public void setUp() {
        System.setProperty("test.environment", "true"); // Set the system property for testing
        storage = new Storage(TEST_FILE_PATH);
        eventList = new EventList();
    }

    @AfterEach
    public void tearDown() {
        File file = new File(TEST_FILE_PATH);
        // Clean up the test file after each test
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testLoadEventsFileNotExists() {
        Storage nonExistentFileStorage = new Storage(NON_EXISTENT_FILE_PATH);

        IOException exception = assertThrows(IOException.class, () -> {
            nonExistentFileStorage.loadEvents(eventList);
        });

        assertEquals("Error loading events from file: " + NON_EXISTENT_FILE_PATH + ".", exception.getMessage());
    }

    @Test
    public void testLoadEvents() {
        try {
            java.nio.file.Files.writeString(
                    java.nio.file.Paths.get(TEST_FILE_PATH),
                    "Meeting,2024-10-25 10:00,Conference Room,HIGH\n" +
                            "Workshop,2024-10-26 14:00,Main Hall,MEDIUM\n"
            );
        } catch (IOException exception) {
            fail("Failed to set up the test data file: " + exception.getMessage());
        }

        try {
            storage.loadEvents(eventList);
        } catch (IOException exception) {
            fail("Exception should not be thrown when loading events: " + exception.getMessage());
        }

        assertEquals(2, eventList.getList().size());

        Event firstEvent = eventList.getList().get(0);
        assertEquals("Meeting", firstEvent.getEventName());
        assertEquals("2024-10-25 10:00", firstEvent.getEventTimeString());
        assertEquals("Conference Room", firstEvent.getEventVenue());

        Event secondEvent = eventList.getList().get(1);
        assertEquals("Workshop", secondEvent.getEventName());
        assertEquals("2024-10-26 14:00", secondEvent.getEventTimeString());
        assertEquals("Main Hall", secondEvent.getEventVenue());
    }

    @Test
    public void testSaveEvents() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        eventList.addEvent("Meeting", LocalDateTime.parse("2024-10-25 10:00", formatter),
                "Conference Room", Priority.HIGH);
        eventList.addEvent("Workshop", LocalDateTime.parse("2024-10-26 14:00", formatter),
                "Main Hall", Priority.MEDIUM);

        try {
            storage.saveEvents(eventList);
        } catch (IOException exception) {
            fail("Exception should not be thrown when saving events: " + exception.getMessage());
        }

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());

        try {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            String expectedContent = "Meeting,2024-10-25 10:00,Conference Room,HIGH\n"
                    + "Workshop,2024-10-26 14:00,Main Hall,MEDIUM\n";
            assertEquals(expectedContent, content, "The file content does not match the expected output.");
        } catch (IOException exception) {
            fail("Exception should not be thrown when reading the saved file: " + exception.getMessage());
        }
    }


}


