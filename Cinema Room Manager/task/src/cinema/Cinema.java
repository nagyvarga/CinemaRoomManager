package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void initialRoom(char[][] room) {
        for (char[] rowInRoom : room) {
            Arrays.fill(rowInRoom, 'S');
        }
    }

    public static void addReservation(char[][] room, int seatRow, int seatColumn) {
        if (seatRow > 0 && seatRow <= room.length && seatColumn > 0 && seatColumn <= room[0].length) {
            room[seatRow - 1][seatColumn - 1] = 'B';
        }
    }

    public static void printRoom(char[][] room) {
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int column = 1; column <= room[0].length; column++) {
            System.out.printf("%d ", column);
        }
        System.out.print("\n");
        int row = 0;
        for (char[] rowInRoom : room) {
            row++;
            System.out.printf("%d ", row);
            for (char seat : rowInRoom) {
                System.out.printf("%c ", seat);
            }
            System.out.print("\n");
        }
    }

    public static int totalIncome(int row, int column) {
        final int eight = 8;
        final int ten = 10;
        final int limit = 60;
        int income;
        int roomSize = row * column;

        if (roomSize <= limit) {
            income = ten * roomSize;
        } else {
            income = ten * (row / 2) * column + eight * (row - row / 2) * column;
        }
        return income;
    }

    public static int ticketPrice(int row, int column, int seatRow) {
        final int eight = 8;
        final int ten = 10;
        final int limit = 60;
        int price;
        int roomSize = row * column;

        if (roomSize <= limit) {
            price = ten;
        } else {
            price = seatRow <= row / 2 ? ten : eight;
        }

        return price;
    }


    public static int[] readRoomSize() {
        Scanner scanner = new Scanner(System.in);
        int[] roomSize = new int[2];

        System.out.println("Enter the number of rows:");
        roomSize[0] = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        roomSize[1] = scanner.nextInt();

        return roomSize;
    }

    public static int[] readSeatPosition() {
        Scanner scanner = new Scanner(System.in);
        int[] seatPosition = new int[2];

        System.out.println("\nEnter a row number:");
        seatPosition[0] = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        seatPosition[1] = scanner.nextInt();

        return seatPosition;
    }

    public static void printMenu() {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static int readMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        while (choice < 0  || choice > 3) {
            choice = scanner.nextInt();
        }
        return choice;
    }

    public static boolean checkOutOfBound(int row, int column, int seatRow, int seatColumn) {
        return seatRow < 1 || seatRow > row || seatColumn < 1 || seatColumn > column;
    }

    public static boolean checkBookedSeat(char[][] room, int seatRow, int seatColumn) {
        return room[seatRow - 1][seatColumn - 1] == 'B';
    }

    public static int buyTicket(char[][] room, int row, int column) {
        int[] seatPosition = readSeatPosition();
        int seatRow = seatPosition[0];
        int seatColumn = seatPosition[1];
        boolean error = true;
        while (error) {
            if (checkOutOfBound(row, column, seatRow, seatColumn)) {
                System.out.println("\nWrong input!");
            } else if (checkBookedSeat(room, seatRow, seatColumn)) {
                System.out.println("\nThat ticket has already been purchased!");
            } else {
                error = false;
            }
            if (error) {
                seatPosition = readSeatPosition();
                seatRow = seatPosition[0];
                seatColumn = seatPosition[1];
            }
        }
        addReservation(room, seatRow, seatColumn);
        int ticketPrice = ticketPrice(row, column, seatRow);
        System.out.printf("\nTicket price: $%d\n", ticketPrice);
        return ticketPrice;
    }

    public static int countBookedSeats(char[][] room) {
        int count = 0;
        for (char[] row : room) {
            for (char seat : row) {
                count += seat == 'B' ? 1 : 0;
            }
        }
        return count;
    }

    public static void printStatistics(char[][] room, int row, int column, int income) {
        int purchasedSeats = countBookedSeats(room);
        System.out.printf("\nNumber of purchased tickets: %d\n", purchasedSeats);
        System.out.printf("Percentage: %.2f%%\n", purchasedSeats * 100.0 / (row * column));
        System.out.printf("Current income: $%d\n", income);
        System.out.printf("Total income: $%d\n", totalIncome(row, column));
    }

    public static void main(String[] args) {
        // Write your code here

        int[] roomSize = readRoomSize();
        int row = roomSize[0];
        int column = roomSize[1];

        int income = 0;

        char[][] room = new char[row][column];

        initialRoom(room);

        printMenu();
        int item = readMenu();

        while (item != 0) {
            switch (item) {
                case 1:
                    printRoom(room);
                    break;
                case 2:
                    income += buyTicket(room, row, column);
                    break;
                case 3:
                    printStatistics(room, row, column, income);
                    break;
                default:
                    break;
            }
            printMenu();
            item = readMenu();
        }
    }
}