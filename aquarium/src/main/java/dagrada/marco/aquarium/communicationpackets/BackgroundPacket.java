package dagrada.marco.aquarium.communicationpackets;

/**
 * Created by Marco on 06/10/2015.
 */
public class BackgroundPacket {

    private int[][] grid;

    public BackgroundPacket(int[][] backgroundGrid){
        this.grid = backgroundGrid;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
