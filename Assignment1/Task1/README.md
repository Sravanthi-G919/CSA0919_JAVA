Assignment 1 – Task 1
Student Details
- Name: Gunapu Sravanthi
- Register Number: 192472331
- Course: CSA0919 Programming in Java
- Assignment: Design, Implement and Validate an Object-Oriented Model for a Campus Smart-Parking System
- Task: Task 1 – Understand the Problem and Identify the Objects
Folder Contents
- Assignment1_Task1_Report_Gunapu_Sravanthi.pdf – Task 1 analysis report.
Task 1 Summary
The report analyses the campus smart-parking scenario and identifies the inputs and outputs, candidate classes, class responsibilities, business rules and assumptions. It also compares a design using Vehicle objects with a design using parallel arrays (regNo[], type[] and entryTime[]).
Java Version
Task 1 is a problem-analysis and design task. It does not require a Java program to be implemented or executed. For the later Java implementation tasks, Java 17 or a compatible later version is a suitable project target. Confirm the version installed on your computer with:
java -version
javac -version
Compile and Run
There is no Java source file to compile or run for Task 1 because the Task 1 instructions ask for analysis and complexity comparison, not a code implementation.

For later tasks, place each task's .java files in its own folder (for example, Task02, Task03) and compile/run according to that task's source files. For a single-file Java program:
javac FileName.java
java ClassName
Replace FileName and ClassName with the actual source filename and class containing main.
Result
Task 1 analysis has been prepared. The object-oriented design is recommended because it groups each vehicle's data and behaviour together and is easier to extend than parallel arrays. A registration-number search is O(n) in the worst case for both approaches; the main advantage of objects is maintainability and safer extension, not a better search complexity.
