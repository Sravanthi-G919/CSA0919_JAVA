# Task 2 – From Requirement to Event-Driven Design

## Task 2.1 – Model the Events
| Event source | Event object | Listener | Action |
|---|---|---|---|
| Department combo box | `ItemEvent` / `ActionEvent` | `ItemListener` / `ActionListener` | Refresh doctor list for selected department |
| Doctor combo box | `ActionEvent` | `ActionListener` | Store selected doctor |
| Time-slot combo box | `ActionEvent` | `ActionListener` | Store selected slot |
| Book button | `ActionEvent` | `ActionListener` | Validate fields and book appointment |
| Window close control | `WindowEvent` | `WindowListener` / `WindowAdapter` | Ask confirmation if booking is in progress; close only if confirmed |

## Task 2.2 – Component Hierarchy
```text
JFrame AppointmentFrame (BorderLayout)
├── JPanel formPanel (GridLayout)
│   ├── JLabel + JTextField patientId
│   ├── JLabel + JComboBox department
│   ├── JLabel + JComboBox doctor
│   └── JLabel + JComboBox timeSlot
├── JPanel actionPanel (FlowLayout)
│   └── JButton Book
└── JLabel status (SOUTH)
```

## Task 2.3 – Handler Pseudocode
**(a) Refresh doctor list**
```text
ON department selection change
    selectedDepartment ← departmentCombo selection
    doctorCombo.removeAllItems()
    FOR EACH doctor IN doctorsByDepartment[selectedDepartment]
        doctorCombo.addItem(doctor)
    END FOR
END EVENT
```
**(b) Validate and book**
```text
ON Book button click
    IF patientId is empty THEN show error; RETURN
    IF department, doctor, or time slot is not selected THEN show error; RETURN
    bookingInProgress ← TRUE
    TRY
        appointmentService.book(patientId, department, doctor, timeSlot)
        show confirmation
    CATCH booking error
        show error message
    FINALLY
        bookingInProgress ← FALSE
    END TRY
END EVENT
```

## Task 2.4 – Event Sequence
1. Construct frame and components.
2. Register listeners and load department/doctor data.
3. Call `setVisible(true)`.
4. User changes department → selection event → handler refreshes doctor list.
5. User clicks Book → action event → validation → service booking → confirmation/error.
6. User closes window → `windowClosing(WindowEvent)` → ask confirmation if a booking is in progress.
7. If confirmed, dispose/close; otherwise cancel closing.

Unlike an applet lifecycle (`init`, `start`, `paint`, `stop`, `destroy`), a desktop GUI is primarily driven by user-generated events. AWT/Swing windows use constructors, visibility, event handlers, painting, and window events rather than the applet lifecycle methods.

## Alternative Concept and Complexity Analysis
Loading all doctors once into `Map<String, List<Doctor>>` costs O(m) time and O(m + d) memory for m doctors and d departments. A department change can retrieve the list in O(1) average map lookup plus O(r) to populate r doctors. Querying the database on every change adds network/database latency each time and uses less in-memory doctor data (roughly O(r) for the current list), but may make the interface slower and less responsive. Caching is better when the data changes infrequently; database refresh is useful when current data is essential.
