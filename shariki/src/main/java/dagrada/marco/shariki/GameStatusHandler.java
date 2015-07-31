package dagrada.marco.shariki;

import android.content.Context;
import android.graphics.Matrix;

import java.io.FileInputStream;
import java.util.ArrayList;

import dagrada.marco.shariki.exceptions.GameEndException;

/**
 * Created by Marco on 23/07/2015.
 */
public class GameStatusHandler {

    public static final int MAX_WIDTH = 5;
    public static final int MAX_HEIGTH = 5;
    private static final int MIN_SEGMENT_SIZE = 3;

    private int[][] marbles;
    private ArrayList<String> levels;
    private int CURRENT_LEVEL;

    Context context;

    public GameStatusHandler(Context context){
        this.context = context;
    }

    public void loadGame(ArrayList<String> levels) throws Exception {

        this.levels = levels;
        loadLevel(0);

    }

    public void loadLevel (int level)throws Exception{

        if (level >= levels.size())
            throw new GameEndException();
        else{
            CURRENT_LEVEL = level;
            marbles = MatrixFileReader.getMatrix(context, levels.get(CURRENT_LEVEL));
        }

    }

    public void nextLevel() throws Exception {
        loadLevel(getCurrentLevel()+1);
    }

    public void compactMarbles(){
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

    public void switchMarbles(int marble1row, int marble1col, int marble2row, int marble2col){
        int buffer;
        if(!(marble1row <0 || marble1col <0 || marble2row <0 || marble2col <0 || marble1row > MAX_WIDTH-1 || marble2row > MAX_WIDTH-1 || marble1col > MAX_HEIGTH-1 || marble2col > MAX_HEIGTH-1)){
            buffer = marbles[marble1row][marble1col];
            marbles[marble1row][marble1col] = marbles[marble2row][marble2col];
            marbles[marble2row][marble2col] = buffer;
        }




    }

    public void checkForSegments(){
        int[][] toCheck = new int[MAX_HEIGTH][MAX_WIDTH];

        for (int i=0; i<MAX_HEIGTH; i++){
            for (int j=0; j<MAX_WIDTH; j++){
                toCheck[i][j] = marbles[i][j];
            }
        }

        try {
            MatrixChecker.CheckForSegments(toCheck, MIN_SEGMENT_SIZE);
            for (int i=0; i<MAX_HEIGTH; i++){
                for (int j=0; j<MAX_WIDTH; j++){
                    marbles[i][j] = toCheck[i][j];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public int[][] getMarbles() {
        return marbles;
    }

    public int getCurrentLevel() {
        return CURRENT_LEVEL;
    }

}