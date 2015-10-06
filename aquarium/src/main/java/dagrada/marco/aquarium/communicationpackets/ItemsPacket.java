package dagrada.marco.aquarium.communicationpackets;

/**
 * Created by Marco on 06/10/2015.
 */
public class ItemsPacket {

    private int[][] grid;

    public ItemsPacket(int[][] backgroundGrid){
        this.grid = backgroundGrid;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
