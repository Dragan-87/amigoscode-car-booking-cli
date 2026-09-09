void main() {

    boolean isExit = false;
    Scanner scanner = new Scanner(System.in);
    int option = 0;

    while (!isExit) {
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
        option = userOptionInput(scanner);
        switch (option) {
            case 1 -> System.out.println("Option 1 wurde gewählt.");
            case 2 -> System.out.println("Option 2 wurde gewählt.");
            case 3 -> System.out.println("Option 2 wurde gewählt.");
            case 4 -> System.out.println("Option 2 wurde gewählt.");
            case 5 -> System.out.println("Option 2 wurde gewählt.");
            case 6 -> System.out.println("Option 2 wurde gewählt.");
            case 7 -> System.out.println("Option 2 wurde gewählt.");
            case 8 -> isExit = true;
            default -> System.out.println("Keine option");
        }
    }
}

public int userOptionInput(Scanner scanner) {
    try {
        System.out.print("Enter your option: ");
        return scanner.nextInt();
    } catch (InputMismatchException e) {
        System.out.println("The given input is not a number.");
        scanner.next();
        return userOptionInput(scanner);
    }
}