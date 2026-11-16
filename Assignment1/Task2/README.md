Assignment 1 – Task 2: Choose the Data Representation
Student Details
- Name: Gunapu Sravanthi
- Register Number: 192472331
- Course: CSA0919 Programming in Java
Task Information
- Assignment: Design, Implement and Validate an Object-Oriented Model for a Campus Smart-Parking System
- Task: Task 2 – Choose the Data Representation
- Marks: 3
Objective
Choose suitable Java data types and an array structure for representing the floors, parking slots, vehicle details, entry time, parking duration and parking fee in the Campus Smart-Parking System.
Design Summary
- int – floor number and slot number.
- String – vehicle registration number and slot occupancy.
- LocalDateTime – vehicle entry time.
- long – parking duration in minutes.
- double – calculated parking fee in this basic model. For production financial calculations, BigDecimal is generally preferable.
- String[][] – jagged two-dimensional array where each row represents a floor and can have a different number of slots.
- Scanner – interactive console input.
Type Conversion
Convert minutes to hours before proportional division, for example:
double hours = minutes / 60.0;
double fee = hours * ratePerHour;
Do not cast after integer division because the fractional part will already have been lost. If every started hour is charged, use a ceiling-based calculation that matches that business rule.
Alternative Representations
The report compares:
1. A jagged String[][] array.
2. A single flat String[] array using index calculations.
3. An ArrayList<String[]>.
The report includes the time and space complexity comparison and explains why a jagged array is suitable when floors have different capacities.
Files
- Assignment1_Task2_Report_Gunapu_Sravanthi.pdf – detailed Task 2 report.
Java Version
The Task 2 deliverable is a data-representation analysis and does not require a separate Java implementation. If Java is used for later tasks, record the actual installed version using:
java -version
Result
The data representation is selected and justified. A jagged two-dimensional array is recommended because it directly represents floors with unequal slot capacities, while suitable Java data types and explicit numeric conversion help avoid data and billing errors.
