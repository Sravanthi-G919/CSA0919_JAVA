Assignment 1 – Task 5: Static Members and Object Life Cycle
Student Details
- Name: Gunapu Sravanthi
- Register Number: 192472331
- Course: CSA0919 Programming in Java
Files
- Assignment1_Task5_Report_Gunapu_Sravanthi.pdf – Task 5 analysis and complexity discussion.
- ParkingTicketStaticDemo.java – Java demonstration of a shared static ticket counter, static block, static method, object passing/returning and object reachability.
Java Version
Recommended: Java 17 or later.
Compile and Run
Open a terminal in this folder and run:
javac ParkingTicketStaticDemo.java
java ParkingTicketStaticDemo
What the Program Demonstrates
1. nextTicketNumber is a static field shared by all ParkingTicket objects.
2. generateTicketNumber() is a static method that updates the shared counter.
3. A static block runs once when ParkingTicket is initialized.
4. Two different ParkingFacility objects issue tickets using the same ticket sequence.
5. The issueTicket() method returns a ParkingTicket object.
6. Clearing a reference may make an object eligible for garbage collection only when no other live references point to it.
Complexity Summary
- Ticket counter update: O(1) time and O(1) counter space.
- Creating n ticket objects: O(n) total constructor time and O(n) heap space for the objects.
- Static counter shared by all facilities: O(1) counter storage.
- Instance counter in k facility objects: O(k) total counter storage.
- Each ticket counter increment: O(1) time.
Expected Output
Ticket numbers will be sequential (starting at PK-1001 and PK-1002). The displayed entry timestamps will depend on the time at which the program is run. The class-initialization message appears once.
