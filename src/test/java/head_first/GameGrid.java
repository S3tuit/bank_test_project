package head_first;


import ships.ShipOfFive;
import ships.ShipOfOne;
import ships.ShipOfThree;

public class GameGrid {

    int gridX = 7;
    int gridY = 5;
    Cell[][] grid;
    int guessesNeeded = 0;
    GameHelper gameHelper;
    int aliveShips = 0;
    boolean quitGame = false;

    public GameGrid() {

        // initialize the grid with empty cells
        grid = new Cell[gridY][gridX];
        for (int i = 0; i < gridY; i++) {
            for (int j = 0; j < gridX; j++) {
                grid[i][j] = new Cell();
            }
        }

        // initialize the gameHelper
        gameHelper = new GameHelper(gridX, gridY);
    }

    public void createShips() {
        // create ships, and update aliveShips
        // for now, they have a fixed position
        ShipOfThree ship_3 = new ShipOfThree();
        aliveShips++;
        for (int i = 0; i < 3; i++) {
            this.grid[2][i].addShipReference(ship_3);
        }

        ShipOfOne ship_1 = new ShipOfOne();
        aliveShips++;
        this.grid[this.gridY-1][0].addShipReference(ship_1);

        ShipOfFive ship_5 = new ShipOfFive();
        aliveShips++;
        for (int idx = 0; idx < this.gridY; idx++) {
            this.grid[idx][this.gridX-1].addShipReference(ship_5);
        }

    }

    public String guess() {
        // print the grid at the start with some space
        System.out.println();
        this.printGrid();
        System.out.println();

        String result = "Miss";
        boolean dead = false;

        // iterate until the user gives in input a cell never guessed before
        int row, col;
        do {
            int[] guess = gameHelper.getUserGuess();
            row = guess[0];
            col = guess[1];

            // the user quits
            if (row == -1 && col == -1) {
                this.userQuit();
                return "Bye";
            }

            // Check if the cell has already been guessed
        } while (this.grid[row][col].isAlreadyGuessed());

        // mark the cell as guessed
        grid[row][col].guessedByUser();

        // check if the user hit a ship
        if (grid[row][col].hasShip()) {
            grid[row][col].getReferencedShip().increaseNumOfHits();
            result = "Hit";

            // check if the ship is dead and update aliveShips
            if (grid[row][col].getReferencedShip().isDead()) {
                aliveShips--;
                result = "Dead";
            }
        }

        this.increaseGuessesNeeded();
        System.out.println(result);

        return result;

    }

    public int getAliveShips() {
        return aliveShips;
    }

    public void printGrid() {
        // Print the numbers above the column
        System.out.print("   "); // Padding for row labels
        for (int col = 0; col < gridX; col++) {
            System.out.print(col + "   ");
        }
        System.out.println();

        // Print the rows
        for (int row = 0; row < gridY; row++) {
            // Print the letter next to the start of each row
            System.out.print((char) ('A' + row) + " "); // Convert row index to letter

            // Print the grid cells
            for (int col = 0; col < gridX; col++) {
                if (grid[row][col].isAlreadyGuessed() && grid[row][col].hasShip()) {
                    System.out.print("|!| ");
                } else if (grid[row][col].isAlreadyGuessed()) {
                    System.out.print("|X| ");
                } else {
                    System.out.print("|O| ");
                }
            }
            System.out.println(); // Move to the next line after each row
        }
    }



    private void increaseGuessesNeeded() {
        guessesNeeded++;
    }


    public void startGame() {
        // the game ends when there are no more alive ships or the input is q/Q to quit
        do{
            this.guess();
        } while(this.aliveShips != 0 && !this.quitGame);

        if (this.quitGame) {
            System.out.println("You have quit the game after " + this.guessesNeeded + " guesses :(");
        } else {System.out.println("You won in " + this.guessesNeeded + " guesses!");}

    }

    public void userQuit() {
        this.quitGame = true;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }
}
