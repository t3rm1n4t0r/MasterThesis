package dagrada.marco.shariki.controllers;

import android.util.Log;

import dagrada.marco.shariki.GameModelHandler;
import dagrada.marco.shariki.exceptions.GameEndException;

/**
 * Created by Marco on 28/07/2015.
 */
public class MatrixController {

    private GameModelHandler handler;

    public MatrixController(GameModelHandler handler){
        this. handler = handler;

    }


    public GameModelHandler getHandler() {
        return handler;
    }

    public void switchPosition(int row1, int col1, int row2, int col2){

        try {
            handler.switchMarbles(row1, col1, row2, col2);
        } catch (GameEndException e) {
            try {
                handler.nextLevel();
            } catch (Exception e1) {
                Log.d("GAME END", "<---");
            }
        }

    }


    /*
    public int[] getMatrixIndeces(float x, float y, float screenWidth, float screenHeight){

        int indeces[] = new int[2];
        indeces[0] = (int) (x / (screenWidth/GameModelHandler.MAX_WIDTH));
        indeces[1] = (int) (y / (screenWidth/GameModelHandler.MAX_HEIGTH));
        return indeces;
    }
    */
}
