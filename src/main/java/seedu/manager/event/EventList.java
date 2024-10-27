package seedu.manager.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;


/**
 * The EventList class manages a list of Event objects.
 * It provides methods to manage an event list.
 */
public class EventList  {
    private final ArrayList<Event> eventList;

    /**
     * Default constructor that initializes an empty event list.
     */
    public EventList(){
        eventList = new ArrayList<>();
    }

    /**
     * Constructor that initializes EventList with a given list of event.
     *
     * @param otherEventList The initial list of tasks.
     */
    public EventList(EventList otherEventList) {
        this.eventList = new ArrayList<>(otherEventList.eventList);
    }

    /**
     * @return The size of the event list.
     */
    public int getListSize() {
        return eventList.size();
    }

    /**
     * @return The event list
     */
    public ArrayList<Event> getList() {
        return this.eventList;
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
    public void addEvent(String eventName, LocalDateTime time, String venue) {
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

    //@@author jemehgoh
    /**
     * Returns an event in the event list with a specified name.
     * Returns null if the event is not found.
     *
     * @param eventName The specified name
     * @return the event with a specified name, or null if the event is not found
     */
    public Optional<Event> getEventByName(String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }

    //@@author KuanHsienn
    /**
     * Removes an event from the event list by its name.
     *
     * @param eventName the name of the event to be removed.
     * @return {@code true} if the event was successfully removed;
     *         {@code false} if no event with the specified name was found.
     */
    public boolean removeEvent(String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                eventList.remove(event);
                return true;
            }
        }
        return false;
    }

    //@@author LTK-1606
    /**
     * Returns true if a participant can be added to a specified event,
     * returns false otherwise.
     *
     * @param participantName the name of the participant to be added.
     * @param eventName the name of the event to which the participant will be added.
     * @return {@code true} if the participant can be added to the event, {@code false} otherwise.
     */
    public boolean addParticipantToEvent(String participantName, String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                event.addParticipant(participantName);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a participant from a specified event.
     *
     * <p>
     * This method searches for the event with the given name in the event list and
     * attempts to remove the specified participant from that event. If the event is
     * found and the participant is successfully removed, it returns {@code true}.
     * If the event does not exist or the participant is not found, it returns
     * {@code false}.
     * </p>
     *
     * @param participantName the name of the participant to be removed from the event.
     * @param eventName      the name of the event from which the participant will be removed.
     * @return {@code true} if the participant was successfully removed;
     *         {@code false} if the event does not exist or the participant was not found.
     */
    public boolean removeParticipantFromEvent(String participantName, String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                return event.removeParticipant(participantName);
            }
        }
        return false;
    }

    /**
     * Sort the event list by name, alphabetically.
     */
    public void sortByName(){
        eventList.sort(Comparator.comparing(Event::getEventName));
    }

    /**
     *  Sort the event list by time in chronological order.
     */
    public void sortByTime() {
        eventList.sort(Comparator.comparing(Event::getEventTime));
    }
}
