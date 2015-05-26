package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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

    public GraphicsView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        this.context=context;
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        renderer = new GraphicsRenderer(context);
        setRenderer(renderer);
    }



    @Override
    public void onRightSwipe(float distance) {
        Log.d("TOUCH", "RIGHT SWIPE");
        renderer.incrementTX(-0.01f);
    }

    @Override
    public void onLeftSwipe(float distance) {
        Log.d("TOUCH", "LEFT SWIPE");
        renderer.incrementTX(0.01f);
    }

    @Override
    public void onUpSwipe(float distance) {
        Log.d("TOUCH", "UP SWIPE");
        renderer.incrementTY(-0.01f);
    }

    @Override
    public void onDownSwipe(float distance) {
        Log.d("TOUCH", "DOWN SWIPE");
        renderer.incrementTY(0.01f);
    }

    @Override
    public void onDoubleTap() {
        Log.d("TOUCH", "DOUBLE TAP");
        renderer.stopMovement();
    }

    @Override
    public void onLongPress() {
        Log.d("TOUCH", "LONG PRESS");
        renderer.resetPosition();
    }


}
