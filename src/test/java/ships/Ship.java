package ships;

public abstract class Ship {

    private final int shipLength;
    private final String name;
    private int numOfHits = 0;
    private boolean dead = false;


    public Ship(int shipLength, String name) {
        this.shipLength = shipLength;
        this.name = name;
    }

    // Increase hits and check if the ship is sunk
    public void increaseNumOfHits() {
        this.numOfHits++;

        if (this.numOfHits == this.shipLength) {
            this.dead = true;
            this.sunk();
        }
    }

    // Abstract method to handle sinking logic
    public abstract void sunk();

    // Getters
    public boolean isDead() {
        return dead;
    }

    public String getName() {
        return name;
    }

    public int getShipLength() {
        return shipLength;
    }

    public int getNumOfHits() {
        return numOfHits;
    }

    @Override
    public String toString() {
        return "Ship{name='" + name + "', length=" + shipLength + ", hits=" + numOfHits + ", dead=" + dead + "}";
    }
}
