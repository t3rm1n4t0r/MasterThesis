package dagrada.marco.aquarium.controllers;

import android.content.Context;

import activities.renderers.ProxyRenderer;
import dagrada.marco.aquarium.GameModeHandler;
import dagrada.marco.aquarium.MyResourceManager;
import dagrada.marco.aquarium.communicationpackets.RotationPacket;
import thesis.Graphics.GraphicsEngine;
import thesis.touch.TouchActivity;

/**
 * Created by Marco on 11/10/2015.
 */
public class PanoramicController implements TouchActivity {

    private GameModeHandler gameModeHandler;
    private GraphicsEngine engine;

    public PanoramicController(Context context, ProxyRenderer renderer, MyResourceManager manager, GameModeHandler gameModeHandler){
        this.gameModeHandler = gameModeHandler;
        this.engine = renderer;
    }

    @Override
    public void onRightSwipe(float startX, float startY, float endX, float endY) {
        RotationPacket p = new RotationPacket(0.01f, 0);
        engine.updateModel(p);

    }

    @Override
    public void onLeftSwipe(float startX, float startY, float endX, float endY) {
        RotationPacket p = new RotationPacket(-0.01f, 0);
        engine.updateModel(p);
    }

    @Override
    public void onUpSwipe(float startX, float startY, float endX, float endY) {
        RotationPacket p = new RotationPacket(0, 0.2f);
        engine.updateModel(p);
    }

    @Override
    public void onDownSwipe(float startX, float startY, float endX, float endY) {
        RotationPacket p = new RotationPacket(0, -0.2f);
        engine.updateModel(p);
    }

    @Override
    public void onDoubleTap(float x, float y) {

    }

    @Override
    public void onLongPress(float x, float y) {
        gameModeHandler.setGamemode(GameModeHandler.GAMEMODE_EDIT);
    }

    @Override
    public void onSingleTapUp(float x, float y) {
        RotationPacket p = new RotationPacket(0, 0);
        engine.updateModel(p);
    }

    @Override
    public void onDown(float x, float y) {

    }

    @Override
    public void onMove(float x, float y) {

    }

    @Override
    public void onUp(float x, float y) {

    }
}
