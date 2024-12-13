package head_first;

import java.util.Scanner;

public class Player {

    int score = 0;
    String name;

    public Player(String name) {
        this.name = name;
    }

    public void guessTheNumber(GuessGame guessGame) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Hey, " + name + " guess the number from " + guessGame.getLowest() + " to " + guessGame.getHighest() + ": ");

        int guessedNum = Integer.parseInt(sc.nextLine());
        score = guessGame.getHighest() - Math.abs(guessGame.getNumToGuess() - guessedNum);
    }
}
