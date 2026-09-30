# AirBorder User Guide

AirBorder is a Java command-line task manager. This guide describes its task commands; see the repository README for the current source status.

## Table of Contents

- [Getting Started](#getting-started)
- [Features](#features)
  - [Adding Tasks](#adding-tasks)
  - [Listing Tasks](#listing-tasks)
  - [Marking and Unmarking Tasks](#marking-and-unmarking-tasks)
  - [Deleting Tasks](#deleting-tasks)
  - [Finding Tasks](#finding-tasks)
  - [Exiting the Program](#exiting-the-program)
- [Command Summary](#command-summary)
- [FAQ](#faq)
- [Support](#support)

## Getting Started

To use AirBorder, ensure that you have Java 17 or newer installed on your computer. Download the latest version of AirBorder from the releases page, and run it using the following command in your terminal:

```bash
java -jar main.jar
```

## Features

### Adding Tasks

AirBorder supports three types of tasks:

- **ToDo**: Adds a task without a specific due date.
  ```
  todo Apply for ETA at gov.uk
  ```
- **Deadline**: Adds a task that needs to be completed by a specific date.
  ```
  deadline Check in closes by 2024-12-31 18:00
  ```
- **Event**: Adds an event with a start and end date.
  ```
  event Traveldoc validity from 2024-10-15 to 2034-10-14
  ```

### Listing Tasks

To view all your tasks in a list:
```
list
```

### Marking and Unmarking Tasks

To mark a task as completed:
```
mark 1
```

To mark the  task as not completed:
```
unmark 1
```

### Deleting Tasks

To remove a task from your list:
```
delete 1
```

### Finding Tasks

To find tasks containing specific keywords:
```
find ETA
```

### Exiting the Program

To exit AirBorder:
```
exit
```

## Command Summary

| Command                                       | Description                                    | Example                                        |
|-----------------------------------------------|------------------------------------------------|------------------------------------------------|
| `todo DESCRIPTION`                            | Adds a todo task                               | `Apply for ETA at gov.uk`                            |
| `deadline DESCRIPTION by DATE`               | Adds a task with a deadline                    | `deadline `      |
| `event DESCRIPTION from START_DATE to END_DATE` | Adds an event                                 | `Traveldoc validity from 2024-10-15 to 2034-10-14` |
| `list`                                        | Displays all tasks                             | `list`                                        |
| `mark INDEX`                                  | Marks the specified task as done               | `mark 1`                                      |
| `unmark INDEX`                                | Marks the specified task as not done           | `unmark 1`                                    |
| `delete INDEX`                                | Deletes the specified task                     | `delete 1`                                    |
| `find KEYWORD`                                | Finds tasks containing the keyword             | `find ETA`                                   |
| `exit`                                         | Exits the program                              | `exit`                                         |

## FAQ

**Q: Can AirBorder handle recurring tasks?**  
A: Currently, AirBorder does not support recurring tasks. Each task must be entered manually.

**Q: What date formats does AirBorder accept?**  
A: AirBorder accepts dates in YYYY-MM-DD format. Please ensure you adhere to this format to avoid any errors.

## Support

Use this repository’s issue tracker for project questions.
