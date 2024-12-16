package head_first;

import java.util.Scanner;

public class GameGrid {

    int gridLength;
    Ship[] grid;
    Scanner scan = new Scanner(System.in);

    public GameGrid(int gridLength) {
        this.gridLength = gridLength;
        grid = new Ship[gridLength];
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

        System.out.println("Enter your guess: ");
        String userGuess = scan.nextLine();
        while (!this.validateGuess(userGuess)) {
            System.out.println("Enter your guess: ");
            userGuess = scan.nextLine();
        }

        int guess = Integer.parseInt(userGuess);
        if (grid[guess] != null) {
            grid[guess].increaseNumOfHits();
            dead = grid[guess].isDead();
            grid[guess] = null;
            result = "Hit";
        }

        if (dead) {result = "Dead";}

        System.out.println(result);
        return result;

    }

    public boolean validateGuess(String userGuess){
        try {
            int guess = Integer.parseInt(userGuess);
            return true;
        }
        catch (NumberFormatException e) {
            System.out.println("Input is not a number");
            return false;
        }
    }


    public Ship[] getGrid() {
        return grid;
    }





}
