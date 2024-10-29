package seedu.manager.storage;

import seedu.manager.event.EventList;
import seedu.manager.event.Event;
import seedu.manager.parser.FileParser;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

//@@author KuanHsienn
/**
 * Represents the storage component for saving and loading events.
 *
 */
public class Storage {
    private final String filePath;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        if (!isTestEnvironment()) {
            assert isValidFilePath(filePath) : "Invalid file path: " + filePath;
        }
        this.filePath = filePath;
    }

    /**
     * Saves the list of events to the file.
     *
     * @param events The EventList to be saved.
     * @throws IOException if there's an error writing to the file.
     */
    public void saveEvents(EventList events) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Event event : events.getList()) {
                String eventTimeString = formatter.format(event.getEventTime());
                writer.write(event.getEventName() + "," + eventTimeString + ","
                        + event.getEventVenue() + ","
                        + event.getEventPriority() + "\n"); // Save event details in CSV format
            }
        } catch (IOException exception) {
            throw new IOException("Error saving events to file: " + filePath);
        }
    }

    /**
     * Loads events from the file and returns an EventList.
     *
     * @throws IOException if there's an error reading from the file.
     */
    public void loadEvents(EventList events) throws IOException {
        FileParser parser = new FileParser();
        parser.parseFile(events, filePath);
    }

    /**
     * Checks if the given file path is valid and matches the expected path.
     *
     * @param filePath The path to check.
     * @return True if valid and matches, false otherwise.
     */
    private boolean isValidFilePath(String filePath) {
        String expectedPath = "events.txt"; // You can change this if needed
        return filePath.equals(expectedPath);
    }

    /**
     * Checks if the code is running in a test environment.
     *
     * @return True if in test environment, false otherwise.
     */
    private boolean isTestEnvironment() {
        return "true".equals(System.getProperty("test.environment"));
    }
}


