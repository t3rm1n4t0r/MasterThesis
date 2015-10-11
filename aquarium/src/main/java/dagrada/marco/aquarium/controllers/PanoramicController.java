package dagrada.marco.aquarium.controllers;

import android.content.Context;

import activities.renderers.ProxyRenderer;
import dagrada.marco.aquarium.GameModeHandler;
import dagrada.marco.aquarium.ResourceManager;
import thesis.touch.TouchActivity;

/**
 * Created by Marco on 11/10/2015.
 */
public class PanoramicController implements TouchActivity {

    private GameModeHandler gameModeHandler;

    public PanoramicController(Context context, ProxyRenderer renderer, ResourceManager manager, GameModeHandler gameModeHandler){
        this.gameModeHandler = gameModeHandler;
    }

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
        gameModeHandler.setGamemode(GameModeHandler.GAMEMODE_EDIT);
    }

    @Override
    public void onSingleTapUp(float x, float y) {

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
