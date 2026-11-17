
import java.util.HashSet;
import java.util.Set;

class Student {
    int regNo;
    String name;

    Student(int regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }
        Student s = (Student) obj;
        return regNo == s.regNo;
    }

    public int hashCode() {
        return Integer.hashCode(regNo);
    }

    public String toString() {
        return regNo + " - " + name;
    }
}

public class Main {
    public static void main(String[] args) {
        Set<Student> set = new HashSet<>();

        set.add(new Student(101, "Ravi"));
        set.add(new Student(102, "Priya"));
        set.add(new Student(101, "Ravi"));

        System.out.println("Attendance List:");
        for (Student s : set) {
            System.out.println(s);
        }

        System.out.println("Total unique students: " + set.size());
    }
}
