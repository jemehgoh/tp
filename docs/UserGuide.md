# User Guide

## Introduction

EventManager CLI is a CLI application that allows organisers of small-scale events to track
the participants and logistics of such events.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `EventManagerCLI` from [here](https://github.com/AY2425S1-CS2113-W13-3/tp/releases).
3. Open a new terminal in the folder that you put the JAR file in, and run the program with the command ```java -jar EventManagerCLI.jar```.
The following message would be printed:
```
Welcome to EventManagerCLI.
Enter a command:
```

## Features

### Notes about the command format:

* Words in `UPPER_CASE` represent parameters that are to be supplied by the user.
* Parameters listed have to be entered in the specified order.
* Extraneous parameters for commands that do not take in parameters (e.g. `list`) will be ignored.

### Viewing the command list: `menu`

Shows a list of all valid user commands in the program.

```
Here are the possible commands:

add -e EVENT -t TIME -v VENUE: Add an event to the event list.
list: List events.
remove -e EVENT: Remove an event from the event list.
add -p PARTICIPANT -e EVENT: Add a participant to an event.
view -e EVENT: View the list of participants of an event.
remove -p PARTICIPANT -e EVENT: Remove a participant from an event.
copy FROM_EVENT > TO_EVENT: Copies participant list from one event to another.
find -e EVENT -p NAME: Finds all participants with specified name in an event.
filter -e/-t/-u FILTER_DESCRIPTION: Filters events by name/time/priority.
sort -by KEYWORD: Sorts events by name/time/priority.
save: Save latest changes to file
exit: Exit program
```

Format: `menu`

### List all events: `list`

Shows a list of all events currently stored in the program.

Format: `list`

### Add an event or participant: `add`

Adds an event to the event list, or a participant to an event.

Format:  

* `add -e EVENT -t TIME -v VENUE` for adding an event to the events list.
* `add -p PARTICIPANT -n NUMBER -email EMAIL -e EVENT` for adding a participant to an event.

Examples:

* `add -e Origami workshop -t Mon 1600-1800 -v Building A` adds an event with name `Origami workshop`, time `Mon 1600-1800` and venue `Building A` to the events list.
* `add -p John Tan -n 91583215 -email john@gmail.com -e Origami workshop` adds a participant `John Tan` to the event `Origami workshop`.

### Remove an event or participant: `remove` 

Removes an event from the event list, or a participant from an event.

Format:

* `remove -e EVENT` for removing an event from the event list.
* `remove -p PARTICIPANT -e EVENT` for removing a participant from an event.

Examples:

* `remove -e Origami workshop` removes the event `Origami workshop` from the event list.
* `remove -p John Tan -e Origami workshop` removes the participant `John Tan` from the event `Origami workshop`.

### View all participants for an event: `view`

Shows a list of all participants for an event.

Format: `view -e EVENT`

Examples:

* `view -e Origami workshop` shows a list of all participants for the event `Origami workshop`.

### Mark an event as done: `mark`

Marks an event in the event list as done or not done.

Format: `mark -e EVENT -s STATUS`

* The status parameter must be either `done` (to mark done) or `undone` (to mark not done).

Examples:

* `mark -e Origami workshop -s done` marks the event `Origami workshop` as done.
* `mark -e Origami workshop -s undone` marks the event `Origami workshop` as not done.

### Marks a participant as present: `mark`

Marks a participant for an event as present or absent.

Format: `mark -p PARTICIPANT -e EVENT -s STATUS`

* The status parameter must be either `present` (to mark present) or `absent` (to mark absent).

Examples:

* `mark -p John Tan -e Origami workshop -s done` marks the participant `John Tan` in the `Origami workshop` event as present.
* `mark -p John Tan -e Origami workshop -s undone` marks the participant `John Tan` in the `Origami workshop` event as absent.

### Copies participant list: `copy`

Copies the participant list from one event to another event.

Format: `copy FROM_EVENT > TO_EVENT`

* Both events must already exist

Examples:

* `copy Origami workshop > Coding workshop` copies the participant list from the `Origami workshop` event to the `Coding workshop` event.

### Find participant: `find`

Finds all participants with a specified name in a specified event.

Format: `find -e EVENT -p NAME`

* The `NAME` keywords are case-insensitive.

Examples:
* `find -e Origami workshop -p john` will output all participants with `john` in their name in the event `Origami workshop`.

### Filters event list: `filter`

Filters out events from the event list based on name, date-time or priority level.

Format: `filter -e/-t/-u DESCRIPTION`

* `-e/-t/-u` are the flags for name, date-time and priority level respectively.
* `DESCRIPTION` is only case-insensitive when filtering using the priority level flag.

Examples:
* `filter -e workshop` will output all events with `workshop` in their event name.
* `filter -t 2024` will output all events that are occurring in `2024`.
* `filter -u high` with output all events with priority level `HIGH`.

### Sorts event list: `sort`

Sorts events according to event name, event date-time or event priority level.

Format: `sort -by KEYWORD`

* `KEYWORD` is case-insensitive, but must be one of these inputs: `name/time/priority`

Examples:
* `sort -by name` will output a list of all the user's events alphabetically (from A to Z).
* `sort -by time` will output a list of all the user's events chronologically (earliest to latest).
* `sort -by priority` will output a list of all the user's events in terms of urgency (most to least important).

### Saving the program: `save`

Save the program

Format: `save`

### Exiting the program: `exit`

Exits the program.

Format: `exit`

## Command Summary

* View all commands: `view`
* List all events: `list`
* Add event: `add -e EVENT -t TIME -v VENUE`
* Add participant to an event: `add -p PARTICIPANT -e EVENT`
* Remove event: `remove -e EVENT`
* Remove participant from an event: `remove -p PARTICIPANT -e EVENT`
* View all participants for an event: `view -e EVENT`
* Mark an event as done: `mark -e EVENT -s STATUS`
* Mark a participant as present: `mark -p PARTICIPANT -e EVENT -s STATUS`
* Copy participant list: `copy FROM_EVENT > TO_EVENT`
* Find participants: `find -e EVENT -p NAME`
* Filter events: `filter -e/-t/-u FILTER_DESCRIPTION`
* Sort events: `sort -by KEYWORD`
* Save program: `save`
* Exit program: `exit`
