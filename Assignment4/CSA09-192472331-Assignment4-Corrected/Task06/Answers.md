# Task 6 – Design Challenge: Campus Event Registration

## Stage 1 – Interpret the Problem
**Users:** students (register), committee members (view/filter/export), administrator (database setup).  
**Screens:** registration form, registrations table/filter view, confirmation/error dialogs.  
**Data:** student ID, student name, event ID/name, registration timestamp.  
**Rules:** required fields cannot be blank; duplicate student/event pairs are rejected; database operations must not freeze the GUI; filtering and exporting must show useful feedback.

Testable requirements:
1. Blank student ID/name/event must be rejected with a message.
2. A valid student/event pair must be stored.
3. A duplicate student/event pair must be rejected by both application logic and a database unique constraint.
4. The registrations table must display stored records.
5. Event filter must show only matching registrations.
6. Long database operations must run off the Swing Event Dispatch Thread.
7. Export must write visible registrations to a CSV file.

## Stage 2 – Propose Screen and Event Flow
```text
Main JFrame
├── JMenuBar: File (Export, Exit), View (Refresh)
├── Registration JPanel: Student ID, Student Name, Event, Register
└── JTable panel: Event filter, Filter button, registrations table
Dialogs: validation error, success confirmation, duplicate warning, export confirmation
```
Flow: Start → load initial table asynchronously → fill registration form → validate → insert → refresh table → choose event filter → refresh filtered table → export CSV / exit.

## Stage 3 – Critical Design Decisions
- Validate required fields in the UI for immediate feedback and again in the service layer.
- Enforce uniqueness in MySQL using `UNIQUE(student_id, event_id)`; application checks alone are not enough because two requests can race.
- Put SQL in a repository/data-access layer using `PreparedStatement`.
- Use `SwingWorker` for loading, registering, filtering and exporting so the Event Dispatch Thread remains responsive.
- Display errors with `JOptionPane` and status text.

## Stage 4 – Classes and Handler Pseudocode
Class layers:
- `EventRegistration` (Swing view/controller)
- `RegistrationService` (validation and business rules)
- `RegistrationRepository` (JDBC SQL)
- MySQL table `registrations`

Register pseudocode:
```text
ON Register click
    read studentId, studentName, eventId
    validate required fields
    IF invalid THEN show error; RETURN
    run SwingWorker
        service.register(studentId, studentName, eventId)
    on completion
        show success or duplicate/error
        refresh table
END EVENT
```
Filter pseudocode:
```text
ON Filter click
    eventId ← selected event filter
    run SwingWorker
        rows ← repository.findByEvent(eventId)
    on completion
        replace JTable model with rows
        show count/status
END EVENT
```

## Stage 5 – Implementation and Demonstration
`EventRegistration.java` is a Swing/JDBC starter implementation with a form, table, event filter, export menu, validation and background database work. Configure MySQL credentials and run `schema.sql`. Demonstrate: (1) valid registration, (2) blank/invalid input, (3) duplicate student/event registration. Capture your own screenshots.

## Alternative Concept and Complexity Analysis
Loading all 100,000 rows at once uses O(N) memory for N registrations and transfers all rows before the table is ready; it can make the UI sluggish if done on the EDT. Pagination with page size p loads O(p) rows into the UI model at a time, reducing visible model memory to O(p), while the database still stores all N rows. Each page query has database/index-dependent cost; with a suitable index it can retrieve a page efficiently. `SwingWorker` keeps loading off the EDT in either design, but pagination generally improves perceived responsiveness and limits memory use. Exporting all rows can still be done in a background worker.
