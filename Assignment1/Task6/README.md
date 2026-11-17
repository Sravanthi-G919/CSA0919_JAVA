Assignment 1 – Task 6: Design the Inheritance Hierarchy
Student Details
- Name: Gunapu Sravanthi
- Register Number: 192472331
- Course: CSA0919 Programming in Java
Objective
Design and justify an inheritance hierarchy for the campus smart-parking system. Demonstrate superclass/subclass relationships, inherited and specialised members, super, and constructor execution order for an ElectricCar.
Files
- Assignment1_Task6_Report_Gunapu_Sravanthi.pdf – report with UML hierarchy, analysis and constructor trace.
- Task6InheritanceDemo.java – Java demonstration program.
Design Summary
Vehicle is the superclass. Car and TwoWheeler extend Vehicle (hierarchical inheritance). ElectricCar extends Car, creating multilevel inheritance: Vehicle → Car → ElectricCar.

Vehicle stores common registration and entry-time information. Car adds seat count. ElectricCar adds battery capacity. TwoWheeler adds helmet-storage information.
Java Version
JDK 8 or later.
Compile and Run
javac Task6InheritanceDemo.java
java Task6InheritanceDemo
Expected Output
Creating an ElectricCar:
Vehicle constructor: TN03C3
Car constructor: 5 seats
ElectricCar constructor: 60 kWh battery

Registration: TN03C3
Entry time: 09:15
Category: Electric car
Seats: 5
Battery: 60 kWh

Creating a TwoWheeler:
Vehicle constructor: TN01A1
TwoWheeler constructor
Category: Two-wheeler
Constructor Execution Order
For new ElectricCar(...), Java executes:
1. Vehicle constructor
2. Car constructor
3. ElectricCar constructor
Each subclass calls its immediate parent constructor with super(...). The parent constructor executes before the subclass constructor continues.
Inheritance Types
- Single: Car extends Vehicle and TwoWheeler extends Vehicle.
- Hierarchical: Car and TwoWheeler share Vehicle as a parent.
- Multilevel: ElectricCar extends Car extends Vehicle.
Alternative: Composition
A composition-based design could use one Vehicle class containing a VehicleType object that stores category and rate. Adding a new type may then require a new type configuration rather than a new subclass. Both approaches calculate a fee in O(1) when the rate is directly available. Searching sequentially through k types takes O(k), while a map can provide expected O(1) lookup. Both designs require O(n) storage for n vehicles; composition adds a type reference per vehicle, and type objects can be shared.
Conclusion
Inheritance avoids repeating common vehicle data and models genuine “is-a” relationships. Composition may be more flexible when categories and pricing rules change frequently. The selected hierarchy is suitable for the current stable vehicle categories.
