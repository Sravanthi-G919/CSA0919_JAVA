# Task 1 – GUI Design Review

## Task 1.1 – Diagnose the Design
1. **UI and business logic are mixed:** the listener parses input, connects to the database, writes data, waits, and prints output. This is difficult to test and maintain.
2. **Incorrect string comparison:** `==` compares object references; use `"Pay".equals(e.getActionCommand())` or compare the source button.
3. **No input validation:** empty registration number, negative amount, non-numeric amount, or zero can cause errors or invalid records.
4. **Blocking work on the UI thread:** database access and `Thread.sleep(3000)` freeze the window, making it appear hung.
5. **No visible feedback:** `System.out.println("Paid")` is not useful to the user of a GUI.
6. **Receipt button is not implemented:** it falls into the `else` branch and clears the fields.
7. **SQL injection risk:** concatenating user input into SQL can corrupt the query or allow malicious input; use `PreparedStatement`.
8. **Resources are not closed:** the connection and statement may remain open; use try-with-resources.
9. **No exception handling:** JDBC and sleep-related checked exceptions are not handled.
10. **Window closing is not handled:** the application may not dispose of the window cleanly.

## Task 1.2 – Repair the Design
Class structure:
- `FeeWindow`: collects registration number and amount; calls service; displays success/error.
- `FeeService`: validates values and performs payment logic/database work.
- A repository/data-access class can be added to isolate JDBC further.

The included `FeeWindow.java` demonstrates separated UI/service responsibilities with validation and clear user feedback. Database integration is represented as a safe service placeholder; production use should inject a JDBC repository and use a `PreparedStatement`.

## Task 1.3 – Design Defence (under 100 words)
The redesigned program separates presentation from payment rules. The window only reads input and displays messages, while `FeeService` validates the registration number and amount and performs the payment operation. The service can be tested without opening a window, and the interface can be redesigned without changing payment rules. This separation reduces duplicated code, makes errors easier to locate, and supports future database changes. Input validation and service behaviour can be tested independently.

## Alternative Concept and Complexity Analysis
With one listener and an if–else chain, dispatch may require up to `k` command comparisons, so worst-case dispatch selection is O(k). Separate listener objects or a shared Swing `Action` connect each event source directly to its handler, making handler selection approximately O(1) after event dispatch. Separate listeners may use O(k) additional objects; a shared Action can be reused by several controls and reduce duplicated handler logic. Actual runtime is usually dominated by the handler's work, not the comparison itself.
