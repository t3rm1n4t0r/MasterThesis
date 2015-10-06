package activities;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;


import dagrada.marco.aquarium.ResourceManager;
import dagrada.marco.aquarium.exceptions.TouchedItemNotFoundException;
import thesis.Graphics.GameRenderer;
import thesis.touch.TouchActivity;


public class GraphicsView extends GLSurfaceView implements TouchActivity{

    private Context context;

    private ProxyRenderer renderer;
    //private GameController controller;
    private ResourceManager manager;

    public GraphicsView(Context context, ProxyRenderer renderer, ResourceManager manager) {
        super(context);
        setEGLContextClientVersion(2);
        this.context=context;
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.renderer = renderer;
        setRenderer(this.renderer);
        //this.controller = controller;
        this.manager = manager;
    }

    @Override
    public void onRightSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "RIGHT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "(" + String.valueOf(endX) + "," + String.valueOf(endY) + ")");
        //controller.moveRight();


    }

    @Override
    public void onLeftSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "LEFT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "(" + String.valueOf(endX) + "," + String.valueOf(endY) + ")");
        //controller.moveLeft();


    }

    @Override
    public void onUpSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "UP SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");


    }

    @Override
    public void onDownSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "DOWN SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");


    }

    @Override
    public void onDoubleTap(float x, float y) {

        //renderer.toggleRenderer();

        manager.loadAllResources();

        Log.d("TOUCH", "DOUBLE TAP");

    }

    @Override
    public void onLongPress(float x, float y) {
        Log.d("TOUCH", "LONG PRESS");
        //controller.toggleGame();

    }

    @Override
    public void onSingleTapUp(float x, float y) {
        Log.d("TOUCH", "SINGLE TAP on (" + String.valueOf(x) + "," + String.valueOf(y) + ")");
;
    }

    @Override
    public void onDown(float x, float y) {

    }

    @Override
    public void onMove(float x, float y) {
        try {
            int [] res = renderer.detectTouchedItem(x, y);
            Log.e("FOUND", String.valueOf(res[0])+";"+String.valueOf(res[1]));
        } catch (TouchedItemNotFoundException e) {
            Log.e("NO ITEM FOUND", "<----");
        }
    }

    @Override
    public void onUp(float x, float y) {

    }

    public GameRenderer getRenderer() {
        return this.renderer;
    }
}
