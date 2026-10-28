package com.aman.parking;

import java.util.List;
import java.util.Scanner;

public final class ParkingLotApp {
    private ParkingLotApp() {}

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(2, 2);
        System.out.println("=== Parking Lot System ===");
        System.out.println("Floors: 2 | Slots per floor: 2");
        printHelp();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("\n> ");
                if (!scanner.hasNextLine()) break;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                String command = parts[0].toUpperCase();

                try {
                    switch (command) {
                        case "ENTER" -> {
                            if (parts.length < 2 || parts.length > 3) {
                                System.out.println("Usage: ENTER <registration> [vehicle-type]");
                                continue;
                            }
                            String type = parts.length == 3 ? parts[2] : "CAR";
                            System.out.println(lot.enter(new Vehicle(parts[1], type)));
                        }
                        case "EXIT" -> {
                            if (parts.length != 2) {
                                System.out.println("Usage: EXIT <registration>");
                                continue;
                            }
                            System.out.println(lot.exit(parts[1]));
                        }
                        case "STATUS" -> lot.getStatusLines().forEach(System.out::println);
                        case "WAITLIST" -> printWaitlist(lot.getWaitingRegistrations());
                        case "HELP" -> printHelp();
                        case "QUIT", "EXITAPP" -> {
                            System.out.println("Goodbye.");
                            return;
                        }
                        default -> System.out.println("Unknown command. Type HELP to see commands.");
                    }
                } catch (IllegalArgumentException | IllegalStateException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }

    private static void printWaitlist(List<String> waiting) {
        if (waiting.isEmpty()) {
            System.out.println("Waiting list is empty.");
            return;
        }
        System.out.println("Waiting list (FIFO):");
        for (int i = 0; i < waiting.size(); i++) {
            System.out.println((i + 1) + ". " + waiting.get(i));
        }
    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  ENTER <registration> [vehicle-type]");
        System.out.println("  EXIT <registration>");
        System.out.println("  STATUS");
        System.out.println("  WAITLIST");
        System.out.println("  HELP");
        System.out.println("  QUIT");
    }
}
