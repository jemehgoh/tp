package seedu.manager.event;

import seedu.manager.exception.ItemNotFoundException;

import java.util.ArrayList;


/**
 * The EventList class manages a list of Event objects.
 * It provides methods to manage an event list.
 */
public class EventList  {
    private static final String MISSING_EVENT_MESSAGE = "Event not found!";

    private final ArrayList<Event> eventList;

    /**
     * Constructor that initializes EventList with a given list of event.
     *
     * @param eventList The initial list of tasks.
     */
    public EventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * Default constructor that initializes an empty event list.
     */
    public EventList(){
        eventList = new ArrayList<>();
    }

    /**
     * @return The size of the event list.
     */
    public int getListSize() {
        return eventList.size();
    }

    /**
     * Adds a new event to the event list.
     *
     * <p>
     * This method creates a new {@link Event} object with the specified event name,
     * time, and venue, and adds it to the event list.
     * </p>
     *
     * @param eventName the name of the event to be added.
     * @param time      the time of the event.
     * @param venue     the venue where the event will take place.
     */
    public void addEvent(String eventName, String time, String venue) {
        Event newEvent = new Event(eventName, time, venue);
        eventList.add(newEvent);
    }

    /**
     * @param index The index of event in the list (0 based indexing)
     * @return The specific event in the event list.
     */
    public Event getEvent(int index) {
        return eventList.get(index);
    }

    /**
     * Returns an event in the event list with the specified name.
     * If the event is not in the list, throws a {@link ItemNotFoundException} with an error message
     *
     * @param eventName the name of the event
     * @return the event in the event list with the specified name
     * @throws ItemNotFoundException if the event is not in the event list
     */
    public Event getEventByName(String eventName) throws ItemNotFoundException {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                return event;
            }
        }

        throw new ItemNotFoundException(MISSING_EVENT_MESSAGE);
    }

    /**
     * Removes an event from the event list by its name.
     *
     * @param eventName the name of the event to be removed.
     * @throws ItemNotFoundException if no event with the specified name was found.
     */
    public void removeEvent(String eventName) throws ItemNotFoundException {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                eventList.remove(event);
                return;
            }
        }

        throw new ItemNotFoundException(MISSING_EVENT_MESSAGE);
    }

    /**
     * Adds a participant to an existing event.
     *
     * @param participantName the name of the participant to be added.
     * @param eventName the name of the event to which the participant will be added.
     */
    public void addParticipantToEvent(String participantName, String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                event.addParticipant(participantName);
                return;
            }
        }
    }

    /**
     * Removes a participant from a specified event.
     *
     * <p>
     * This method searches for the event with the given name in the event list and
     * attempts to remove the specified participant from that event. If the event is
     * found and the participant is successfully removed, it returns {@code true}.
     * If the event does not exist or the participant is not found, it throws an
     * {@link ItemNotFoundException}.
     * </p>
     *
     * @param participantName the name of the participant to be removed from the event.
     * @param eventName      the name of the event from which the participant will be removed.
     * @throws ItemNotFoundException if the event does not exist or the participant was not found.
     */
    public void removeParticipantFromEvent(String participantName, String eventName) throws ItemNotFoundException {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                event.removeParticipant(participantName);
                return;
            }
        }
        throw new ItemNotFoundException(MISSING_EVENT_MESSAGE);
    }
}
