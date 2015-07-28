package dagrada.marco.shariki;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Marco on 23/07/2015.
 */
public class GameStatusHandler {

    private static final int MAX_WIDTH = 5;
    private static final int MAX_HEIGTH = 5;

    private int[][] marbles;
    private ArrayList<String> levels;
    private int CURRENT_LEVEL;
    FileInputStream stream;

    public void loadGame(ArrayList<String> levels) throws Exception {

        this.levels = levels;
        loadLevel(0);

    }

    public void loadLevel (int level)throws Exception{
        CURRENT_LEVEL = level;
        marbles = MatrixFileReader.getMatrix(stream = new FileInputStream(levels.get(CURRENT_LEVEL)));
    }

    public void nextLevel() throws Exception {
        loadLevel(getCurrentLevel()+1);
    }

    private void compactMarbles(){
        int buffer;
        for (int i=0; i<marbles.length; i++){
            for (int j=0; j<marbles[i].length;j++){
                if (marbles[i][j] == 0){
                    for (int k=j; k<marbles.length-1;k++){
                        buffer = marbles[i][k];
                        marbles[i][k] = marbles[i][k+1];
                        marbles[i][k+1] = buffer;
                    }
                }
            }
        }
    }

    public void switchMarbles(int marble1X, int marble1Y, int marble2X, int marble2Y){
        int buffer;
        if(!(marble1X <1 || marble1Y <1 || marble2X <1 || marble2Y <1 || marble1X > MAX_WIDTH || marble2X > MAX_WIDTH || marble1Y > MAX_HEIGTH || marble2Y > MAX_HEIGTH)){
            buffer = marbles[marble1X][marble1Y];
            marbles[marble1X][marble1Y] = marbles[marble2X][marble2Y];
            marbles[marble2X][marble2Y] = buffer;
        }
    }

    public int[][] getMarbles() {
        return marbles;
    }

    public int getCurrentLevel() {
        return CURRENT_LEVEL;
    }

}