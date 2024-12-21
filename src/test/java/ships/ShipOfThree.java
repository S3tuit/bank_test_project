package ships;

public class ShipOfThree extends Ship {
    public ShipOfThree() {
        super(3, "Tempest");
    }

    @Override
    public void sunk() {
        System.out.println("The Tempest has calmed forever. The storm has passed!");
    }
}
