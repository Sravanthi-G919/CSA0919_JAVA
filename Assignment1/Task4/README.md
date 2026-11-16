Assignment 1 -- Task 4: Design Classes, Objects and Constructors
Student Details
- Name: Gunapu Sravanthi
- Register Number: 192472331
- Course: CSA0919 Programming in Java
Task Information
- Assignment: Design, Implement and Validate an Object-Oriented
  Model for a Campus Smart-Parking System
- Task 4: Design Classes, Objects and Constructors
- Marks: 4
Files
- Assignment1_Task4_Report_Gunapu_Sravanthi.pdf -- Task 4 report.
- Task4Demo.java -- Java demonstration of default, parameterized,
  overloaded and copy constructors, this, and constructor chaining.
- output.png -- Add your own screenshot showing the program output
  after running it.
Concepts Covered
1. Fields and methods of Vehicle, ParkingSlot, ParkingFacility,
   ParkingTicket and ParkingUser.
2. Default, parameterized and overloaded constructors.
3. Use of this and constructor chaining.
4. Copy constructor and its use for reproducing the same ticket/session
   details.
5. Reference variables, stack frames, heap objects and garbage
   collection.
6. Comparison of constructor initialization, default construction
   followed by setters, and static factory methods.
7. Time and space complexity for creating n ParkingTicket objects.
Java Version
Use a JDK that supports java.time.LocalDateTime (Java 8 or later).
Record the exact version installed on your computer with:
java -version
javac -version
Compile and Run
Open a terminal in the folder containing Task4Demo.java, then run:
javac Task4Demo.java
java Task4Demo
Expected Result
The program displays the original parking ticket and a copied ticket.
Both display the same ticket number and session details because the copy
constructor represents a copy of the same parking session, not a new
parking session.
Complexity
For a fixed number of fields: - Constructor initialization: O(1) per
ticket. - Creating n tickets: O(n) total time. - Storage for n
ticket objects: O(n) total space. - Setters and factory methods have
the same asymptotic allocation cost; constructors are preferred here
because required data can be supplied together.
