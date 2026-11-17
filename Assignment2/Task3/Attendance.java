import java.util.*;
class Student {
    private final String regNo;
    private final String name;
    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Student))
            return false;
        Student other = (Student) obj;
        return Objects.equals(regNo, other.regNo);
    }
    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }
    @Override
    public String toString() {
        return regNo + " " + name;
    }
}
public class Attendance {
    public static void main(String[] args) {
        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));
        System.out.println("Unique students: " + present.size());
        System.out.println("Students: " + present);
        Set<Student> secondTest = new HashSet<>();
        secondTest.add(new Student("192311003", "Kiran"));
        secondTest.add(new Student("192311003", "Kiran Kumar"));
        System.out.println("Same registration number: "
                + secondTest.size());
        Map<String, Student> byRegNo = new HashMap<>();
        byRegNo.put("192311001",
                new Student("192311001", "Arun"));
        byRegNo.put("192311002",
                new Student("192311002", "Divya"));
        byRegNo.put("192311001",
                new Student("192311001", "Arun"));
        System.out.println("Unique registration numbers: "
                + byRegNo.size());
    }
}
