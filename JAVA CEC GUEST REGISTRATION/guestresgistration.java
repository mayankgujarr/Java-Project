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

    // Getters and setters for the Reservation class (as before)...
}

class ReservationSystem {
    // Existing methods and variables (as before)...

    // New method to update reservation information
    public boolean updateReservation(int id, String name, String date, int numberOfGuests) {
        Reservation reservation = getReservationById(id);
        if (reservation != null) {
            reservation.setName(name);
            reservation.setDate(date);
            reservation.setNumberOfGuests(numberOfGuests);
            saveReservationsToFile();
            return true;
        }
        return false;
    }

    // New method to search for reservations by name, date, or number of guests
    public List<Reservation> searchReservations(String searchCriteria) {
        List<Reservation> searchResults = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getName().contains(searchCriteria) ||
                reservation.getDate().contains(searchCriteria) ||
                String.valueOf(reservation.getNumberOfGuests()).contains(searchCriteria)) {
                searchResults.add(reservation);
            }
        }
        return searchResults;
    }

    // New method to check reservation availability for a given date
    public boolean isDateAvailable(String date) {
        for (Reservation reservation : reservations) {
            if (reservation.getDate().equals(date)) {
                return false; // Date is already reserved
            }
        }
        return true; // Date is available
    }
}

class ReservationSystemUI {
    // Existing methods and variables (as before)...

    public void start() {
        // Existing menu options (as before)...

        while (true) {
            // ... (as before)

            switch (choice) {
                // ... (as before)

                case 5:
                    System.out.print("Enter a search criteria (name, date, or number of guests): ");
                    String searchCriteria = scanner.nextLine();
                    List<Reservation> searchResults = reservationSystem.searchReservations(searchCriteria);

                    if (searchResults.isEmpty()) {
                        System.out.println("No matching reservations found.");
                    } else {
                        System.out.println("Matching Reservations:");
                        for (Reservation r : searchResults) {
                            System.out.println(r.getId() + " - " + r.getName() + " - " + r.getDate() + " - " + r.getNumberOfGuests());
                        }
                    }
                    break;

                case 6:
                    System.out.print("Enter a date to check availability: ");
                    String checkDate = scanner.nextLine();
                    if (reservationSystem.isDateAvailable(checkDate)) {
                        System.out.println("Date is available for reservations.");
                    } else {
                        System.out.println("Date is already reserved.");
                    }
                    break;
                // ... (as before)
            }
        }
    }
    
    public static void main(String[] args) {
        ReservationSystemUI obj = new ReservationSystemUI();
        obj.start();
    }
}
