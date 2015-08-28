package activities;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import dagrada.marco.shariki.controllers.GameController;
import dagrada.marco.shariki.exceptions.TouchedItemNotFoundException;
import thesis.touch.TouchActivity;


public class GraphicsView extends GLSurfaceView implements TouchActivity{

    private Context context;

    private GraphicsRenderer renderer;
    private GameController controller;

    public GraphicsView(Context context, GameController controller, GraphicsRenderer renderer) {
        super(context);
        setEGLContextClientVersion(2);
        this.context=context;
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.renderer = renderer;
        setRenderer(this.renderer);
        this.controller = controller;
    }

    @Override
    public void onRightSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "RIGHT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(startX, startY);
            int x = indices[0];
            int y = indices[1];
            controller.switchPosition(x, y, x + 1, y);
        } catch (TouchedItemNotFoundException e) {}

    }

    @Override
    public void onLeftSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "LEFT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(startX, startY);
            int x = indices[0];
            int y = indices[1];
            controller.switchPosition(x, y, x - 1, y);
        } catch (TouchedItemNotFoundException e) {

        }

    }

    @Override
    public void onUpSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "UP SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(startX, startY);
            int x = indices[0];
            int y = indices[1];
            controller.switchPosition(x, y, x, y + 1);
        } catch (TouchedItemNotFoundException e) {

        }

    }

    @Override
    public void onDownSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "DOWN SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(startX, startY);
            int x = indices[0];
            int y = indices[1];
            controller.switchPosition(x, y, x,y - 1);
        } catch (TouchedItemNotFoundException e) {

        }

    }

    @Override
    public void onDoubleTap(float x, float y) {
        Log.d("TOUCH", "DOUBLE TAP");

    }

    @Override
    public void onLongPress(float x, float y) {
        Log.d("TOUCH", "LONG PRESS");
        renderer.testAnimation();
    }

    @Override
    public void onSingleTapUp(float x, float y) {
        Log.d("TOUCH", "SINGLE TAP on (" + String.valueOf(x) + "," + String.valueOf(y) + ")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(x, y);
            int xx = indices[0];
            int yy = indices[1];
            Log.d("TOUCH", "INDICES "+ String.valueOf(xx)+","+String.valueOf(yy));
        } catch (TouchedItemNotFoundException e) {

        }
;
    }

    public GraphicsRenderer getRenderer() {
        return renderer;
    }
}
