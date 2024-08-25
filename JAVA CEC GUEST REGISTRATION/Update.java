import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Guest {
    private String name;
    private String email;

    public Guest(String name, String email) {
    this.name = name;
    this.email = email;
}


    // Constructor, getters, and setters omitted for brevity
}

public class Room {
    private int roomNumber;
    private String roomType;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType) {
    this.roomNumber = roomNumber;
    this.roomType = roomType;
    this.isAvailable = true; // Assuming all rooms are initially available
}


    // Constructor, getters, and setters omitted for brevity
}

public class Reservation {
    private int reservationId;
    private Guest guest;
    private Room room;
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    private boolean isPaid;

    public Reservation(Guest guest, Room room, LocalDateTime checkInDateTime, LocalDateTime checkOutDateTime) {
    this.guest = guest;
    this.room = room;
    this.checkInDateTime = checkInDateTime;
    this.checkOutDateTime = checkOutDateTime;
    this.isPaid = false; // Assuming reservations are initially unpaid
}
    public int getReservationId() {
    return reservationId;
}

public void setReservationId(int reservationId) {
    this.reservationId = reservationId;
}


    // Constructor, getters, and setters omitted for brevity
}

public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservation.setReservationId(generateReservationId());
        reservations.add(reservation);
        sendReservationConfirmationEmail(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void updateReservation(Reservation reservation) {
        // Update reservation logic
        sendReservationUpdateEmail(reservation);
    }

    public void cancelReservation(Reservation reservation) {
        reservations.remove(reservation);
        sendReservationCancellationEmail(reservation);
    }

    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate, String roomType) {
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : getAllRooms()) {
            if (room.isAvailable() && room.getRoomType().equals(roomType)) {
                boolean isRoomAvailable = true;

                for (Reservation reservation : reservations) {
                    if (reservation.getRoom().equals(room) &&
                            ((startDate.isAfter(reservation.getCheckInDateTime().toLocalDate()) &&
                                    startDate.isBefore(reservation.getCheckOutDateTime().toLocalDate())) ||
                                    endDate.isAfter(reservation.getCheckInDateTime().toLocalDate()) &&
                                            endDate.isBefore(reservation.getCheckOutDateTime().toLocalDate()))) {
                        isRoomAvailable = false;
                        break;
                    }
                }

                if (isRoomAvailable) {
                    availableRooms.add(room);
                }
            }
        }

        return availableRooms;
    }

    private int generateReservationId() {
        // Logic to generate a unique reservation ID
        return 0;
    }

    private List<Room> getAllRooms() {
        // Logic to fetch all rooms from the database
        return new ArrayList<>();
    }

    private void sendReservationConfirmationEmail(Reservation reservation) {
        // Logic to send email notification to the guest confirming the reservation
    }

    private void sendReservationUpdateEmail(Reservation reservation) {
        // Logic to send email notification to the guest about reservation update
    }

    private void sendReservationCancellationEmail(Reservation reservation) {
        // Logic to send email notification to the guest about reservation cancellation
    }
}

public class PaymentService {
    public boolean processPayment(Reservation reservation, double amount) {
        // Logic to process the payment using a payment gateway
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        ReservationService reservationService = new ReservationService();
        PaymentService paymentService = new PaymentService();
        Guest guest = new Guest("John Doe", "johndoe@example.com");
        Room room = new Room(101, "Standard");
        LocalDateTime checkInDateTime = LocalDateTime.of(2023, 1, 1, 14, 0);
        LocalDateTime checkOutDateTime = LocalDateTime.of(2023, 1, 2, 12, 0);

        Reservation reservation = new Reservation(guest, room, checkInDateTime, checkOutDateTime);
        reservationService.addReservation(reservation);

        boolean paymentStatus = paymentService.processPayment(reservation, 100.0);
        if (paymentStatus) {
            reservation.setPaid(true);
            reservationService.updateReservation(reservation);
        } else {
            reservationService.cancelReservation(reservation);
        }

        List<Reservation> reservations = reservationService.getReservations();
        for (Reservation r : reservations) {
            System.out.println("Guest: " + r.getGuest().getName());
            System.out.println("Room: " + r.getRoom().getRoomNumber());
            System.out.println("Check-in: " + r.getCheckInDateTime());
            System.out.println("Check-out: " + r.getCheckOutDateTime());
            System.out.println("Is Paid: " + r.isPaid());
            System.out.println("-------------------------------------");
        }
    }
}
