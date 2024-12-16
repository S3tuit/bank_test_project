package head_first;


public class Ship {

    private final int shipLength;
    private final int[] location;
    private int numOfHits = 0;
    private boolean dead = false;

    public Ship(int gridLength, int shipLength) {
        this.shipLength = shipLength;
        int startPosition = (int) Math.round(Math.random() * (gridLength - this.shipLength));
        location = new int[shipLength];

        for (int i = 0; i < this.shipLength; i++) {
            location[i] = startPosition + i;
        }
    }

    public int[] getLocation() {
        return location;
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
}
