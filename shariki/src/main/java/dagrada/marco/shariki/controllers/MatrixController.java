package dagrada.marco.shariki.controllers;

import dagrada.marco.shariki.MatrixChecker;
import thesis.touch.TouchActivity;

/**
 * Created by Marco on 28/07/2015.
 */
public class MatrixController implements TouchActivity{


    /*
    int[][] toCheck = new int[MAX_HEIGTH][MAX_WIDTH];

    for (int i=0; i<MAX_HEIGTH; i++){
        for (int j=0; j<MAX_WIDTH; j++){
            toCheck[i][j] = marbles[i][j];
        }
    }

    try {
        MatrixChecker.CheckForSegments(toCheck, MIN_SEGMENT_SIZE);
        compactMarbles();
    } catch (Exception e) {
        e.printStackTrace();
    }
    */


    @Override
    public void onRightSwipe(float startX, float startY, float endX, float endY) {

    }

    @Override
    public void onLeftSwipe(float startX, float startY, float endX, float endY) {

    }

    @Override
    public void onUpSwipe(float startX, float startY, float endX, float endY) {

    }

    @Override
    public void onDownSwipe(float startX, float startY, float endX, float endY) {

    }

    @Override
    public void onDoubleTap(float x, float y) {

    }

    @Override
    public void onLongPress(float x, float y) {

    }

    @Override
    public void onSingleTapUp(float x, float y) {

    }
}
