
```java
import java.io.*;
import java.util.*;

class Reservation {
    private int id;
    private String name;
    private String date;
    private int numberOfGuests;

    public Reservation(int id, String name, String date, int numberOfGuests) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.numberOfGuests = numberOfGuests;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}


class ReservationSystem {
    private List<Reservation> reservations;
    private int nextId;
    private static final String RESERVATIONS_FILE = "reservations.txt";

    public ReservationSystem() {
        reservations = new ArrayList<>();
        nextId = 1;
        loadReservationsFromFile();
    }

    public Reservation makeReservation(String name, String date, int numberOfGuests) {
        Reservation reservation = new Reservation(nextId++, name, date, numberOfGuests);
        reservations.add(reservation);
        saveReservationsToFile();
        return reservation;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation getReservationById(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }

    public boolean cancelReservation(int id) {
        Reservation reservation = getReservationById(id);
        if (reservation != null) {
            reservations.remove(reservation);
            saveReservationsToFile();
            return true;
        }
        return false;
    }

    private void loadReservationsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String date = data[2].trim();
                int numberOfGuests = Integer.parseInt(data[3].trim());
                Reservation reservation = new Reservation(id, name, date, numberOfGuests);
                reservations.add(reservation);
                nextId = Math.max(nextId, id + 1);
            }
        } catch (IOException e) {
            System.out.println("Error reading reservations file");
        }
    }

    private void saveReservationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESERVATIONS_FILE))) {
            for (Reservation reservation : reservations) {
                writer.write(reservation.getId() + "," + reservation.getName() + "," + reservation.getDate() + "," + reservation.getNumberOfGuests());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing reservations file");
        }
    }
}


class ReservationSystemUI {
    private ReservationSystem reservationSystem = new ReservationSystem();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Make a reservation");
            System.out.println("2. View all reservations");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Date: ");
                    String date = scanner.nextLine();
                    System.out.print("Number of guests: ");
                    int numberOfGuests = scanner.nextInt();
                    scanner.nextLine();

                    Reservation reservation = reservationSystem.makeReservation(name, date, numberOfGuests);
                    System.out.println("Reservation made with ID " + reservation.getId());
                    break;
                case 2:
                    System.out.println("Reservations:");
                    for (Reservation r : reservationSystem.getReservations()) {
                        System.out.println(r.getId() + " - " + r.getName() + " - " + r.getDate() + " - " + r.getNumberOfGuests());
                    }
                    break;
                case 3:
                    System.out.print("Reservation ID to cancel: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    if (reservationSystem.cancelReservation(id)) {
                        System.out.println("Reservation canceled");
                    } else {
                        System.out.println("Reservation not found");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        ReservationSystemUI obj = new ReservationSystemUI();
        obj.start();
    }
  