package com.aman.parking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public final class ParkingLot {
    private final Map<Integer, Stack<ParkingSlot>> availableByFloor = new HashMap<>();
    private final Map<String, ParkingSlot> occupiedByRegistration = new HashMap<>();
    private final Queue<Vehicle> waitingQueue = new ArrayDeque<>();
    private final int floorCount;
    private final int slotsPerFloor;

    public ParkingLot(int floorCount, int slotsPerFloor) {
        if (floorCount <= 0 || slotsPerFloor <= 0) {
            throw new IllegalArgumentException("Floor count and slots per floor must be positive.");
        }
        this.floorCount = floorCount;
        this.slotsPerFloor = slotsPerFloor;
        for (int floor = 1; floor <= floorCount; floor++) {
            Stack<ParkingSlot> slots = new Stack<>();
            for (int slot = 1; slot <= slotsPerFloor; slot++) {
                slots.push(new ParkingSlot(floor, slot));
            }
            availableByFloor.put(floor, slots);
        }
    }

    public String enter(Vehicle vehicle) {
        String registration = vehicle.getRegistration();
        if (occupiedByRegistration.containsKey(registration) || isWaiting(registration)) {
            return "Vehicle " + registration + " is already parked or waiting.";
        }
        ParkingSlot slot = findAvailableSlot();
        if (slot == null) {
            waitingQueue.offer(vehicle);
            return "Parking lot is full. " + registration
                    + " added to waiting queue at position " + waitingQueue.size() + ".";
        }
        assign(vehicle, slot);
        return "Parked " + registration + " at " + slot + ".";
    }

    public String exit(String registration) {
        String key = normalize(registration);
        ParkingSlot slot = occupiedByRegistration.remove(key);
        if (slot == null) return "No parked vehicle found with registration " + key + ".";
        Vehicle departing = slot.release();
        Vehicle next = waitingQueue.poll();
        if (next != null) {
            slot.park(next);
            occupiedByRegistration.put(next.getRegistration(), slot);
            return departing.getRegistration() + " exited. " + next.getRegistration()
                    + " from the waiting queue was assigned " + slot + ".";
        }
        availableByFloor.get(slot.getFloorNumber()).push(slot);
        return departing.getRegistration() + " exited. " + slot + " is now available.";
    }

    public List<String> getStatusLines() {
        List<String> lines = new ArrayList<>();
        lines.add("Parking lot status:");
        for (int floor = 1; floor <= floorCount; floor++) {
            int available = availableByFloor.get(floor).size();
            lines.add("Floor " + floor + ": " + (slotsPerFloor - available) + "/"
                    + slotsPerFloor + " occupied, " + available + " available");
        }
        lines.add("Vehicles parked: " + occupiedByRegistration.size());
        lines.add("Vehicles waiting: " + waitingQueue.size());
        return lines;
    }

    public List<String> getWaitingRegistrations() {
        List<String> result = new ArrayList<>();
        for (Vehicle vehicle : waitingQueue) result.add(vehicle.toString());
        return result;
    }

    private ParkingSlot findAvailableSlot() {
        for (int floor = 1; floor <= floorCount; floor++) {
            Stack<ParkingSlot> slots = availableByFloor.get(floor);
            if (!slots.isEmpty()) return slots.pop();
        }
        return null;
    }

    private void assign(Vehicle vehicle, ParkingSlot slot) {
        slot.park(vehicle);
        occupiedByRegistration.put(vehicle.getRegistration(), slot);
    }

    private boolean isWaiting(String registration) {
        String key = normalize(registration);
        for (Vehicle vehicle : waitingQueue) {
            if (vehicle.getRegistration().equals(key)) return true;
        }
        return false;
    }

    private String normalize(String registration) {
        if (registration == null || registration.isBlank()) {
            throw new IllegalArgumentException("Registration cannot be empty.");
        }
        return registration.trim().toUpperCase(Locale.ROOT);
    }
}
