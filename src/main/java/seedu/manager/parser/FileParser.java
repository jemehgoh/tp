package seedu.manager.parser;

import seedu.manager.enumeration.Priority;
import seedu.manager.event.EventList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a CSV file parser.
 */
//@@author KuanHsienn
public class FileParser {
    /**
     * Parses a CSV file containing event details and loads the events into the specified EventList.
     *
     * This method reads each line from the specified file, expecting the format to be:
     * <pre>
     * eventName, eventTime, eventVenue, eventPriority
     * </pre>
     * where:
     * - eventName is a String representing the name of the event.
     * - eventTime is a String formatted as "yyyy-MM-dd HH:mm" that will be parsed into a LocalDateTime object.
     * - eventVenue is a String representing the venue of the event.
     * - eventPriority is a String representing the priority level of the event.
     *
     * If a line does not contain exactly three parts, it is skipped.
     *
     * @param events The EventList where the parsed events will be added.
     * @param filePath The path to the file containing the event details.
     * @throws IOException If there is an error reading from the file or if the file cannot be found.
     */
    public void parseFile(EventList events, String filePath) throws IOException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (String line : Files.readAllLines(Paths.get(filePath))) {
                String[] parts = line.split(","); // CSV format
                if (parts.length == 4) {
                    String eventName = parts[0].trim();
                    LocalDateTime time = LocalDateTime.parse(parts[1].trim(), formatter);
                    String venue = parts[2].trim();
                    Priority priority = Priority.valueOf(parts[3].trim().toUpperCase());
                    events.addEvent(eventName, time, venue, priority);
                }
            }
        } catch (IOException exception) {
            throw new IOException("Error loading events from file: " + filePath + ".");
        }
    }
}
