package battleship;

public class Ship {
    private ShipNames name;
    private int length;
    boolean placed;

    public enum ShipNames{
        CARRIER ("Aircraft Carrier"),
        BATTLESHIP ("Battleship"),
        SUBMARINE ("Submarine"),
        CRUISER ("Cruiser"),
        DESTROYER ("Destroyer");
        private final String name;

        ShipNames(String s) {
            this.name = s;
        }

        public String getName() {
            return name;
        }
    }

    public Ship(ShipNames name, int length) {
        this.name = name;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public ShipNames getName() {
        return name;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
