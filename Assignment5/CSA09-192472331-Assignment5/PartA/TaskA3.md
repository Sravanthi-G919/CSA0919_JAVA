# Task A3 – Discover the Data-Access Design Pattern

## A3.1 Common and different parts
Common steps: obtain connection, prepare/execute SQL, map `ResultSet` rows to objects, handle exceptions, and close resources. Differences: table/entity type, columns, key type, SQL and row-to-object mapping.

## A3.2 Generic DAO interface
```java
public interface Dao<T,K> {
    T insert(T value) throws java.sql.SQLException;
    java.util.Optional<T> findById(K id) throws java.sql.SQLException;
    java.util.List<T> findAll() throws java.sql.SQLException;
    boolean update(T value) throws java.sql.SQLException;
    boolean delete(K id) throws java.sql.SQLException;
}
```
`StudentDao implements Dao<Student,Integer>` and implements each method with `PreparedStatement`, parameter binding, try-with-resources, and a `Student` row mapper. Keep connection creation in a shared connection provider and SQL details inside the DAO.

## A3.3 Student mapping
Example mapping: `Student.id` → `student.student_id` (primary key); `Student.registerNumber` → `student.reg_no` (unique); `Student.name` → `student.name`; `Student.email` → `student.email`. The exact columns should match the actual schema used by the application.

## Alternative: SQL in service/GUI
With SQL embedded in service or GUI classes, a column rename can require edits in every caller that contains the SQL. With a DAO, changes are usually localized to the DAO/mapper (and schema migration). Both designs have broadly the same database-operation asymptotic time and similar result-set memory; DAO adds a small abstraction/method-call overhead but improves maintainability and testability.
