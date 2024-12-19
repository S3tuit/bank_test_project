package head_first;

import java.util.Scanner;

public class GameGrid {

    int gridLength;
    Ship[] grid;

    // false = the user never guessed it before, true otherwise
    boolean[] gridHistory;
    int guessesNeeded = 0;
    GameHelper gameHelper;

    public GameGrid(int gridLength) {
        this.gridLength = gridLength;
        grid = new Ship[gridLength];
        gameHelper = new GameHelper(gridLength);
        gridHistory = new boolean[gridLength];
    }

    public void createShips(){
        Ship ship = new Ship(this.gridLength, 3);

        for (int idx: ship.getLocation()) {
            grid[idx] = ship;
        }
    }

    public String guess() {
        String result = "Miss";
        boolean dead = false;
        int guess = gameHelper.getUserGuess();

        if (this.gridHistory[guess]) {
            System.out.println("You already guessed that position...");
            return "Repeated";
        }

        if (grid[guess] != null) {
            grid[guess].increaseNumOfHits();
            dead = grid[guess].isDead();
            grid[guess] = null;
            result = "Hit";
        }

        if (dead) {result = "Dead";}

        this.increaseGuessesNeeded();
        this.gridHistory[guess] = true;
        System.out.println(result);
        return result;

    }


    private void increaseGuessesNeeded() {
        guessesNeeded++;
    }


    public void startGame() {
        String guess = "";
        do{
            guess = this.guess();
        } while(guess != "Dead");

        System.out.println("You won in " + this.guessesNeeded + " guesses!");
    }

    public Ship[] getGrid() {
        return grid;
    }





}
