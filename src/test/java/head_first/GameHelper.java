package head_first;

import java.util.Scanner;

public class GameHelper {
    Scanner scan = new Scanner(System.in);
    int gridLength;

    public GameHelper(int gridLength) {
        this.gridLength = gridLength;
    }

    public int getUserGuess() {

        System.out.println("Enter your guess: ");
        String userGuess = scan.nextLine();
        while (!this.validateGuess(userGuess)) {
            System.out.println("Enter your guess: ");
            userGuess = scan.nextLine();
        }

        return Integer.parseInt(userGuess);
    }

    public boolean validateGuess(String userGuess){

        try {
            int guess = Integer.parseInt(userGuess);
            if (!(guess >= 0 && guess < this.gridLength)) {
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
