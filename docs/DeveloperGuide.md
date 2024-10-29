# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design

<img src = "images/ArchitectureDiagram.png">

The above **Architecture Diagram** provides the high-level design of the application.

### Main components

The application comprises the following components:

* `Main`, which handles program startup and shutdown, and also interactions between other components.
* `UI`, which handles user input and showing messages to the user.
* `Storage`, which handles the loading and saving of data upon program startup and shutdown.
* `Parser`, which converts user input into commands.
* `Command`, which are executed to modify the data stored in the program.
* `Event`, which stores the program's data.

### Interactions between components

<img src = "images/ArchitectureSequenceDiagram.png">

The above **Sequence Diagram** shows how the different components of the system interact with one
another in the scenario when the command `add -e event -t 1200 -v venue` is executed.

### UI component

<img src = "images/UiClassDiagram.png">

The `UI` component comprises an Ui class, as shown in the above **Class Diagram**.

The `UI` does the following:

* Take in command input from the user and pass the input to `Main`.
* Show output messages from `Command` to the user after command execution.

### Storage component

<img src = "images/StorageClassDiagram.png">

The `Storage` component comprises a Storage class, as shown in above **Class Diagram**.

The `Storage` does the following:

* Load events and participants information from a text file and save it to `EventList` list.
* Save events from `EventList` list to a text file

### Command component

The `Command` component and its component classes are shown in the below **Class Diagram**:

<img src = "images/CommandClassDiagram.png">

The `Command` component does the following:

* Handle the execution of the user command through interactions with `Event`.
* Provides a command output message to `Ui` after the command execution.

The interactions between `Command` and other commands in the system is shown in the following _Sequence Diagram_:

<img src = "images/CommandSequenceDiagram.png">

### Event component

The `Event` component and its component classes are shown in the below **Class Diagram**:

<img src = "images/EventClassDiagram.png">

The `Event` component does the following:
* Handle the addition, removal and marking of events stored in `EventList`.
* Add, remove and mark participants for a specific `Event` in `EventList`.

### Parser component

The component classes and dependent components of the `Parser` are as shown in the _Class Diagram_ below:

<img src = "images/ParserClassDiagram.png">

The logic of the `Parser` is as follows:

1. Upon receiving a user command, `Main` creates a `ParserManager`.
2. The `ParserManager`, based on the first word of the user command, does the following:

   * If the command has no parameters, it creates an `XYZCommand` associated with the word.
   * If the command has parameters, it creates an `XYZCommandParser` associated with the word.

3. If an `XYZCommandParser` is created, it parses the command input based on the command flags present, and creates an `XYZCommand` with the relevant parameters.
4. The `ParserManager` passes the creates `XYZCommand` back to `Main` for execution.

The `ParameterFlags` class acts as a storage class for the command flags used by `XYZCommandParser`.

## Implementation

This section describes some noteworthy details on how certain features are implemented.

### List feature[TBD]

The `list` feature allows users to view all scheduled events in the system. 
It is implemented in the `ListCommand` class, which extends the base `Command` class and formats the output to display all events. 
Internally, the `list` operation does not modify any data but simply retrieves and displays the information from `EventList`.

The `ListCommand` class performs the following key operations:

* ListCommand#execute() — Generates a formatted message displaying all events in the list.

These operations are accessible through the `Command` and can be invoked when the list command is entered by the user.

Given below is an example usage scenario and the behavior of the list feature at each step:

1. User Command Input:
The user enters the command `list` to view all scheduled events.

2. Command Recognition:
The `COMMAND_WORD` is set to "list", enabling the system to recognize the command input and invoke `ListCommand`.

3. Execution of ListCommand#execute():
The `execute()` method retrieves each event from `eventList` and appends it to a formatted output message.
It uses the `String.format` method with `LIST_MESSAGE` to include the total number of events in the message header.
Events are appended to `outputMessage` with numbered formatting for readability.

4. Output Generation:
The method stores the generated `outputMessage` in `this.message`, ready for display.

### Mark/unmark feature

The mark/unmark feature allows users to mark events as done or not done. The feature comprises `MarkEventCommand`, which 
extends `Command`. This class performs one operation, which marks a specified event as done or not done.

The above operation is implemented as `MarkEventCommand#execute()`. This overrides the `Command#execute()` operation in `Command`,
and is invoked when the latter operation is called.

#### Feature implementation

Given below is an example usage scenario for the mark/unmark mechanism, and how it behaves at each step.

1. The user adds an event `Event 1` to the event list. The mark status for `Event 1` is initially `false` or not done.

2. The user enters the command `mark -e Event 1 -s done` to mark `Event 1` as done. `MarkEventCommand` calls `MarkEventCommand#execute`,
in which it gets the event `Event 1` from the event list, and sets its mark status to `true` or done.

3. The user then enters the command `mark -e Event 1 -s undone` to mark `Event 1` as not done. The `MarkEventCommand` again calls `MarkEventCommand#execute`,
in which it gets the event `Event 1` from the event list, and sets its mark status to `false` or not done.

The interactions between components during the execution of the `mark` command are show in the **Sequence Diagram** below:

<img src = "images/MarkEventSequenceDiagram.png">

Upon execution of the command, the output message of `MarkEventCommand` is set to inform the user if the event has been marked done or not done,
or if the operation was unsuccessful (e.g. if the event specified is not present in the event list).

The user determines if an event is to be marked done or not done through the status parameter (indicated by the `-s` flag) in the `mark` command.
The `Parser` then checks this parameter for two values, `done` or `undone`, and constructs the `MarkEventCommand` accordingly.
If the parameter value is `done`, the `MarkEventCommand` will set the event as done, and will do otherwise if the parameter value is `undone`.
Any other values entered for the status parameter will be treated as invalid.

## Product scope
### Target user profile

The target user:

* has a need to organise a large number of events
* organises small-scale events, such that he is able to handle all matters on his own
* prefers typing to mouse interactions
* is comfortable using a command-line interface

### Value proposition

The user is able to organise and manage his events more quickly and efficiently than with a mouse/GUI app

## User Stories

| Version | As a ... | I want to ...                                                   | So that I can ...                                                                         |
|---------|----------|-----------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| v1.0    | new user | see the list of commands                                        | know how to format my input                                                               |
| v1.0    | user     | add new events                                                  | manage future events                                                                      |
| v1.0    | user     | remove events from the events list                              | maintain the event list with events that are relevant and current                         |
| v1.0    | user     | list all current events                                         | know which events are currently ongoing                                                   |
| v1.0    | user     | add participants to a specific event                            | ensure all relevant individuals are included in that event's participant list efficiently |
| v1.0    | user     | remove participants who are no longer coming to specific events | efficiently keep the participant list for that event up-to-date and relevant              |
| v1.0    | user     | view the participant list of an event                           | know who is involved                                                                      |
| v2.0    | user     | mark events as completed                                        | easily track all past events                                                              |
| v2.0    | user     | mark participants present                                       | know exactly who signed up but did not attend the event                                   |
| v2.0    | user     | save events info                                                | can still access the information if the program terminates                                |

## Non-Functional Requirements

* Should work for any **mainstream OS** as long as Java 17 is installed.

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
