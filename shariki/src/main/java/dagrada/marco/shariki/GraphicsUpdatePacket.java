package dagrada.marco.shariki;

/**
 * Created by Marco on 06/08/2015.
 */
public class GraphicsUpdatePacket {
    private int[][] model;
    private int score;

    public GraphicsUpdatePacket(int[][] model, int score){
        this.model = model;
        this.score = score;
    }

    public int[][] getModel() {
        return model;
    }

    public int getScore() {
        return score;
    }
}
