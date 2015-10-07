package activities;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;


import dagrada.marco.aquarium.ResourceManager;
import dagrada.marco.aquarium.communicationpackets.ProxyItemMovePacket;
import dagrada.marco.aquarium.communicationpackets.ProxyItemPacket;
import dagrada.marco.aquarium.exceptions.TouchedItemNotFoundException;
import thesis.Graphics.GameRenderer;
import thesis.touch.TouchActivity;


public class GraphicsView extends GLSurfaceView{

    private Context context;

    private ProxyRenderer renderer;
    //private GameController controller;


    public GraphicsView(Context context, ProxyRenderer renderer) {
        super(context);
        setEGLContextClientVersion(2);
        this.context=context;
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.renderer = renderer;
        setRenderer(this.renderer);
        //this.controller = controller;

    }


    public GameRenderer getRenderer() {
        return this.renderer;
    }
}
