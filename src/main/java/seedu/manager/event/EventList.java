package seedu.manager.event;

import seedu.manager.enumeration.Priority;
import seedu.manager.exception.DuplicateDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

/**
 * The EventList class manages a list of Event objects.
 * It provides methods to manage an event list.
 */
public class EventList  {
    private static final String DUPLICATE_EVENT_MESSAGE = "Duplicate event!";

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
     * @param index The index of event in the list (0 based indexing)
     * @return The specific event in the event list.
     */
    public Event getEvent(int index) {
        return eventList.get(index);
    }

    /**
     * Adds an event to the event list.
     *
     * @param event the event to be added to the list
     */
    public void addEvent(Event event) {
        eventList.add(event);
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
     * @param priority the priority level of the event
     * @throws DuplicateDataException if an event with eventName is present in the event list.
     */
    public void addEvent(String eventName, LocalDateTime time,
            String venue, Priority priority) throws DuplicateDataException {
        addEvent(eventName, time, venue, priority, false);
    }

    /**
     * Adds a new {@code Event} with the given parameters and adds it to the event list.
     *
     * @param eventName the name of the event to be added.
     * @param time the time of the event.
     * @param venue the venue where the event will take place.
     * @param priority the priority level of the event.
     * @param isDone {@code true} if the event is marked done, {@code false otherwise}.
     * @throws DuplicateDataException if an event with eventName is present in the event list.
     */
    public void addEvent(String eventName, LocalDateTime time, String venue,
            Priority priority, boolean isDone) throws DuplicateDataException {
        if (getEventByName(eventName).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_EVENT_MESSAGE);
        }
        Event newEvent = new Event(eventName, time, venue, priority, isDone);
        eventList.add(newEvent);
    }

    //@@author LTK-1606
    /**
     * Returns true if a participant can be added to a specified event,
     * returns false otherwise.
     *
     * @param name the name of the participant to be added.
     * @param number the contact number of the participant
     * @param email the email address of the participant
     * @param eventName the name of the event to which the participant will be added.
     * @return {@code true} if the participant can be added to the event, {@code false} otherwise.
     */
    public boolean addParticipantToEvent(String name, String number, String email, String eventName) {
        return addParticipantToEvent(name, number, email, false, eventName);
    }

    /**
     * Returns true if a participant can be added to a specified event,
     * returns false otherwise.
     *
     * @param name the name of the participant to be added.
     * @param number the contact number of the participant.
     * @param email the email address of the participant.
     * @param isPresent {@code true} if the participant is to be marked present, {@code false} otherwise.
     * @param eventName the name of the event to which the participant will be added.
     * @return {@code true} if the participant can be added to the event, {@code false} otherwise.
     */
    public boolean addParticipantToEvent(String name, String number, String email, boolean isPresent,
                                         String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                event.addParticipant(name, number, email, isPresent);
                return true;
            }
        }
        return false;
    }

    //@@author jemehgoh
    /**
     * Returns true if an item with a given name is successfully added to a given event, returns false otherwise.
     *
     * @param itemName the name of the item to be added.
     * @param eventName the name of the event the item is to be added to.
     * @return {@code true} if the item is successfully added to the event, {@code false} otherwise.
     */
    public boolean addItemToEvent(String itemName, String eventName) {
        return addItemToEvent(itemName, false, eventName);
    }

    /**
     * Returns true if an item with a given name is successfully added to a given event, returns false otherwise.
     *
     * @param itemName the name of the item to be added.
     * @param eventName the name of the event the item is to be added to.
     * @return {@code true} if the item is successfully added to the event, {@code false} otherwise.
     */
    public boolean addItemToEvent(String itemName, boolean isPresent, String eventName) {
        assert itemName != null : "Item name should not be null";
        Optional<Event> event = getEventByName(eventName);
        if (event.isPresent()) {
            event.get().addItem(itemName, isPresent);
        }
        return event.isPresent();
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

    //@@author jemehgoh
    /**
     * Returns true if an item with a given name is successfully removed from an event, returns false otherwise.
     *
     * @param itemName the name of the item to be removed.
     * @param eventName the name of the event the item is to be removed from.
     * @return {@code true} if the item is successfully removed from the vent, {@code false} otherwise.
     */
    public boolean removeItemFromEvent(String itemName, String eventName) {
        assert itemName != null : "Item name should not be null";
        Optional<Event> event = getEventByName(eventName);
        if (event.isPresent()) {
            return event.get().removeItem(itemName);
        }
        return false;
    }

    //@@author MatchaRRR
    /**
     * Edits the details of an event in a specified event.
     *
     * <p>
     * This method searches for the event with the given name in the event list and update the event's information.
     * If the event is found and the participant is successfully updated, it returns {@code true}.
     * If the event does not exist, it returns {@code false}.
     * </p>
     *
     * @param eventName The name of the event to be edited.
     * @param newEventName The new name of the event.
     * @param eventTime The new time of the event.
     * @param eventVenue The new venue of the event.
     * @param eventPriority The new priority of the event.
     * @return {@code true} if the event was successfully edited;
     *         {@code false} if the event does not exist.
     * @throws DuplicateDataException if an event with name newEventName is already present in the event list.
     */
    public boolean editEvent(String eventName, String newEventName, LocalDateTime eventTime, String eventVenue,
            Priority eventPriority) throws DuplicateDataException {

        if (getEventByName(newEventName).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_EVENT_MESSAGE);
        }

        Optional<Event> eventToUpdate = getEventByName(eventName);
        if (eventToUpdate.isEmpty()) {
            return false;
        }

        eventToUpdate.get().updateEvent(newEventName, eventTime, eventVenue, eventPriority);
        return true;
    }

    //@@author KuanHsienn
    /**
     * Edits the details of a participant in a specified event.
     *
     * <p>
     * This method searches for the event with the given name in the event list and
     * attempts to update the specified participant's phone number and email. If the event
     * is found and the participant is successfully updated, it returns {@code true}.
     * If the event does not exist or the participant is not found, it returns
     * {@code false}.
     * </p>
     *
     * @param participantName the name of the participant to be edited.
     * @param number         the new contact number of the participant.
     * @param email          the new email address of the participant.
     * @param eventName      the name of the event associated with the participant.
     * @return {@code true} if the participant was successfully edited;
     *         {@code false} if the event does not exist or the participant was not found.
     */
    public boolean editParticipant(String participantName, String number, String email, String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                return event.updateParticipant(participantName, number, email);
            }
        }
        return false;
    }

    //@@author MatchaRRR
    /**
     * Edits the details of an event in a specified event.
     *
     * <p>
     * This method searches for the event with the given name in the event list and update the event's information.
     * If the event is found and the participant is successfully updated, it returns {@code true}.
     * If the event does not exist, it returns {@code false}.
     * </p>
     *
     * @param itemName The name of original item.
     * @param itemNewName The name of the new item.
     * @return {@code true} if the item was successfully edited;
     *         {@code false} if the item does not exist.
     */
    public boolean editItem(String itemName, String itemNewName, String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                return event.updateItem(itemName, itemNewName);
            }
        }
        return false;
    }

    //@@author LTK-1606
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

    /**
     *  Sort the event list by priority level from highest to lowest priority.
     */
    public void sortByPriority() {
        eventList.sort(Comparator.comparing(Event::getEventPriority));
    }
  
    /**
     * Filters events in the event list by the specified priority level.
     *
     * @param priority the priority level to filter events by
     * @return an {@code EventList} containing only events with the specified priority
     */
    public EventList filterByPriority(Priority priority) {
        EventList filteredList = new EventList();

        for (Event event : eventList) {
            if (event.getEventPriority() == priority) {
                filteredList.addEvent(event);
            }
        }

        return filteredList;
    }

    //@@author glenn-chew
    /**
     *  Filters events in the event list to display only events with names containing the keyword.
     *
     * @param keyword the keyword that the event is filtered by.
     * @return an {@code EventList} containing only events that the keyword can be found in the event name.
     */
    public EventList filterByName(String keyword) {
        EventList filteredList = new EventList();

        for (Event event : eventList) {
            if (event.getEventName().toLowerCase().contains(keyword)) {
                filteredList.addEvent(event);
            }
        }

        return filteredList;
    }

    //@@author glenn-chew
    /**
     *  Filters events in the event list to display only events that of the specified date.
     *
     * @param eventDate the date that the event is filtered by.
     * @return an {@code EventList} containing only events with of the specified date.
     */
    public EventList filterByDate(LocalDate eventDate) {
        EventList filteredList = new EventList();

        for (Event event : eventList) {
            if (event.getEventTime().toLocalDate().equals(eventDate)) {
                filteredList.addEvent(event);
            }
        }

        return filteredList;
    }

    //@@author glenn-chew
    /**
     *  Filters events in the event list to display only events that of the specified time.
     *
     * @param eventTime the date that the event is filtered by.
     * @return an {@code EventList} containing only events with of the specified time.
     */
    public EventList filterByTime(LocalTime eventTime) {
        EventList filteredList = new EventList();

        for (Event event : eventList) {
            if (event.getEventTime().toLocalTime().equals(eventTime)) {
                filteredList.addEvent(event);
            }
        }

        return filteredList;
    }

    //@@author glenn-chew
    /**
     *  Filters events in the event list to display only events that of the specified date-time.
     *
     * @param eventDateTime the date that the event is filtered by.
     * @return an {@code EventList} containing only events with of the specified date-time.
     */
    public EventList filterByDateTime(LocalDateTime eventDateTime) {
        EventList filteredList = new EventList();

        for (Event event : eventList) {
            if (event.getEventTime().equals(eventDateTime)) {
                filteredList.addEvent(event);
            }
        }

        return filteredList;
    }


}
