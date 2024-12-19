package head_first;


public class Ship {

    private final int shipLength;
    private final int[] location;
    private int numOfHits = 0;
    private boolean dead = false;

    public Ship(int gridLength, int shipLength) {
        this.shipLength = shipLength;
        // the gridLength + 1 is there because Math.random gives a num from 0 to 0.999...
        int startPosition = (int) (Math.random() * ((gridLength + 1) - this.shipLength));

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
