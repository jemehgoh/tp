package seedu.manager.item;

import seedu.manager.event.Event;


/**
 * Represents an event participant, in the participant list of an {@link Event}.
 */
public class Participant extends Item {
    private String number;
    private String email;

    /**
     * Constructs a new Participant with a given name.
     *
     * @param name the given name.
     */
    public Participant(String name, String number, String email, boolean isPresent) {
        super(name, isPresent);
        this.number = number;
        this.email = email;
    }

    //@@author jemehgoh
    /**
     * Returns the name of the participant.
     *
     * @return the name of the participant.
     */
    public String getName() {
        return name;
    }

    //@@author KuanHsienn
    /**
     * Returns the contact number of the participant.
     *
     * @return the contact number of the participant.
     */
    public String getNumber() {
        return this.number;
    }

    //@@author KuanHsienn
    /**
     * Returns the contact email of the participant.
     *
     * @return the contact email of the participant.
     */
    public String getEmail() {
        return this.email;
    }

    //@@author KuanHsienn
    /**
     * Sets the details of the participant.
     *
     * @param newName the new name of the participant.
     * @param newNumber the new contact number of the participant.
     * @param newEmail the new contact email of the participant.
     */
    public void setDetails(String newName, String newNumber, String newEmail) {
        name = newName;
        number = newNumber;
        email = newEmail;
    }

    //@@author glenn-chew
    /**
     * Returns a formatted string of the participant.
     *
     * @return a formatted string of the participant.
     */
    @Override
    public String toString() {
        return String.format("Name: %s / Number: %s / Email: %s [%s]",
                this.name, this.number, this.email, this.markIfPresent());
    }
}
