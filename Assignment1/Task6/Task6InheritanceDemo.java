public class Task6InheritanceDemo {
    static class Vehicle {
        private final String regNo;
        private final String entryTime;
        public Vehicle(String regNo, String entryTime) {
            this.regNo = regNo;
            this.entryTime = entryTime;
            System.out.println("Vehicle constructor: " + regNo);
        }
        public String getRegNo() {
            return regNo;
        }
        public String getEntryTime() {
            return entryTime;
        }
        public String category() {
            return "General vehicle";
        }
    }
    static class Car extends Vehicle {
        private final int seatCount;
        public Car(String regNo, String entryTime, int seatCount) {
            super(regNo, entryTime);
            this.seatCount = seatCount;
            System.out.println("Car constructor: "
                    + seatCount + " seats");
        }
        public int getSeatCount() {
            return seatCount;
        }
        @Override
        public String category() {
            return "Car";
        }
    }
    static class ElectricCar extends Car {
        private final int batteryCapacityKWh;
        public ElectricCar(String regNo, String entryTime,
                           int seatCount, int batteryCapacityKWh) {
            super(regNo, entryTime, seatCount);
            this.batteryCapacityKWh = batteryCapacityKWh;
            System.out.println("ElectricCar constructor: "
                    + batteryCapacityKWh + " kWh battery");
        }
        public int getBatteryCapacityKWh() {
            return batteryCapacityKWh;
        }
        @Override
        public String category() {
            return "Electric car";
        }
    }
    static class TwoWheeler extends Vehicle {
        private final boolean hasHelmetStorage;
        public TwoWheeler(String regNo, String entryTime,
                          boolean hasHelmetStorage) {
            super(regNo, entryTime);
            this.hasHelmetStorage = hasHelmetStorage;
            System.out.println("TwoWheeler constructor");
        }
        public boolean hasHelmetStorage() {
            return hasHelmetStorage;
        }
        @Override
        public String category() {
            return "Two-wheeler";
        }
    }
    public static void main(String[] args) {
        System.out.println("Creating an ElectricCar:");
        ElectricCar ev = new ElectricCar(
                "TN03C3", "09:15", 5, 60);
        System.out.println();
        System.out.println("Registration: " + ev.getRegNo());
        System.out.println("Entry time: " + ev.getEntryTime());
        System.out.println("Category: " + ev.category());
        System.out.println("Seats: " + ev.getSeatCount());
        System.out.println("Battery: "
                + ev.getBatteryCapacityKWh() + " kWh");
        System.out.println();
        System.out.println("Creating a TwoWheeler:");
        TwoWheeler bike = new TwoWheeler(
                "TN01A1", "09:20", true);
        System.out.println("Category: " + bike.category());
    }
}

