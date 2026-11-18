import java.time.LocalTime;
import java.util.*;

abstract class Vehicle {
    private final String registrationNo;
    protected Vehicle(String registrationNo) {
        if (registrationNo == null || registrationNo.trim().isEmpty())
            throw new IllegalArgumentException("Registration number required");
        this.registrationNo = registrationNo;
    }
    public String getRegistrationNo() { return registrationNo; }
    public abstract double calculateFee(int minutes, LocalTime entryTime);
}
interface PassHolder {
    boolean hasValidPass();
}
class StudentCar extends Vehicle implements PassHolder {
    private final boolean monthlyPass;
    StudentCar(String r, boolean pass) { super(r); monthlyPass = pass; }
    public boolean hasValidPass() { return monthlyPass; }
    public double calculateFee(int minutes, LocalTime entryTime) {
        return hasValidPass() ? 0 : Math.max(0, (minutes + 59) / 60) * 30.0;
    }
}
class ECar extends Vehicle {
    ECar(String r) { super(r); }
    public double calculateFee(int minutes, LocalTime entryTime) {
        return Math.max(0, (minutes + 59) / 60) * 30.0;
    }
}
class EBike extends Vehicle {
    EBike(String r) { super(r); }
    public double calculateFee(int minutes, LocalTime entryTime) { return 15.0; }
}
class DeliveryVan extends Vehicle {
    DeliveryVan(String r) { super(r); }
    public double calculateFee(int minutes, LocalTime entryTime) {
        double rate = (!entryTime.isBefore(LocalTime.of(9, 0))
                && entryTime.isBefore(LocalTime.of(17, 0))) ? 60.0 : 30.0;
        return Math.max(0, (minutes + 59) / 60) * rate;
    }
}
class VisitorCar extends Vehicle {
    private final boolean dayPass;
    VisitorCar(String r, boolean dayPass) { super(r); this.dayPass = dayPass; }
    public double calculateFee(int minutes, LocalTime entryTime) {
        if (dayPass) return 100.0;
        return Math.max(0, (minutes + 59) / 60) * 30.0;
    }
}
class AdaptiveFacility {
    private final String[][] slots;
    private final Map<String, Vehicle> parked = new LinkedHashMap<>();
    AdaptiveFacility(int[] floorSlotCounts) {
        slots = new String[floorSlotCounts.length][];
        for (int i = 0; i < floorSlotCounts.length; i++)
            slots[i] = new String[floorSlotCounts[i]];
    }
    public boolean park(Vehicle v) {
        if (parked.containsKey(v.getRegistrationNo())) {
            System.out.println("Duplicate registration rejected: " + v.getRegistrationNo());
            return false;
        }
        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    slots[f][s] = v.getRegistrationNo();
                    parked.put(v.getRegistrationNo(), v);
                    System.out.println(v.getClass().getSimpleName() + " " + v.getRegistrationNo()
                            + " parked at floor " + f + ", slot " + s);
                    return true;
                }
            }
        }
        System.out.println("Parking full for " + v.getRegistrationNo());
        return false;
    }
    public double leave(String regNo, int minutes, LocalTime entryTime) {
        Vehicle v = parked.remove(regNo);
        if (v == null) {
            System.out.println("No active vehicle: " + regNo);
            return -1;
        }
        for (int f = 0; f < slots.length; f++)
            for (int s = 0; s < slots[f].length; s++)
                if (regNo.equals(slots[f][s])) slots[f][s] = null;
        double fee = v.calculateFee(minutes, entryTime);
        System.out.println(regNo + " fee = Rs " + fee);
        return fee;
    }
}
public class AdaptiveParkingSystem {
    public static void main(String[] args) {
        AdaptiveFacility facility = new AdaptiveFacility(new int[]{3, 4, 2, 5, 3});
        facility.park(new EBike("EB01"));
        facility.park(new DeliveryVan("DV01"));
        facility.park(new VisitorCar("VC01", true));
        facility.park(new StudentCar("SC01", true));
        facility.leave("EB01", 120, LocalTime.of(10, 0));
        facility.leave("DV01", 120, LocalTime.of(10, 0));
        facility.leave("VC01", 180, LocalTime.of(12, 0));
        facility.leave("SC01", 60, LocalTime.of(8, 0));
    }
}