package head_first;

public class Test {
    public static void main(String[] args) {

        GuessGame game = new GuessGame();
        Player matteo = new Player("Matteo");
        Player erwin = new Player("Erwin");
        Player asta = new Player("Asta");

        game.addPlayer(matteo);
        game.addPlayer(erwin);
        game.addPlayer(asta);

        game.startGame();
        Player winner = game.getWinner();

        if(winner == null) {
            System.out.println("No winner.");
        } else {
            System.out.println("The number was " + game.getNumToGuess());
            System.out.println("Winner is " + winner.name + " with a score of " + winner.score + " points!!!");
        }
    }
}
