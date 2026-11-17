# Assignment 1 – Task 7: Override and Specialise Behaviour

## Student Details

- **Name:** Gunapu Sravanthi
- **Register Number:** 192472331
- **Course:** CSA0919 – Programming in Java
- **Assignment:** Assignment 1
- **Task:** Task 7 – Override and Specialise Behaviour

---

## 1. Objective

The objective of this task is to demonstrate method overriding in Java using the Campus Smart-Parking System. Different vehicle types calculate parking fees differently. Method overriding allows each subclass to provide its own implementation of the `calculateFee()` method.

This task also demonstrates the use of the `@Override` annotation, the `final` keyword, and overriding the `toString()`, `equals()`, and `hashCode()` methods inherited from the Object class.

## 2. Concepts Used

### Method Overriding

Method overriding occurs when a subclass provides its own implementation of a method already declared in its superclass.

In this program:

- `Vehicle` is the abstract superclass.
- `TwoWheeler` overrides `calculateFee()`.
- `Car` overrides `calculateFee()`.
- `ElectricCar` overrides `calculateFee()`.

Each vehicle type calculates its parking fee according to its own hourly rate.

### @Override Annotation

The `@Override` annotation indicates that a method is intended to override a superclass method. It helps the compiler identify mistakes in method signatures.

The overriding method must have a compatible return type, the same method signature, and must not reduce the access level of the inherited method.

### final Keyword

The `final` keyword is used to prevent unwanted modifications.

- The registration number is declared `final` because it should not change after vehicle creation.
- The getter for the registration number is declared `final` to prevent subclasses from changing its behaviour.
- The vehicle class is not declared `final` because it is designed to be extended.

### Object Class Methods

The following methods are overridden:

- `toString()` – Returns readable vehicle information.
- `equals()` – Compares two vehicles using their registration numbers.
- `hashCode()` – Generates a hash code based on the registration number.

Two vehicles with the same registration number are considered equal.

## 3. Program Description

The program contains an abstract `Vehicle` class and three subclasses: `TwoWheeler`, `Car`, and `ElectricCar`.

The parking fee is calculated based on the vehicle type and parking duration. The program also checks whether two vehicle objects with the same registration number are equal and whether they produce the same hash code.

## 4. Software Requirements

- **Programming Language:** Java
- **Recommended Java Version:** Java 17 or above
- **IDE:** Visual Studio Code / IntelliJ IDEA / Eclipse
- **Operating System:** Windows / Linux / macOS

## 5. File Structure

```text
Task07/
│
├── README.md
├── Assignment1_Task7_Report_Gunapu_Sravanthi.pdf
├── VehicleOverridingDemo.java
└── output.png
```

## 6. Compilation and Execution

Open the terminal in the Task07 folder.

Compile
