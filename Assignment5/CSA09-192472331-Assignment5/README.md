# Programming in Java – Assignment 5 (CO5)
**Name:** Gunapu Sravanthi  
**Register Number:** 192472331  
**Course:** Programming in Java  
**Repository name:** `CSA09-192472331-Assignment5`

## Structure
- `PartA/TaskA1.md` through `TaskA5.md`: design answers, standard algorithmic pseudocode, and alternative-concept complexity comparisons. Part A requires pseudocode/reasoning, not Java in place of pseudocode.
- `PartB/TaskB1/`: text-file bus-pass store and buffered/unbuffered line counter.
- `PartB/TaskB2/`: deliberately unsafe and safe JDBC login examples plus damaged-file test.
- `PartB/TaskB3/`: batch loader, JDBC comparison benchmark, and SQL schema.
- `PartB/TaskB4/`: migration utility and database metadata inspection.
- `PartB/sql/schema.sql`: database setup.
- `PartB/test-data/`: sample pass records including damaged input.
- `PartB/results/`: CSV templates for measurements.

## Requirements
- Java 17 or newer recommended (`Files.readString`, `Files.writeString`, and text blocks are used).
- Tasks B2–B4 need MySQL and MySQL Connector/J on the classpath. Edit the database URL, username, and password in the Java files or set environment variables `DB_URL`, `DB_USER`, and `DB_PASSWORD`.
- File-only B1 programs can run in a normal Java compiler. JDBC programs will not run in an online compiler unless it supports external JDBC drivers and can reach your database.
- Do not use the intentionally vulnerable login with real accounts or a real database. It is only for a local assignment test database.

## Compile and run (VS Code terminal)
From the repository root:
```bash
javac -d out PartB/TaskB1/PassStore.java
java -cp out PassStore

javac -d out PartB/TaskB1/BufferedLineCounter.java
java -cp out BufferedLineCounter PartB/test-data/attendance-1mb.txt

javac -d out PartB/TaskB2/DamagedPassFileTest.java
java -cp out DamagedPassFileTest PartB/test-data/damaged-passes.csv
```
For JDBC files, put MySQL Connector/J JAR in `lib/` and include it in the classpath. Windows example:
```bat
javac -cp "lib\mysql-connector-j.jar" -d out PartB\TaskB2\LoginComparison.java
java -cp "out;lib\mysql-connector-j.jar" LoginComparison
```
Use `:` instead of `;` on macOS/Linux. Compile each JDBC class with the connector JAR and follow the comments at the top of the source file.

## Important evidence requirement
The assignment requires actual screenshots, test results and measured timings for 1 MB, 10 MB and 50 MB files, 1,000/10,000/100,000 records, plus interpretations. Those results depend on your computer/database, so the ZIP contains runnable measurement programs and CSV templates—not fabricated measurements. Run them and fill in `PartB/results/` with your actual observations and screenshots before submission.
