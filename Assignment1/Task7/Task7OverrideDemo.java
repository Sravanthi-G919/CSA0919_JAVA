public class Task7OverrideDemo {
    static abstract class Vehicle {
        private final String regNo;
        private final String entryTime;
        protected Vehicle(String regNo, String entryTime) {
            if (regNo == null || regNo.trim().isEmpty()) {
                throw new IllegalArgumentException(
                    "Registration number cannot be empty.");
            }
            this.regNo = regNo;
            this.entryTime = entryTime;
        }
        public final String getRegNo() {
            return regNo;
        }
        public String getEntryTime() {
            return entryTime;
        }
        protected final int billableHours(int minutes) {
            if (minutes < 0) {
                throw new IllegalArgumentException(
                    "Parking minutes cannot be negative.");
            }
            return (minutes + 59) / 60;
        }
        public abstract double calculateFee(int minutes);
        @Override
        public String toString() {
            return getClass().getSimpleName()
                    + "{regNo='" + regNo
                    + "', entryTime='" + entryTime + "'}";
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Vehicle)) {
                return false;
            }
            Vehicle other = (Vehicle) obj;
            return regNo.equals(other.regNo);
        }
        @Override
        public int hashCode() {
            return regNo.hashCode();
        }
    }
    static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo, String entryTime) {
            super(regNo, entryTime);
        }
        @Override
        public double calculateFee(int minutes) {
            return billableHours(minutes) * 10.0;
        }
    }
    static class Car extends Vehicle {
        public Car(String regNo, String entryTime) {
            super(regNo, entryTime);
        }
        @Override
        public double calculateFee(int minutes) {
            return billableHours(minutes) * 30.0;
        }
    }
    static class ElectricCar extends Car {
        public ElectricCar(String regNo, String entryTime) {
            super(regNo, entryTime);
        }
        @Override
        public double calculateFee(int minutes) {
            return billableHours(minutes) * 30.0;
        }
    }
    public static void main(String[] args) {
        Vehicle bike =
                new TwoWheeler("TN01A1", "09:00");
        Vehicle car =
                new Car("TN02B2", "09:10");
        Vehicle electricCar =
                new ElectricCar("TN03C3", "09:15");
        System.out.println("Parking fees for 130 minutes:");
        System.out.println("TwoWheeler -> Rs "
                + bike.calculateFee(130));
        System.out.println("Car -> Rs "
                + car.calculateFee(130));
        System.out.println("ElectricCar -> Rs "
                + electricCar.calculateFee(130));
        System.out.println();
        System.out.println("Vehicle details:");
        System.out.println(bike);
        System.out.println(car);
        System.out.println(electricCar);
        Vehicle carWithSameReg =
                new Car("TN02B2", "11:00");
        System.out.println();
        System.out.println("Same registration means equal: "
                + car.equals(carWithSameReg));
        System.out.println("Equal objects have same hash code: "
                + (car.hashCode()
                == carWithSameReg.hashCode()));
    }
}
