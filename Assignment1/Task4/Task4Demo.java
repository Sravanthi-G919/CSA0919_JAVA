import java.time.LocalDateTime;
public class Task4Demo {
    static class ParkingTicket {
        private String ticketNumber;
        private String registrationNumber;
        private String vehicleType;
        private int floorNumber;
        private int slotNumber;
        private LocalDateTime entryTime;
        public ParkingTicket() {
            this("NOT-ISSUED", "NOT-ASSIGNED", "UNKNOWN",
                 -1, -1, LocalDateTime.now());
        }
        public ParkingTicket(String ticketNumber, String registrationNumber,
                             String vehicleType, int floorNumber, int slotNumber) {
            this(ticketNumber, registrationNumber, vehicleType,
                 floorNumber, slotNumber, LocalDateTime.now());
        }
        public ParkingTicket(String ticketNumber, String registrationNumber,
                             String vehicleType, int floorNumber, int slotNumber,
                             LocalDateTime entryTime) {
            this.ticketNumber = ticketNumber;
            this.registrationNumber = registrationNumber;
            this.vehicleType = vehicleType;
            this.floorNumber = floorNumber;
            this.slotNumber = slotNumber;
            this.entryTime = entryTime;
        }
        public ParkingTicket(ParkingTicket other) {
            this(other.ticketNumber, other.registrationNumber,
                 other.vehicleType, other.floorNumber,
                 other.slotNumber, other.entryTime);
        }
        public void displayTicket() {
            System.out.println("Ticket: " + ticketNumber);
            System.out.println("Vehicle: " + registrationNumber);
            System.out.println("Type: " + vehicleType);
            System.out.println("Slot: Floor " + floorNumber
                               + ", Slot " + slotNumber);
            System.out.println("Entry: " + entryTime);
        }
    }
    public static void main(String[] args) {

        ParkingTicket ticket1 = new ParkingTicket(
            "TKT101", "TN01AB1111", "Car", 0, 2);
        ParkingTicket ticket2 = new ParkingTicket(ticket1);
        System.out.println("Original ticket:");
        ticket1.displayTicket();
        System.out.println("\nCopied ticket (same session details):");
        ticket2.displayTicket();
    }
}

