package head_first;

public class Cell{
    private boolean alreadyGuessed;
    private Ship referencedShip;

    public Cell() {
        this.alreadyGuessed = false;
        this.referencedShip = null;
    }

    public void addShipReference(Ship ship){
        referencedShip = ship;
    }

    public void guessedByUser(){
        this.alreadyGuessed = true;
    }

    public boolean hasShip(){
        return referencedShip != null;
    }

    public boolean isAlreadyGuessed() {
        return alreadyGuessed;
    }

    public Ship getReferencedShip() {
        return referencedShip;
    }
}
