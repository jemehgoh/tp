package seedu.manager.exception;

//@@author jemehgoh
/**
 * Signals that an item being added to a list is a duplicate.
 */
public class DuplicateDataException extends RuntimeException {

    /**
     * @param message contains information on the type of duplicate item.
     */
    public DuplicateDataException(String message) {
        super(message);
    }
}
