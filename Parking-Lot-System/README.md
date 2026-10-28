# Parking Lot System: Multi-Floor Simulation

A Java console application that simulates a multi-floor parking lot. This is a **recreated portfolio implementation based on the project summary in my resume**, not a recovery of the original source files.

## Features
- Multiple floors with configurable parking capacity
- Stack-based management of available parking slots per floor
- HashMap-based lookup of parked vehicles by registration number
- FIFO queue for vehicles waiting when the lot is full
- Vehicle entry and exit
- Automatic assignment of a newly freed slot to the first waiting vehicle
- Status view showing occupied and available slots

## Data Structures
| Data structure | Purpose |
|---|---|
| `Stack<ParkingSlot>` | Tracks available slots on each floor |
| `HashMap<String, ParkingSlot>` | Looks up active parking assignments |
| `Queue<Vehicle>` (`ArrayDeque`) | Maintains a FIFO waiting list |

## Requirements
- Java 17 or later
- No external dependencies

## Run
From the project root:

```bash
javac -d out src/main/java/com/aman/parking/*.java
java -cp out com.aman.parking.ParkingLotApp
```

## Commands
- `ENTER <registration> [vehicle-type]`
- `EXIT <registration>`
- `STATUS`
- `WAITLIST`
- `HELP`
- `QUIT`

## Design
Each floor owns a stack of available slots. A HashMap tracks current parking assignments. When all slots are occupied, incoming vehicles join a FIFO waiting queue. When a vehicle exits, the first waiting vehicle receives the freed slot immediately, if one is waiting.

## Scope
This is an educational console simulation. It does not implement payments, authentication, persistence, reservations, sensors, or a graphical interface.

## Portfolio note
This is a recreated implementation prepared from the project description in my resume because the original project files were unavailable. Review, run, and understand the code before publishing. Do not describe this as the original source if it is not.
