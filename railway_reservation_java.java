import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Train {
    private String name;
    private String time;
    private int passengerStrength;
    private int trainNumber;

    public Train(String name, String time, int passengerStrength, int trainNumber) {
        this.name = name;
        this.time = time;
        this.passengerStrength = passengerStrength;
        this.trainNumber = trainNumber;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getPassengerStrength() {
        return passengerStrength;
    }

    public int getTrainNumber() {
        return trainNumber;
    }
}

class Ticket {
    private Train train;
    private List<String> passengerNames;
    private List<Integer> seatNumbers;

    public Ticket(Train train, List<String> passengerNames, List<Integer> seatNumbers) {
        this.train = train;
        this.passengerNames = passengerNames;
        this.seatNumbers = seatNumbers;
    }

    public Train getTrain() {
        return train;
    }

    public List<String> getPassengerNames() {
        return passengerNames;
    }

    public List<Integer> getSeatNumbers() {
        return seatNumbers;
    }
}

public class RailwayReservationSystem {
    private static List<Train> trains = new ArrayList<>();
    private static List<Ticket> tickets = new ArrayList<>();
    private static List<String> availableStations = List.of("Delhi", "Jaipur", "Prayagraj", "Mumbai");

    public static void main(String[] args) {
        initializeTrains();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nRailway Reservation System Menu:");
            System.out.println("1. Train Information");
            System.out.println("2. Check Seat Availability");
            System.out.println("3. Book Ticket");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Ticket Display");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    displayTrainInformation();
                    break;
                case 2:
                    checkSeatAvailability(scanner);
                    break;
                case 3:
                    bookTicket(scanner);
                    break;
                case 4:
                    cancelTicket(scanner);
                    break;
                case 5:
                    displayTickets();
                    break;
                case 6:
                    System.out.println("Thank you for using the Railway Reservation System!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeTrains() {
        trains.add(new Train("Mumbai - Delhi", "13:05", 50, 1010));
        trains.add(new Train("Delhi - Jaipur", "7:00", 50, 2013));
        trains.add(new Train("Prayagraj - Delhi", "10:00", 50, 3045));
    }

    private static void displayTrainInformation() {
        System.out.println("\nAvailable Trains:");
        System.out.println("Train Name\tTime\tPassenger Strength\tTrain Number");
        for (Train train : trains) {
            System.out.println(train.getName() + "\t" + train.getTime() + "\t" +
                    train.getPassengerStrength() + "\t" + train.getTrainNumber());
        }
    }

    private static void checkSeatAvailability(Scanner scanner) {
        System.out.print("Enter source station: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination station: ");
        String destination = scanner.nextLine();

        System.out.println("\nAvailable Trains:");
        System.out.println("Train Number\tTrain Name");
        for (Train train : trains) {
            if (canReachDestination(train, source, destination)) {
                System.out.println(train.getTrainNumber() + "\t" + train.getName());
            }
        }
    }

    private static boolean canReachDestination(Train train, String source, String destination) {
        return availableStations.contains(source) && availableStations.contains(destination) &&
                availableStations.indexOf(source) < availableStations.indexOf(destination);
    }

    private static void bookTicket(Scanner scanner) {
        System.out.print("Enter train number: ");
        int trainNumber = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        Train selectedTrain = null;
        for (Train train : trains) {
            if (train.getTrainNumber() == trainNumber) {
                selectedTrain = train;
                break;
            }
        }

        if (selectedTrain == null) {
            System.out.println("Invalid train number.");
            return;
        }

        System.out.print("Enter number of passengers: ");
        int numPassengers = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        if (checkSeatAvailability(selectedTrain, numPassengers)) {
            List<String> passengerNames = new ArrayList<>();
            List<Integer> seatNumbers = new ArrayList<>();

            for (int i = 1; i <= numPassengers; i++) {
                System.out.print("Enter passenger " + i + " name: ");
                passengerNames.add(scanner.nextLine());
                seatNumbers.add(i);
            }

            Ticket ticket = new Ticket(selectedTrain, passengerNames, seatNumbers);
            tickets.add(ticket);
            System.out.println("Ticket booked successfully!");
        } else {
            System.out.println("Ticket booking failed due to unavailability of seats.");
        }
    }

    private static boolean checkSeatAvailability(Train train, int numPassengers) {
        return train.getPassengerStrength() >= numPassengers;
    }

    private static void cancelTicket(Scanner scanner) {
        System.out.print("Enter ticket number: ");
        int ticketNumber = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        boolean ticketFound = false;
        for (Ticket ticket : tickets) {
            if (ticket.hashCode() == ticketNumber) {
                tickets.remove(ticket);
                System.out.println("Ticket canceled successfully!");
                ticketFound = true;
                break;
            }
        }

        if (!ticketFound) {
            System.out.println("Ticket not found.");
        }
    }

    private static void displayTickets() {
        System.out.println("\nBooked Tickets:");
        System.out.println("Ticket Number\tTrain Name\tPassenger Names\tSeat Numbers");
        for (Ticket ticket : tickets) {
            System.out.print(ticket.hashCode() + "\t" + ticket.getTrain().getName() + "\t");

            List<String> passengerNames = ticket.getPassengerNames();
            List<Integer> seatNumbers = ticket.getSeatNumbers();

            for (int i = 0; i < passengerNames.size(); i++) {
                System.out.print(passengerNames.get(i) + " (Seat " + seatNumbers.get(i) + ")");
                if (i < passengerNames.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}
