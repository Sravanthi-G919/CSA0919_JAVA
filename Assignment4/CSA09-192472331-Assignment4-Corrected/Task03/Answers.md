# Task 3 – From Sketch to Layout

## Task 3.1 – Choose Layout Managers
- Main frame: `BorderLayout` so the centre results area expands when the frame is resized.
- Search row: `FlowLayout(LEFT)` to keep the text field, category choice and Search button together at preferred sizes.
- Bottom controls: `FlowLayout(CENTER)` to keep Prev, Next and Reserve buttons at preferred sizes.
- Results: `BorderLayout` centre containing a `JTextArea` in a `JScrollPane`, or a `JTable` in a `JScrollPane`, so it receives extra space.
- Status message: `BorderLayout.SOUTH` to stay at the bottom.

## Task 3.2 – Nested Containers
```text
JFrame (BorderLayout)
├── JLabel title (NORTH)
├── JPanel searchPanel (FlowLayout.LEFT) (PAGE_START inside main panel)
│   ├── JTextField
│   ├── JComboBox category
│   └── JButton Search
├── JScrollPane → JTable results (CENTER)
├── JPanel navigationPanel (FlowLayout.CENTER) (SOUTH)
│   ├── JButton Prev
│   ├── JButton Next
│   └── JButton Reserve
└── JLabel status (PAGE_END / bottom status strip)
```

## Task 3.3 – Implement the Screen
See `LibrarySearch.java`. It uses nested panels and a scrollable result table.

## Task 3.4 – Test Resizing Behaviour
Capture screenshots after running at these sizes:
1. Small: approximately 500 × 350
2. Medium: approximately 800 × 500
3. Large: approximately 1100 × 700

Expected result: the table/scroll pane expands in both dimensions; the search controls and bottom buttons stay at their preferred sizes. Attach your own screenshots because the window must be executed on your desktop.

## Alternative Concept and Complexity Analysis
`GridBagLayout` is flexible and supports resizing/weight constraints, but requires more constraint configuration. Absolute positioning with `setLayout(null)` and `setBounds` is simple initially but controls do not automatically adapt to resizing or different fonts/screens. Layout computation is generally proportional to the number of components, O(n), for ordinary nested layouts; GridBagLayout has more constraint work and a larger configuration burden. Nested panels add O(p) container objects for p panels, while absolute positioning can reduce panel count but creates maintenance and resizing problems. In most applications, maintainable layout managers are preferable.
