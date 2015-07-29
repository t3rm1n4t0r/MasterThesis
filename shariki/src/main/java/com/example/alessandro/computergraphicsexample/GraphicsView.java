package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dagrada.marco.shariki.GameStatusHandler;
import dagrada.marco.shariki.MatrixChecker;
import dagrada.marco.shariki.controllers.MatrixController;
import sfogl.integration.Node;
import sfogl2.SFOGLSystemState;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import thesis.Graphics.NodesKeeper;
import thesis.Graphics.ShadersKeeper;
import thesis.touch.TouchActivity;


public class GraphicsView extends GLSurfaceView implements TouchActivity{

    private Context context;

    private GraphicsRenderer renderer;
    private MatrixController controller;

    public GraphicsView(Context context, MatrixController controller) {
        super(context);
        setEGLContextClientVersion(2);
        this.context=context;
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        renderer = new GraphicsRenderer(context);
        setRenderer(renderer);
        this.controller = controller;
    }

    @Override
    public void onRightSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "RIGHT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int x = controller.getMatrixIndeces(startX, startY, display.getWidth(), display.getHeight())[0];
        int y = controller.getMatrixIndeces(startX, startY, display.getWidth(), display.getHeight())[1];
        controller.switchPosition(y ,x, y, x+1 );
    }

    @Override
    public void onLeftSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "LEFT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int x = controller.getMatrixIndeces(startX, startY, display.getWidth(), display.getHeight())[0];
        int y = controller.getMatrixIndeces(startX, startY, display.getWidth(), display.getHeight())[1];
        controller.switchPosition(y, x, y, x - 1);
    }

    @Override
    public void onUpSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "UP SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int x = controller.getMatrixIndeces(startX, startY, display.getWidth(), display.getHeight())[0];
        int y = controller.getMatrixIndeces(startX, startY, display.getWidth(), display.getHeight())[1];
        controller.switchPosition(y, x, y-1, x);
    }

    @Override
    public void onDownSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "DOWN SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int x = controller.getMatrixIndeces(startX, startY, display.getWidth(), display.getHeight())[0];
        int y = controller.getMatrixIndeces(startX, startY, display.getWidth(), display.getHeight())[1];
        controller.switchPosition(y, x, y+1, x);
    }

    @Override
    public void onDoubleTap(float x, float y) {
        Log.d("TOUCH", "DOUBLE TAP");

    }

    @Override
    public void onLongPress(float x, float y) {
        Log.d("TOUCH", "LONG PRESS");

    }

    @Override
    public void onSingleTapUp(float x, float y) {
        Log.d("TOUCH", "SINGLE TAP on (" + String.valueOf(x) + "," + String.valueOf(y) + ")");
        //renderer.touch(x, y);
    }
}
