package ships;

public class ShipOfFive extends Ship{

    public ShipOfFive() {
        super(5, "Kraken's Fury");
    }

    @Override
    public void sunk() {
        System.out.println("Kraken's Fury has been subdued! The beast retreats to the abyss!");
    }
}
