package com.aman.parking;

import java.util.Locale;
import java.util.Objects;

public final class Vehicle {
    private final String registration;
    private final String type;

    public Vehicle(String registration, String type) {
        if (registration == null || registration.isBlank()) {
            throw new IllegalArgumentException("Registration cannot be empty.");
        }
        this.registration = registration.trim().toUpperCase(Locale.ROOT);
        this.type = (type == null || type.isBlank())
                ? "CAR" : type.trim().toUpperCase(Locale.ROOT);
    }

    public String getRegistration() { return registration; }
    public String getType() { return type; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vehicle vehicle)) return false;
        return registration.equals(vehicle.registration);
    }

    @Override
    public int hashCode() { return Objects.hash(registration); }

    @Override
    public String toString() { return registration + " (" + type + ")"; }
}
