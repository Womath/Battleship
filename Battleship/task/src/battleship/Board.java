package battleship;

public class Board {
    String name;
    String[][] gameBoard;
    int shipsPlaced = 0;

    public Board(String name){
        this.name = name;
        gameBoard = new String[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                gameBoard[x][y] = "~";
            }
        }

    }
    public String getName(){
        return this.name;
    }

    public void setShipsPlaced() {
        this.shipsPlaced += 1;
    }

    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int x = 0; x < 10; x++) {
            char c = 'A';
            System.out.print((char) (c + x) + " ");
            for (int y = 0; y < 10; y++) {
                System.out.print(gameBoard[x][y] + " ");
            }
            System.out.println();
        }
    }

    public String getCharacterByCoordinate (int x, int y) {
        return this.gameBoard[x][y];
    }

    public void setGameBoardByCoordinate (int[] coordinate, String s) {
        this.gameBoard[coordinate[0]][coordinate[1]] = s;
    }

    public void changeBoardByCoordinate (int[] coordinate, Board fogOfWar) {
        if (this.gameBoard[coordinate[0]][coordinate[1]].equals("O")) {
            this.gameBoard[coordinate[0]][coordinate[1]] = "X";
            fogOfWar.setGameBoardByCoordinate(coordinate, this.gameBoard[coordinate[0]][coordinate[1]]);
            if (!checkSurroundings(coordinate)) {
                System.out.println("You hit a ship!\n");
            } else {
                System.out.println("You sank a ship!\n");
                shipsPlaced -= 1;
            }

        } else if (this.gameBoard[coordinate[0]][coordinate[1]].equals("X")) {
            fogOfWar.printBoard();
            System.out.println("You hit a ship!\n");
        } else {
            this.gameBoard[coordinate[0]][coordinate[1]] = "M";
            fogOfWar.setGameBoardByCoordinate(coordinate, this.gameBoard[coordinate[0]][coordinate[1]]);
            System.out.println("You missed!\n");
        }


    }

    public void placeShip(int[] firstCoordinate, int[] secondCoordinate) {
        int xMin = Math.min(firstCoordinate[0], secondCoordinate[0]);
        int xMax = Math.max(firstCoordinate[0], secondCoordinate[0]);
        int yMin = Math.min(firstCoordinate[1], secondCoordinate[1]);
        int yMax = Math.max(firstCoordinate[1], secondCoordinate[1]);

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                this.gameBoard[x][y] = "O";
            }
        }
    }

    private boolean checkSurroundings(int[] coordinate) {
        int sum = 0;
        int x = coordinate[0];
        int y = coordinate[1];

        for (int i = x; i >= 0; i--) {
            if (this.gameBoard[i][y].equals("O")) {
                sum += 1;
            } else if (this.gameBoard[i][y].equals("~")) {
                break;
            }
        }

        for (int i = x; i <= 9; i++) {
            if (this.gameBoard[i][y].equals("O")) {
                sum += 1;
            } else if (this.gameBoard[i][y].equals("~")) {
                break;
            }
        }

        for (int i = y; i >= 0; i--) {
            if (this.gameBoard[x][i].equals("O")) {
                sum += 1;
            } else if (this.gameBoard[x][i].equals("~")) {
                break;
            }
        }

        for (int i = y; i <= 9; i++) {
            if (this.gameBoard[x][i].equals("O")) {
                sum += 1;
            } else if (this.gameBoard[x][i].equals("~")) {
                break;
            }
        }

        return sum == 0;

    }
}
