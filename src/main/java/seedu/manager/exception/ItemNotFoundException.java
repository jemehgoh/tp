package seedu.manager.exception;

/**
 * Signals that the specified item was not found in a given list
 */
public class ItemNotFoundException extends Exception {
    /**
    * @param message relevant information on the type of item that was not found
    */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
