package ships;

public class ShipOfOne extends Ship {

    public ShipOfOne() {
        super(1, "Stinger");
    }

    @Override
    public void sunk() {
        System.out.println("The Stinger has been neutralized! Its sharp bite is no more!");
    }
}
