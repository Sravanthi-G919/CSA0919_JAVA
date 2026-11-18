import java.util.*;

abstract class Vehicle {
    private final String registrationNo;
    protected Vehicle(String registrationNo) {
        if (registrationNo == null || registrationNo.trim().isEmpty())
            throw new IllegalArgumentException("Registration number is required");
        this.registrationNo = registrationNo;
    }
    public String getRegistrationNo() { return registrationNo; }
    public abstract double hourlyRate();
}
class Bike extends Vehicle {
    Bike(String r) { super(r); }
    public double hourlyRate() { return 10; }
}
class Car extends Vehicle {
    Car(String r) { super(r); }
    public double hourlyRate() { return 30; }
}
class ElectricCar extends Car {
    ElectricCar(String r) { super(r); }
}
class Ticket {
    private static int nextId = 1000;
    private final int ticketId;
    private final Vehicle vehicle;
    private final int floor;
    private final int slot;
    private final long entryMillis;
    Ticket(Vehicle vehicle, int floor, int slot) {
        this.ticketId = ++nextId;
        this.vehicle = vehicle;
        this.floor = floor;
        this.slot = slot;
        this.entryMillis = System.currentTimeMillis();
    }
    public String getRegistrationNo() { return vehicle.getRegistrationNo(); }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public int getTicketId() { return ticketId; }
    public double feeForMinutes(int minutes) {
        if (minutes <= 0) return 0;
        return ((minutes + 59) / 60) * vehicle.hourlyRate();
    }
}
class ParkingFacility {
    private final String[][] slots;
    private final Map<String, Ticket> activeTickets = new LinkedHashMap<>();
    ParkingFacility(int[] slotsPerFloor) {
        slots = new String[slotsPerFloor.length][];
        for (int i = 0; i < slotsPerFloor.length; i++) {
            if (slotsPerFloor[i] < 1) throw new IllegalArgumentException("Each floor needs a slot");
            slots[i] = new String[slotsPerFloor[i]];
        }
    }
    public Ticket enter(Vehicle vehicle) {
        String reg = vehicle.getRegistrationNo();
        if (activeTickets.containsKey(reg)) {
            System.out.println("Rejected duplicate registration: " + reg);
            return null;
        }
        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    slots[f][s] = reg;
                    Ticket ticket = new Ticket(vehicle, f, s);
                    activeTickets.put(reg, ticket);
                    System.out.println("Parked " + reg + " at floor " + f + ", slot " + s
                            + " | Ticket " + ticket.getTicketId());
                    return ticket;
                }
            }
        }
        System.out.println("No free slot for " + reg);
        return null;
    }
    public double exit(String registrationNo, int minutesParked) {
        Ticket ticket = activeTickets.remove(registrationNo);
        if (ticket == null) {
            System.out.println("No active ticket for " + registrationNo);
            return -1;
        }
        slots[ticket.getFloor()][ticket.getSlot()] = null;
        double fee = ticket.feeForMinutes(minutesParked);
        System.out.println("Exit " + registrationNo + " | minutes " + minutesParked
                + " | fee Rs " + fee);
        return fee;
    }
    public void printStatus() {
        int occupied = activeTickets.size(), total = 0;
        for (String[] floor : slots) total += floor.length;
        System.out.println("Occupied: " + occupied + "/" + total);
    }
}
public class ParkingSystem {
    public static void main(String[] args) {
        ParkingFacility parking = new ParkingFacility(new int[]{2, 1});
        parking.enter(new Bike("TN01AB1111"));
        parking.enter(new Car("TN02CD2222"));
        parking.enter(new ElectricCar("TN03EF3333"));
        parking.enter(new Car("TN04GH4444"));
        parking.printStatus();
        parking.exit("TN02CD2222", 60);
        parking.enter(new Car("TN04GH4444"));
        parking.exit("TN01AB1111", 0);
        parking.exit("UNKNOWN", 30);
        parking.printStatus();
    }
}