package dagrada.marco.shariki.controllers;

import android.util.Log;

import dagrada.marco.shariki.GameModelHandler;
import dagrada.marco.shariki.GameStateHandler;
import dagrada.marco.shariki.exceptions.GameEndException;

/**
 * Created by Marco on 28/07/2015.
 */
public class GameController {

    private GameStateHandler handler;

    public GameController(GameStateHandler handler){
        this.handler = handler;

    }


    public GameStateHandler getHandler() {
        return handler;
    }

    public void switchPosition(int row1, int col1, int row2, int col2){

        Log.d("PRIMO", String.valueOf(row1)+ " "+ String.valueOf(col1));

        Log.d("SECONDO", String.valueOf(row2)+ " "+ String.valueOf(col2));

        handler.switchMarbles(row1, col1, row2, col2);

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
