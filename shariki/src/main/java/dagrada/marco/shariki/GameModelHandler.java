package dagrada.marco.shariki;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import dagrada.marco.shariki.communicationpackets.GraphicsUpdatePacket;
import dagrada.marco.shariki.exceptions.GameEndException;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 23/07/2015.
 */
public class GameModelHandler {

    private final int MAX_WIDTH = 5;
    private final int MAX_HEIGTH = 5;
    private final int MIN_SEGMENT_SIZE = 3;
    private final int SCORE_FOR_MARBLE = 20;

    private int[][] marbles;
    private ArrayList<String> levels;
    private int CURRENT_LEVEL;

    private ScoreKeeper scorekeeper;

    Context context;

    private boolean gameEnded;

    public GameModelHandler(Context context){
        this.context = context;
        this.scorekeeper = new ScoreKeeper();
    }

    public void loadGame(ArrayList<String> levels) throws Exception {

        this.levels = levels;
        loadLevel(0);

    }

    private void loadLevel (int level)throws Exception{
        //Log.d("LEVEL LOADED", String.valueOf(level));
        //Log.d("LEVELS", String.valueOf(levels.size()));

        if (level >= levels.size()) {

            throw new GameEndException();
        }
        else if(level < 0){

            throw new Exception("Level doesn't exists");


        }
        else{

            gameEnded = false;
            CURRENT_LEVEL = level;
            setMarbles(MatrixFileReader.getMatrix(context, levels.get(CURRENT_LEVEL)));


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

    public int[][] copyModel(){
        int[][] copy = new int[marbles.length][marbles[0].length];
        for (int i=0; i<marbles.length; i++){
            for (int j =0; j<marbles[i].length; j++){
                copy[i][j] = marbles[i][j];
            }
        }
        return copy;
    }



    public void switchMarbles(int marble1row, int marble1col, int marble2row, int marble2col){
        int buffer;
        if(!(marble1row <0 || marble1col <0 || marble2row <0 || marble2col <0 || marble1row > MAX_WIDTH-1 || marble2row > MAX_WIDTH-1 || marble1col > MAX_HEIGTH-1 || marble2col > MAX_HEIGTH-1)){
            buffer = marbles[marble1row][marble1col];
            marbles[marble1row][marble1col] = marbles[marble2row][marble2col];
            marbles[marble2row][marble2col] = buffer;

        }



    }

    public void updateScore() {
        for (int i = 0; i < MAX_HEIGTH; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                if (marbles[i][j] == 0) {
                    scorekeeper.updateScore(SCORE_FOR_MARBLE);
                }
            }
        }
    }


    public ScoreKeeper getScorekeeper() {
        return scorekeeper;
    }

    public boolean checkForEndGame(){

        try {
            this.gameEnded = MatrixChecker.CheckForEndGame(getMarbles(),  MAX_WIDTH, MAX_HEIGTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameEnded;
    }

    public boolean isGameEnded(){
        return this.gameEnded;
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


    public int getMaxWidth() {
        return this.MAX_WIDTH;
    }

    public int getMaxHeigth() {

        return this.MAX_HEIGTH;
    }




}