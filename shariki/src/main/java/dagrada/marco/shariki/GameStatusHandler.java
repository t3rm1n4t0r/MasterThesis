package dagrada.marco.shariki;

import android.content.Context;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import dagrada.marco.shariki.exceptions.GameEndException;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 23/07/2015.
 */
public class GameStatusHandler{

    public static final int MAX_WIDTH = 5;
    public static final int MAX_HEIGTH = 5;
    private static final int MIN_SEGMENT_SIZE = 3;
    private static final int CHECK_TIME_DELAY = 1500;

    private int[][] marbles;
    private ArrayList<String> levels;
    private int CURRENT_LEVEL;
    private GraphicsEngine renderer;

    Context context;

    public GameStatusHandler(Context context, GraphicsEngine renderer){
        this.context = context;
        this.renderer = renderer;
    }

    public void loadGame(ArrayList<String> levels) throws Exception {

        this.levels = levels;
        loadLevel(0);

    }

    public void loadLevel (int level)throws Exception{

        if (level >= levels.size())
            throw new GameEndException();
        else if(level < 0){
            throw new Exception("Level doesn't exists");
        }
        else{
            CURRENT_LEVEL = level;
            setMarbles(MatrixFileReader.getMatrix(context, levels.get(CURRENT_LEVEL)));
            renderer.updateModel(marbles);
        }

    }

    public void setMarbles(int[][] marbles) {
        this.marbles = marbles;
    }

    public void nextLevel() throws Exception {
        loadLevel(getCurrentLevel()+1);
    }

    public void compactMarbles(){
        int buffer;
        for (int i=0; i<marbles.length; i++){
            for (int j=marbles[i].length-2; j>=0;j--){
                if (marbles[i][j] == 0){
                    for (int k=j; k<marbles[i].length-1;k++){
                        buffer = marbles[i][k];
                        marbles[i][k] = marbles[i][k+1];
                        marbles[i][k+1] = buffer;
                    }
                }
            }
        }
    }

    private int[][] copyModel(){
        int[][] copy = new int[marbles.length][marbles[0].length];
        for (int i=0; i<marbles.length; i++){
            for (int j =0; j<marbles[i].length; j++){
                copy[i][j] = marbles[i][j];
            }
        }
        return copy;
    }

    public void tryToSwitch(int marble1row, int marble1col, int marble2row, int marble2col) throws GameEndException {
        int buffer;
        if(!(marble1row <0 || marble1col <0 || marble2row <0 || marble2col <0 || marble1row > MAX_WIDTH-1 || marble2row > MAX_WIDTH-1 || marble1col > MAX_HEIGTH-1 || marble2col > MAX_HEIGTH-1)){
            buffer = marbles[marble1row][marble1col];
            marbles[marble1row][marble1col] = marbles[marble2row][marble2col];
            marbles[marble2row][marble2col] = buffer;

            boolean changed = false;

            Timer timer = new Timer("Delay");

            while(checkForSegments()){
                changed = true;


                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                        compactMarbles();

                    }
                };
                timer.schedule(task, CHECK_TIME_DELAY);

                if(checkForEndGame()){

                    throw new GameEndException();

                }
                updateRenderer();


            }

            if(!changed){
                buffer = marbles[marble1row][marble1col];
                marbles[marble1row][marble1col] = marbles[marble2row][marble2col];
                marbles[marble2row][marble2col] = buffer;
            }
        }



    }

    public void updateRenderer(){
        renderer.updateModel(copyModel());
        renderer.update();
    }

    public boolean checkForEndGame(){
        try {
            return MatrixChecker.CheckForEndGame(getMarbles(), MAX_WIDTH, MAX_HEIGTH);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkForSegments(){
        int[][] toCheck = new int[MAX_HEIGTH][MAX_WIDTH];
        boolean found = false;

        for (int i=0; i<MAX_HEIGTH; i++){
            for (int j=0; j<MAX_WIDTH; j++){
                toCheck[i][j] = marbles[i][j];
            }
        }

        try {
            found = MatrixChecker.CheckForSegments(toCheck, MIN_SEGMENT_SIZE);
            for (int i=0; i<MAX_HEIGTH; i++){
                for (int j=0; j<MAX_WIDTH; j++){
                    marbles[i][j] = toCheck[i][j];
                }
            }





        } catch (Exception e) {
            e.printStackTrace();
        }

            return found;

    }

    public int[][] getMarbles() {
        return marbles;
    }

    public int getCurrentLevel() {
        return CURRENT_LEVEL;
    }



}