package dagrada.marco.shariki.controllers;

import android.os.Handler;

import dagrada.marco.shariki.GameStatusHandler;
import dagrada.marco.shariki.MatrixChecker;
import thesis.touch.TouchActivity;

/**
 * Created by Marco on 28/07/2015.
 */
public class MatrixController {

    private GameStatusHandler handler;

    public MatrixController(GameStatusHandler handler){
        this. handler = handler;

    }






    public void switchPosition(int row1, int col1, int row2, int col2){

        handler.switchMarbles(row1, col1, row2, col2);
        handler.checkForSegments();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // your code here
            }
        }, 800/* 1sec delay */);
        handler.compactMarbles();

    }

    public int[] getMatrixIndeces(float x, float y, float screenWidth, float screenHeight){

        int indeces[] = new int[2];
        indeces[0] = (int) (x / (screenWidth/GameStatusHandler.MAX_WIDTH));
        indeces[1] = (int) (y / (screenWidth/GameStatusHandler.MAX_HEIGTH));
        return indeces;
    }
}
