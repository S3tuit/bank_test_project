package head_first;

import java.util.ArrayList;

public class GuessGame {

    private int numToGuess = 0;
    private int lowest = 0;
    private int highest = 100;
    private ArrayList<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame() {
        numToGuess = (int) (Math.random() * (highest - lowest) + lowest);
        System.out.println("Guess the number STAAAAAAAAARTED!!!");

        for (Player player : players) {
            player.guessTheNumber(this);
        }
    }

    public Player getWinner() {
        int currentScore = 0;
        Player winner = null;

        for (Player player : players) {
            if(player.score > currentScore) {
                currentScore = player.score;
                winner = player;
            }
        }

        return winner;
    }

    public int getNumToGuess() {
        return numToGuess;
    }

    public int getLowest() {
        return lowest;
    }

    public int getHighest() {
        return highest;
    }
}
