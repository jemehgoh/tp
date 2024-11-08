package seedu.manager.command;

import seedu.manager.exception.DuplicateDataException;

/**
 * Represents a command to edit an item for an event.
 * The edit command will store the event's name, the item to be edited.
 */
public class EditItemCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    private static final String EDIT_ITEM_MESSAGE = "Item successfully updated";
    private static final String EDIT_FAILURE_MESSAGE = "Event/Item not found!";
    protected String itemName;
    protected String itemNewName;
    protected String eventName;

    //@@author MatchaRRR
    /**
     * Constructs an EditItemCommand object with the specified event name and item name,
     *
     * @param itemName The name of the original item.
     * @param itemNewName The name of the new item.
     * @param eventName The name of the event associated with the participant.
     */
    public EditItemCommand(String itemName, String itemNewName, String eventName) {
        super(false);
        this.itemName = itemName;
        this.itemNewName = itemNewName;
        this.eventName = eventName;
    }

    @Override
    public void execute() throws DuplicateDataException {
        boolean isEdited = this.eventList.editItem(itemName, itemNewName, eventName);
        this.message = (isEdited) ? EDIT_ITEM_MESSAGE : EDIT_FAILURE_MESSAGE;
    }
}
