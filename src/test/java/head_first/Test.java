package head_first;

public class Test {
    public static void main(String[] args) {

        GameGrid gameGrid = new GameGrid(7);
        gameGrid.createShips();

        for(Ship ship: gameGrid.getGrid()) {
            if (ship != null) {
                for(int n: ship.getLocation()){
                    System.out.print(n + " ");
                }
                System.out.println();
            }
        }

        do{} while(gameGrid.guess() != "Dead");


    }
}