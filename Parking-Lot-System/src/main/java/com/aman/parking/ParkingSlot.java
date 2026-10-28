package com.aman.parking;

public final class ParkingSlot {
    private final int floorNumber;
    private final int slotNumber;
    private Vehicle vehicle;

    public ParkingSlot(int floorNumber, int slotNumber) {
        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
    }

    public int getFloorNumber() { return floorNumber; }
    public int getSlotNumber() { return slotNumber; }
    public Vehicle getVehicle() { return vehicle; }
    public boolean isOccupied() { return vehicle != null; }

    public void park(Vehicle vehicle) {
        if (isOccupied()) throw new IllegalStateException("Slot is already occupied.");
        this.vehicle = vehicle;
    }

    public Vehicle release() {
        if (!isOccupied()) throw new IllegalStateException("Slot is already empty.");
        Vehicle departing = vehicle;
        vehicle = null;
        return departing;
    }

    @Override
    public String toString() {
        return "Floor " + floorNumber + ", Slot " + slotNumber;
    }
}
