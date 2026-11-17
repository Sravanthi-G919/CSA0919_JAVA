# Assignment 1 – Task 5: Apply Static Members and the Object Life Cycle

## Student Details

- **Name:** Gunapu Sravanthi
- **Register Number:** 192472331
- **Course:** CSA0919 Programming in Java

## 1. Task Objective

The objective of this task is to understand and apply static members in Java and explain the object life cycle in the Campus Smart-Parking System.

The task demonstrates:
- Static variables and their importance.
- Static methods and static blocks.
- Object creation and object references.
- Passing and returning objects from methods.
- Garbage collection and object eligibility.
- Comparison between static and instance variables.

## 2. Files Included

| File Name | Description |
|---|---|
| `Assignment1_Task5_Report_Gunapu_Sravanthi.pdf` | Detailed Task 5 report |
| `ParkingTicketStaticDemo.java` | Java implementation |
| `README.md` | Task description and execution instructions |
| `output.png` | Screenshot of program output |

## 3. Java Concepts Used

### Static Variable

The `nextTicketNumber` variable is declared as static because the ticket counter must be shared by all ParkingTicket objects.

This ensures that tickets issued at different gates receive unique sequential numbers.

### Static Method

The `getIssuedTicketCount()` method is static because it accesses class-level information and does not require an individual ticket object.

### Static Block

The static block initializes the ticket counter when the ParkingTicket class is initialized. It executes only once during that class initialization.

### Object Life Cycle

When a vehicle leaves the parking facility, its ticket is closed and the occupied slot count is decreased.

When no reachable reference points to a ticket object, it becomes eligible for garbage collection. Garbage collection is managed by the JVM and is not immediate or guaranteed at a particular time.

### Object Passing and Returning

The `admitVehicle()` method creates and returns a ParkingTicket object.

The `vehicleLeaves()` method receives a ParkingTicket object as an argument.

## 4. Program Implementation

**Java File:** `ParkingTicketStaticDemo.java`

The program demonstrates:
- Ticket number generation using a static counter.
- Separate occupancy values for North Gate and South Gate.
- Static method to display the total number of issued tickets.
- Passing and returning object references.
- Object life cycle after a vehicle leaves.

## 5. Software Requirements

- Java Development Kit (JDK 8 or later)
- Visual Studio Code / IntelliJ IDEA / Eclipse
- GitHub account

## 6. Steps to Compile and Run

Open the terminal in the Task05 folder.

**Step 1: Compile the Java program**

```bash
javac ParkingTicketStaticDemo.java
```

**Step 2: Execute the program**

```bash
java ParkingTicketStaticDemo
```

## 7. Expected Output

```text
Ticket service initialized.
Issued tickets:
PK-1001 | TN01AB1111 | North Gate
PK-1002 | TN02CD2222 | South Gate
Total tickets issued: 2
North occupancy: 1
South occupancy: 1
Closed ticket: PK-1001
North occupancy after exit: 0
South occupancy after North exit: 1
South ticket still active: PK-1002
```

## 8. Complexity Analysis

Let:
- `k` = Number of parking facility objects.
- `n` = Number of parking tickets.

| Operation | Time Complexity | Space Complexity |
|---|---|---|
| Increment static ticket counter | O(1) | O(1) |
| Create one ticket | O(1) | O(1) per ticket |
| Create n tickets | O(n) | O(n) for all ticket objects |
| Store static counter | O(1) | O(1) |
| Store instance counter in k facilities | O(k) | O(k) |

A static counter uses constant space because only one shared copy exists. An instance counter requires one copy for every facility object and may generate duplicate ticket numbers if each facility starts its own sequence.

## 9. Result

The Java program was compiled and executed successfully. It generated unique ticket numbers across North Gate and South Gate using a shared static counter. It also demonstrated separate occupancy values for each facility and explained object references and garbage collection.

## 10. GitHub Repository Structure

```text
CSA09-192472331-Assignment1/
│
└── Task05/
    ├── README.md
    ├── Assignment1_Task5_Report_Gunapu_Sravanthi.pdf
    ├── ParkingTicketStaticDemo.java
    └── output.png
```

## 11. Conclusion

This task demonstrates the importance of static members in Java. A static ticket counter provides a common ticket-number sequence across multiple parking facilities, while instance variables maintain separate facility occupancy. The program also explains how objects are passed and returned using references and when objects become eligible for garbage collection.

---

**Course:** CSA0919 Programming in Java  
**Assignment:** Assignment 1 – Task 5
