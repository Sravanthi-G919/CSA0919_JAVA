# Task 5 – Same Screen, Different Event-Handling Styles

## Task 5.1 – Four Implementations
Four complete examples are supplied:
- `Converter.java`: frame itself implements `ActionListener`
- `ConverterB.java`: named inner class
- `ConverterC.java`: anonymous inner class
- `Converter.java` uses lambda in the complete-code style; `ConverterLambda.java` is also provided explicitly for clarity.

Each implementation handles closing with a `WindowAdapter`. The amount field also triggers conversion when Enter is pressed, and the result appears in a dialog.

## Task 5.2 – Change the Requirement
The button and text field can both call the same conversion method. A dialog is displayed with `JOptionPane.showMessageDialog`. In all four designs, the main change is to register the text field's action event and replace/update result-label feedback with a dialog. Lambdas and method references require the least boilerplate; a named inner class is convenient when the handler has substantial logic or needs reuse.

## Task 5.3 – Compare the Styles
| Style | Readability | Access to fields | Reuse | Ease of change |
|---|---|---|---|---|
| Frame implements `ActionListener` | Simple for tiny programs; grows cluttered | Direct | Low unless shared dispatch is used | Moderate |
| Named inner class | Clear separation; more lines | Direct access to outer instance | Good | Good for complex logic |
| Anonymous inner class | Handler stays near component | Direct access | Limited | Moderate |
| Lambda | Concise for short actions | Direct access to enclosing fields | Low as an inline expression, unless assigned/shared | Very easy for small handlers |

For a large application, use a named handler or shared `Action`/service method to keep logic reusable and testable. Lambdas are excellent for short event actions.

## Alternative Concept and Complexity Analysis
A method reference such as `this::convert` avoids repeating a lambda body and delegates to a named method. A shared `Action` can be attached to a button, menu item, and keyboard shortcut, keeping the action consistent. Separate inline listeners may create one object per trigger; a shared action can reduce duplicated handler objects and maintenance work. Dispatch itself is approximately O(1); the conversion calculation is O(1), while UI/database work may dominate.
