Assignment 1 – Task 3: Design the Control Logic
Student Details
- Name: Gunapu Sravanthi
- Register Number: 192472331
- Course: CSA0919 Programming in Java
Assignment Details
- Title: Design, Implement and Validate an Object-Oriented Model for a Campus Smart-Parking System
- Task: Task 3 – Design the Control Logic
- Marks: 4
Objective
Design the decision and looping logic for the two main parking operations:
1. Allocating the first suitable free parking slot.
2. Computing the parking fee using fee slabs.
Design Summary
- The parking layout is stored in a jagged two-dimensional array: String[][] slots.
- The outer array represents floors; each row represents the slots on one floor.
- null means that a slot is free; a vehicle registration number means that it is occupied.
- Allocation scans floor by floor and slot by slot. It stores the vehicle in the first free slot and returns {floor, slot}.
- If all slots are occupied, allocation returns null.
- Release validates the floor and slot indexes. It returns false for invalid or already-free positions; otherwise, it clears the slot and returns true.
- Fee computation uses decisions for the first hour, additional hours and a daily maximum. The assignment does not specify the actual fee rates, so symbolic rates are used in the report.
- Compile and Run
Install a JDK and check the installed version:
java -version
javac -version
Compile:
javac ParkingFacility.java
Run:
java ParkingFacility
Expected Output
[0, 0]
[0, 1]
[1, 0]
null
true false false
[0, 1]
The completed program was compiled and executed, and its output matched the expected output in the assignment sheet.
Complexity Summary
Let F be the number of floors and S the maximum number of slots per floor.
- Scanning allocation: O(F × S) worst case.
- Releasing a known floor and slot: O(1).
- Per-floor free-slot stacks: O(1) pop/push once a non-full floor is available; finding that floor by scanning counts takes O(F). An additional non-full-floor structure can reduce floor selection overhead.
- The stack approach requires additional storage for free slot positions and per-floor counts.
