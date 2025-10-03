# Finch Chatbot User Guide

Welcome to **Finch**, your personal task management chatbot! Finch helps you create, track, and organize your tasks in a simple and interactive way.

---

## Features

Finch supports the following types of tasks:

1. **ToDo**
    - Simple tasks without a date/time.
    - Example: `todo Read book`

2. **Deadline**
    - Tasks with a due date and time.
    - Example: `deadline Submit report /by 2025-09-27 18:00`
    - Date format: `yyyy-MM-dd HH:mm`

3. **Event**
    - Tasks with a start and end date/time.
    - Example: `event Team meeting /from 2025-09-28 14:00 /to 2025-09-28 15:30`
    - Start and end date format: `yyyy-MM-dd HH:mm`

4. **Mark & Unmark**
    - Mark a task as done: `mark 2`
    - Unmark a task as not done: `unmark 2`

5. **Delete**
    - Remove a task from your list: `delete 3`

6. **Find**
    - Search for tasks by keyword: `find book`
    - Returns all tasks whose description contains the keyword.

7. **List**
    - View all tasks in your list: `list`

8. **Exit**
    - Quit Finch: `bye`

---

## Getting Started

1. Run the Finch application (e.g., via `java -jar Finch.jar`).
2. Finch will display a welcome message and prompt you for input.
3. Enter commands following the syntax above.
4. Finch will confirm your actions and show the current task list.
5. Repeat as needed. Use `bye` to exit.

---

## Command Summary

| Command | Description | Example |
|---------|-------------|---------|
| `todo <desc>` | Add a ToDo task | `todo Read book` |
| `deadline <desc> /by <yyyy-MM-dd HH:mm>` | Add a Deadline task | `deadline Submit report /by 2025-09-27 18:00` |
| `event <desc> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>` | Add an Event task | `event Team meeting /from 2025-09-28 14:00 /to 2025-09-28 15:30` |
| `list` | List all tasks | `list` |
| `mark <number>` | Mark task as done | `mark 2` |
| `unmark <number>` | Mark task as not done | `unmark 2` |
| `delete <number>` | Delete a task | `delete 3` |
| `find <keyword>` | Search tasks | `find book` |
| `bye` | Exit Finch | `bye` |

---

## Notes

- Finch saves tasks automatically to a local file so your data persists between sessions.
- Always use the correct date/time format for deadlines and events (`yyyy-MM-dd HH:mm`).
- If a command is entered incorrectly, Finch will display an error message with guidance.

---

Finch makes task management simple, interactive, and easy to keep track of your deadlines and events!
