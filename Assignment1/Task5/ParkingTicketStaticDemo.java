public class ParkingTicketStaticDemo {
    static class ParkingTicket {
        private static int nextTicketNumber;
        private static final String PREFIX = "PK-";
        private final String ticketNumber;
        private final String vehicleNumber;
        private final String gateName;
        static {
            nextTicketNumber = 1000;
            System.out.println("Ticket service initialized.");
        }
        public ParkingTicket(String vehicleNumber, String gateName) {
            nextTicketNumber++;
            this.ticketNumber = PREFIX + nextTicketNumber;
            this.vehicleNumber = vehicleNumber;
            this.gateName = gateName;
        }
        public String getTicketNumber() {
            return ticketNumber;
        }
        public String getVehicleNumber() {
            return vehicleNumber;
        }
        public String getGateName() {
            return gateName;
        }
        public static int getIssuedTicketCount() {
            return nextTicketNumber - 1000;
        }
        public void display() {
            System.out.println(ticketNumber + " | "
                    + vehicleNumber + " | " + gateName);
        }
    }
    static class ParkingFacility {
        private final String gateName;
        private int occupiedSlots;
        public ParkingFacility(String gateName) {
            this.gateName = gateName;
            this.occupiedSlots = 0;
        }
        public ParkingTicket admitVehicle(String vehicleNumber) {
            ParkingTicket ticket =
                    new ParkingTicket(vehicleNumber, gateName);
            occupiedSlots++;
            return ticket;
        }
        public void vehicleLeaves(ParkingTicket ticket) {
            if (ticket != null && occupiedSlots > 0
                    && gateName.equals(ticket.getGateName())) {
                occupiedSlots--;
                System.out.println("Closed ticket: "
                        + ticket.getTicketNumber());
            }
        }
        public int getOccupiedSlots() {
            return occupiedSlots;
        }
    }
    public static void main(String[] args) {
        ParkingFacility northGate =
                new ParkingFacility("North Gate");
        ParkingFacility southGate =
                new ParkingFacility("South Gate");
        ParkingTicket northTicket =
                northGate.admitVehicle("TN01AB1111");
        ParkingTicket southTicket =
                southGate.admitVehicle("TN02CD2222");
        System.out.println("Issued tickets:");
        northTicket.display();
        southTicket.display();
        System.out.println("Total tickets issued: "
                + ParkingTicket.getIssuedTicketCount());
        System.out.println("North occupancy: "
                + northGate.getOccupiedSlots());
        System.out.println("South occupancy: "
                + southGate.getOccupiedSlots());
        northGate.vehicleLeaves(northTicket);
        northTicket = null;
        System.out.println("North occupancy after exit: "
                + northGate.getOccupiedSlots());
        System.out.println("South occupancy after North exit: "
                + southGate.getOccupiedSlots());
        System.out.println("South ticket still active: "
                + southTicket.getTicketNumber());
    }
}

