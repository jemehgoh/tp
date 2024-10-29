package seedu.manager.item;

import seedu.manager.event.Event;


/**
 * Represents an event participant, in the participant list of an {@link Event}.
 */
public class Participant {
    private final String name;
    private String number;
    private String email;
    private boolean isPresent;

    /**
     * Constructs a new Participant with a given name
     *
     * @param name the given name
     */
    public Participant(String name, String number, String email) {
        this.name = name;
        this.isPresent = false;
        this.number = number;
        this.email = email;
    }

    //@@author jemehgoh
    /**
     * Returns the name of the participant.
     *
     * @return the name of the participant
     */
    public String getName() {
        return name;
    }

    //@@author KuanHsienn
    /**
     * Returns the contact number of the participant
     *
     * @return the contact number of the participant
     */
    public String getNumber() {
        return this.number;
    }

    //@@author KuanHsienn
    /**
     * Returns the contact email of the participant
     *
     * @return the contact email of the participant
     */
    public String getEmail() {
        return this.email;
    }

    //@@author KuanHsienn
    /**
     * Sets the contact number of the participant
     *
     * @param newNumber the new contact number of the participant
     */
    public void setNumber(String newNumber) {
        this.number = newNumber;
    }

    //@@author KuanHsienn
    /**
     * Sets the contact email of the participant
     *
     * @param newEmail the new contact email of the participant
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    //@@author jemehgoh
    /**
     * Sets the participant as present or not present.
     *
     * @param isPresent whether the participant is present or not present
     */
    public void setPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    //@@author jemehgoh
    /**
     * Returns "X" if isPresent is true, " " otherwise.
     *
     * @return "X" if isPresent is true, " " otherwise.
     */
    public String markIfPresent() {
        return (this.isPresent) ? "X" : " ";
    }

    //@@author jemehgoh
    /**
     * Returns a formatted string of the item
     *
     * @return a formatted string of the item
     */
    @Override
    public String toString() {
        return String.format("%s [%s]", this.name, this.markIfPresent());
    }
}
