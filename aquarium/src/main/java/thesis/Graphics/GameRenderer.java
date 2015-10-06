package thesis.Graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import activities.renderers.EditRenderer;
import dagrada.marco.aquarium.exceptions.TouchedItemNotFoundException;
import thesis.Graphics.GraphicsAnimation;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 05/10/2015.
 */
public abstract class GameRenderer implements GraphicsEngine,  GLSurfaceView.Renderer {

    public abstract int[] detectTouchedItem(float x, float y) throws TouchedItemNotFoundException;

    public abstract void update();

    public abstract void drawItems();

    public abstract void drawBackground();

    public abstract void drawScore(int score);
    public abstract void onSurfaceCreated(GL10 gl, EGLConfig config);
    public abstract void onSurfaceChanged(GL10 gl, int width, int height);
    public abstract void onDrawFrame(GL10 gl);


    public abstract void updateAnimations();


    public abstract void updateModel(Object obj);
    public abstract void addAnimation(GraphicsAnimation animation);
    public abstract void animate(Object object);

    public abstract boolean isBlocked();

    public abstract Context getContext();
}


