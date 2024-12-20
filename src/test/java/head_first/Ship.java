package head_first;


public class Ship {

    private final int shipLength;
    private final String name;
    private int numOfHits = 0;
    private boolean dead = false;

    public Ship(int shipLength, String name) {
        this.shipLength = shipLength;
        this.name = name;
    }

    public void increaseNumOfHits() {
        this.numOfHits ++;

        if (this.numOfHits == this.shipLength) {
            this.dead = true;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public String getName() {
        return name;
    }
}
