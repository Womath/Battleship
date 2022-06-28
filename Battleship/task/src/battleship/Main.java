package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Board playerOne = new Board("Player 1");
        Board p1FogOfWar = new Board("Player 1");
        Board playerTwo = new Board("Player 2");
        Board p2FogOfWar = new Board("Player 2");

        placeShipsOnBoard(playerOne);

        promptEnterKey();


        placeShipsOnBoard(playerTwo);


        if (playerOne.shipsPlaced == 5 && playerTwo.shipsPlaced == 5) {
            promptEnterKey();
            while (playerOne.shipsPlaced != 0 || playerTwo.shipsPlaced != 0) {
                p1FogOfWar.printBoard();
                System.out.print("---------------------\n");
                playerOne.printBoard();
                shoot(playerTwo, p1FogOfWar);
                if (playerTwo.shipsPlaced == 0) {
                    System.out.println("You sank the last ship, " + playerOne.getName() + ". You won. Congratulations!");
                    break;
                }
                promptEnterKey();

                p2FogOfWar.printBoard();
                System.out.print("---------------------\n");
                playerTwo.printBoard();
                shoot(playerOne, p2FogOfWar);
                if (playerOne.shipsPlaced == 0) {
                    System.out.println("You sank the last ship, " + playerTwo.getName() + ". You won. Congratulations!");
                    break;
                }
                promptEnterKey();
            }
        }
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void placeShipsOnBoard(Board board) {
        Ship aircraftCarrier = new Ship(Ship.ShipNames.CARRIER, 5);
        Ship battleship = new Ship(Ship.ShipNames.BATTLESHIP, 4);
        Ship submarine = new Ship(Ship.ShipNames.SUBMARINE, 3);
        Ship cruiser = new Ship(Ship.ShipNames.CRUISER, 3);
        Ship destroyer = new Ship(Ship.ShipNames.DESTROYER, 2);

        System.out.println(board.getName() + ", place your ships on the game field\n");
        board.printBoard();
        placeShip(aircraftCarrier, board);
        placeShip(battleship, board);
        placeShip(submarine, board);
        placeShip(cruiser, board);
        placeShip(destroyer, board);
    }



    public static int convertCodeToNumber(char c) {
        switch (c) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'J':
                return 9;
            default:
                return -1;
        }
    }

    public static int[] parseCoordinates(String coordinates) {
        int[] coordinate = new int[2];

        if (coordinates.length() > 2) {
            if (convertCodeToNumber(coordinates.charAt(0)) != -1) {
                coordinate[0] = convertCodeToNumber(coordinates.charAt(0));
                if (Integer.parseInt(String.valueOf(coordinates.charAt(1))) * 10 + Integer.parseInt(String.valueOf(coordinates.charAt(2))) == 10) {
                    coordinate[1] = 9;
                } else {
                    coordinate[1] = -1;
                }
            }
        } else {
            for (int i = 0; i < coordinates.length(); i++) {
                if (coordinates.charAt(i) >= 'A' && coordinates.charAt(i) <= 'J') {
                    coordinate[i] = convertCodeToNumber(coordinates.charAt(i));
                } else if (coordinates.charAt(i) >= (char) 48 && coordinates.charAt(i) < (char) 58) {
                    coordinate[i] = (Integer.parseInt(String.valueOf(coordinates.charAt(i)))) - 1;
                } else {
                    coordinate[i] = -1;
                }
            }
        }
        return coordinate;
    }

    public static boolean checkLength(int[] firstCoordinate, int[] secondCoordinate, int length) {
        return Math.abs(firstCoordinate[0] - secondCoordinate[0]) + 1  == length || Math.abs(firstCoordinate[1] - secondCoordinate[1]) + 1  == length;
    }

    public static boolean checkIfValid(int[] firstCoordinate, int[] secondCoordinate) {
        return firstCoordinate[0] == secondCoordinate[0] || firstCoordinate[1] == secondCoordinate[1];
    }

    public static boolean checkAvailability(int[] firstCoordinate, int[] secondCoordinate, Board gameBoard) {
        int xMin = Math.min(firstCoordinate[0], secondCoordinate[0]);
        int xMax = Math.max(firstCoordinate[0], secondCoordinate[0]);
        int yMin = Math.min(firstCoordinate[1], secondCoordinate[1]);
        int yMax = Math.max(firstCoordinate[1], secondCoordinate[1]);

        boolean hasSpace = false;

        if (xMin != 0) {
            xMin -= 1;
        }
        if (xMax != 9) {
            xMax += 1;
        }

        if (yMin != 0) {
            yMin -= 1;
        }
        if (yMax != 9) {
            yMax += 1;
        }

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                if (gameBoard.getCharacterByCoordinate(x, y).equals("~")) {
                    hasSpace = true;
                } else {
                    hasSpace = false;
                    System.out.println("Error! You placed it too close to another ship. Try again:\n");
                    break;
                }
            }
            if (!hasSpace) {
                break;
            }
        }
        return hasSpace;
    }

    public static void placeShip(Ship ship, Board board) {
        while(!ship.isPlaced()) {
            System.out.println("Enter the coordinates of the " + ship.getName().getName() + " (" + ship.getLength() + " cells):");
            Scanner scanner = new Scanner(System.in);
            String[] coordinates = scanner.nextLine().split(" ");
            int[] firstCoordinate = parseCoordinates(coordinates[0]);
            int[] secondCoordinate = parseCoordinates(coordinates[1]);
            boolean coordinations = true;

            for (int i = 0; i < firstCoordinate.length; i++) {
                if (firstCoordinate[i] == -1 || secondCoordinate[i] == -1) {
                    coordinations = false;
                }
            }

            if (!coordinations) {
                System.out.println("Error! Coordinations are invalid! Try again:\n");
            } else if (!checkIfValid(firstCoordinate, secondCoordinate)) {
                System.out.println("Error! Invalid placement! Try again:\n");
            } else if (!checkLength(firstCoordinate, secondCoordinate, ship.getLength())) {
                System.out.println("Error! Wrong length of the ship! Try again:\n");
            } else if (checkAvailability(firstCoordinate, secondCoordinate, board)) {
                board.placeShip(firstCoordinate, secondCoordinate);
                board.printBoard();
                ship.setPlaced(true);
                board.setShipsPlaced();
            }
        }
    }

    public static void shoot(Board board, Board fogOfWar) {
        Scanner scanner = new Scanner(System.in);
        boolean coordinate = false;
        System.out.println("\n" + fogOfWar.getName() + ", it's your turn:");

        while(!coordinate) {
            int[] target = parseCoordinates(scanner.nextLine());
            coordinate = true;

            for (int j : target) {
                if (j == -1) {
                    coordinate = false;
                    break;
                }
            }

            if (!coordinate) {
                System.out.println("Error! You entered wrong coordinates! Try again:\n");
            } else {
                board.changeBoardByCoordinate(target, fogOfWar);
            }
        }
    }
}
