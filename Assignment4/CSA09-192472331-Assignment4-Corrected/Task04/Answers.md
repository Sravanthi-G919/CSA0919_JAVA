# Task 4 – Event-Handling Forensics

## Task 4.1 – Locate the Defect
The method is named `mouseclicked`, but Java's listener method is case-sensitive and must be `mouseClicked`. It therefore does not override the adapter method, so clicks are never handled. The label is also never updated in the original code.

## Task 4.2 – Construct a Failing Interaction
Click to draw several dots, then minimise and restore the window or cover it with another window. The dots may disappear because `getGraphics()` draws directly to the current screen surface; the drawing is not retained when the window is repainted.

## Task 4.3 – Repair the Program
Store each click as a `Point`, update the label, call `repaint()`, and draw all stored points in `paint(Graphics)`. See `DrawPad.java`.

## Task 4.4 – Prevent Recurrence
Writing `@Override` above `mouseclicked` makes the compiler report that the method does not override or implement a method from a supertype. This catches the spelling/capitalisation defect before execution.

## Complete the Code
The TODO 1 and TODO 2 implementation is included in `DrawPad.java`. To reproduce the compiler message, add `@Override` above a method spelled `mouseclicked`; the compiler reports that it does not override a method from its superclass.

## Alternative Concept and Complexity Analysis
With stored points, each repaint redraws n dots, requiring O(n) drawing work and O(n) memory. With an off-screen `BufferedImage`, each click updates the image and each repaint copies the image to the screen, so repaint work is approximately O(width × height) for a full image copy (implementation-dependent), rather than iterating over n points. The image uses O(width × height) pixels of memory; point storage uses O(n). A buffered image can improve repaint speed for many points but consumes memory proportional to the canvas size.
