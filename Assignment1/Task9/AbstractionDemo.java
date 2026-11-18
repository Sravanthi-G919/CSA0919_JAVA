interface Chargeable {
    double chargingFee(int kWh);
    default String chargingMessage() { return "Charging service available"; }
}
abstract class Vehicle {
    private final String regNo;
    protected Vehicle(String regNo) {
        if (regNo == null || regNo.trim().isEmpty())
            throw new IllegalArgumentException("Registration number is required");
        this.regNo = regNo;
    }
    public String getRegNo() { return regNo; }
    public abstract double calculateFee(int minutes);
}
class Bike extends Vehicle {
    Bike(String regNo) { super(regNo); }
    @Override public double calculateFee(int minutes) {
        return Math.max(0, (minutes + 59) / 60) * 10.0;
    }
}
class ElectricCar extends Vehicle implements Chargeable {
    ElectricCar(String regNo) { super(regNo); }
    @Override public double calculateFee(int minutes) {
        return Math.max(0, (minutes + 59) / 60) * 30.0;
    }
    @Override public double chargingFee(int kWh) {
        if (kWh < 0) throw new IllegalArgumentException("kWh cannot be negative");
        return kWh * 12.0;
    }
}
public class AbstractionDemo {
    public static void main(String[] args) {
        Vehicle[] vehicles = { new Bike("TN01A1"), new ElectricCar("TN02B2") };
        for (Vehicle v : vehicles) {
            System.out.println(v.getClass().getSimpleName() + " fee = Rs "
                    + v.calculateFee(90));
            if (v instanceof Chargeable) {
                Chargeable c = (Chargeable)v;
                System.out.println(c.chargingMessage());
                System.out.println("Charging fee = Rs " + c.chargingFee(5));
            }
        }
    }
}