package seedu.manager;

import seedu.manager.command.Command;
import seedu.manager.event.EventList;
import seedu.manager.exception.InvalidCommandException;
import seedu.manager.parser.ParserManager;
import seedu.manager.ui.Ui;
import seedu.manager.storage.Storage;

import java.io.IOException;

public class Main {
    private static final Ui ui = new Ui();
    private static EventList events = new EventList();
    private static final String FILE_PATH = "events.txt";
    private static final Storage storage = new Storage(FILE_PATH);

    /**
     * Main entry-point for the EventManagerCLI application.
     */
    public static void main(String[] args) {
        ui.greetUser();
        loadData();
        runCommandLoop();
        saveData();
        System.exit(0);
    }

    //@@author MatchaRRR
    /**
     * Run command loop to get command from users
     * Parse the command and execute it
     * The loop ends when ExitCommand is triggered
     */
    private static void runCommandLoop() {
        Command command;
        boolean isGettingCommands = true;
        while (isGettingCommands){
            try {
                String userCommandText = ui.getCommand();
                command = new ParserManager().parseCommand(userCommandText);
                command.setData(events);
                command.execute();
                ui.showOutputToUser(command);

                isGettingCommands = !command.getCanExit();
            } catch (InvalidCommandException exception) {
                ui.showErrorMessageToUser(exception);
            }
        }
    }

    //@@author KuanHsienn
    /**
     * Loads events from file and handles exceptions.
     */
    private static void loadData() {
        try {
            storage.loadEvents(events);
            ui.showMessage("Events loaded successfully.");
        } catch (IOException exception) {
            ui.showErrorMessageToUser(exception);
        }
    }

    /**
     * Saves events to file and handles exceptions.
     */
    private static void saveData() {
        try {
            storage.saveEvents(events);
            ui.showMessage("Events saved successfully.");
        } catch (IOException e) {
            ui.showErrorMessageToUser(e);
        }
    }
}
