package head_first;

import java.util.Scanner;

public class GameHelper {
    Scanner scan = new Scanner(System.in);
    int gridX;
    int gridY;

    public GameHelper(int gridX, int gridY) {
        // it needs the X and Y length of the grid it'll help
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public int[] getUserGuess() {
        // return a valid [row (int), column (int)] based on the grid it helps
        // if the input is q/Q (stands for quit), it returns {-1, -1}

        // iterate until the user gives a valid guess
        String userGuess;
        do {
            System.out.println("Enter the guess: ");
            userGuess = scan.nextLine();

            // the user quits
            if (userGuess.equalsIgnoreCase("Q")) {
                return new int[]{-1, -1};
            }

        } while (!this.validateGuess(userGuess));

        // parse the row, A->0... B->1...etc
        char letterRow = userGuess.toUpperCase().charAt(0);
        int row = letterRow - 'A';

        // parse the column
        int column = Integer.parseInt(userGuess.substring(1));

        return new int[]{row, column};
    }

    public boolean validateGuess(String userGuess){
        // return true if the input is valid, false otherwise
        // format expected, e.g.: "A4" where A is the row, 4 is the column

        if(userGuess.length() < 2){
            System.out.println("Format not valid");
            return false;
        }

        // validate the row
        char letterRow = userGuess.toUpperCase().charAt(0);
        int row = letterRow - 'A'; // A->row 0... B->row 1...etc
        if(row < 0 || row >= this.gridY){
            System.out.println("Position out of bounds");
            return false;
        }

        // validate the column
        try {
            int column = Integer.parseInt(userGuess.substring(1));
            if (!(column >= 0 && column < this.gridX)) {
                System.out.println("Position out of bounds");
                return false;
            }
            return true;
        }
        catch (NumberFormatException e) {
            System.out.println("Input is not a number");
            return false;
        }

    }
}
