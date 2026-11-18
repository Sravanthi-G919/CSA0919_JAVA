class ParkingSlot {
    private final int floor;
    private final int slotNumber;
    private String vehicleNo;

    public ParkingSlot(int floor, int slotNumber) {
        if (floor < 0 || slotNumber < 0)
            throw new IllegalArgumentException("Floor and slot must be non-negative");
        this.floor = floor;
        this.slotNumber = slotNumber;
    }
    public int getFloor() { return floor; }
    public int getSlotNumber() { return slotNumber; }
    public String getVehicleNo() { return vehicleNo; }
    public boolean isFree() { return vehicleNo == null; }

    public void park(String registrationNo) {
        if (registrationNo == null || registrationNo.trim().isEmpty())
            throw new IllegalArgumentException("Registration number is required");
        if (!isFree()) throw new IllegalStateException("Slot is already occupied");
        vehicleNo = registrationNo;
    }
    public boolean release() {
        if (isFree()) return false;
        vehicleNo = null;
        return true;
    }
}
public class EncapsulationDemo {
    public static void main(String[] args) {
        ParkingSlot slot = new ParkingSlot(0, 1);
        System.out.println("Initially free: " + slot.isFree());
        slot.park("TN01AB1234");
        System.out.println("Vehicle parked: " + slot.getVehicleNo());
        System.out.println("Release successful: " + slot.release());
        System.out.println("Free after release: " + slot.isFree());
        try {
            slot.park(" ");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input rejected");
        }
    }
}