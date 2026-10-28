import com.dragan.booking.CarBooking;
import com.dragan.booking.CarBookingService;
import com.dragan.car.Car;
import com.dragan.exceptions.EntityNotFoundException;
import com.dragan.user.User;
import com.dragan.user.UserService;

void main() {
    UserService userService = new UserService();
    CarBookingService carBookingService = new CarBookingService();

    boolean isRunning = true;
    Scanner scanner = new Scanner(System.in);
    int option;
    while (isRunning) {
        System.out.println("""
                
                1 - Book Car
                2 - Delete Booking
                3 - View All User Booked Cars
                4 - View All Bookings
                5 - View Available Cars
                6 - View Available Electric Cars
                7 - View All Users
                8 - Exit
                """);
        try {
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> System.out.println(bookCar(carBookingService, userService, scanner));
                case 2 -> handleDeleteById(scanner, carBookingService);
                case 3 -> printAllUserBookedCars(carBookingService, scanner);
                case 4 -> System.out.println(Arrays.toString(carBookingService.findAll()));
                case 5 -> printCars(carBookingService.getAvailableCars());
                case 6 -> printCars(carBookingService.availableElectricCars());
                case 7 -> printAllUsers(userService);
                case 8 -> isRunning = false;
                default -> System.out.println("Input invalid, pleas try again or exit!");
            }
        } catch (InputMismatchException e) {
            System.out.println("The given input is not a number.");
            scanner.next();
        }
    }
}

public void printAllUsers(UserService userService) {
    User[] users = userService.findAll();
    if (users == null) {
        System.out.println("No users found.");
        return;
    }

    for (User user : users) {
        System.out.println(user);
    }
}

public void printAllUserBookedCars(CarBookingService carBookingService, Scanner scanner) {
    CarBooking[] carBookings = carBookingService.findAllBookingsByUserId(validateUserUuidInput(scanner));
    if (carBookings == null || carBookings.length == 0) {
        System.out.println("No bookings found.");
        return;
    }
    System.out.println(carBookings.length);
    for (CarBooking booking : carBookings) {
        if (booking != null) {
            System.out.println(booking.getCar());
        }
    }

}

public UUID validateUserUuidInput(Scanner scanner) {
    while (true) {
        try {
            System.out.print("Please enter a user id: ");
            return UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Input is not a valid UUID, please try again");
        }
    }
}

public void printCars(Car[] cars) {
    if (cars == null || cars.length == 0) {
        System.out.println("No cars found");
        return;
    }

    for (Car car : cars) {
        System.out.println(car);
    }
}

public UUID validateUUID(Scanner scanenr, String prompt) {
    while (true) {
        try {
            System.out.println(prompt);
            return UUID.fromString(scanenr.nextLine().trim());
        } catch (IllegalArgumentException e) {
            System.out.print("UUID invalid, please try again!");
        }
    }
}

public LocalDate validateDate(Scanner scanner, String prompt) {
    while (true) {
        try {
            System.out.println(prompt);
            return LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Date invalid, please try again!");
        }
    }
}

public CarBooking bookCar(CarBookingService carBookingService, UserService userService, Scanner scanner) {
    System.out.println("AvailableCars: ");
    printCars(carBookingService.getAvailableCars());
    System.out.println();
    System.out.println("Users: ");
    printAllUsers(userService);
    while (true) {
        UUID carUUID = validateUUID(scanner, "Enter car UUID: ");
        UUID userUUID = validateUUID(scanner, "Enter user UUID: ");
        LocalDate startDate = validateDate(scanner, "Enter starting date.");
        LocalDate endDate = validateDate(scanner, "Enter end date.");
        try {
             return carBookingService.bookCar(userUUID, carUUID, startDate, endDate);

        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            System.out.println(e);
        }
    }
}

public void printBookings(CarBooking[] bookings) {
    for (CarBooking booking : bookings) {
        if (booking != null) {
            System.out.println(booking);
        }
    }
}

public void handleDeleteById(Scanner scanner, CarBookingService carBookingService) {
    printBookings(carBookingService.findAll());
    UUID bookingUUID = validateUUID(scanner, "Enter booking UUID");
    try {
        carBookingService.deleteById(bookingUUID);
        System.out.println("Booking deleted.");    }
    catch (EntityNotFoundException e) {
        System.out.println(e);
    }
}