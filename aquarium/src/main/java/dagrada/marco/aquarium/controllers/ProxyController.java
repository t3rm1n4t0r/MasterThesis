package dagrada.marco.aquarium.controllers;

import android.content.Context;

import activities.renderers.ProxyRenderer;
import dagrada.marco.aquarium.GameModeHandler;
import dagrada.marco.aquarium.ResourceManager;
import thesis.touch.TouchActivity;

/**
 * Created by Marco on 11/10/2015.
 */
public class ProxyController implements TouchActivity {

    private TouchActivity proxy;

    private Context context;

    private ProxyRenderer renderer;
    //private GameController controller;
    private ResourceManager manager;
    private TouchActivity[] controllers;
    private int current;
    private GameModeHandler gameModeHandler;

    public ProxyController(Context context, ProxyRenderer renderer, ResourceManager manager, GameModeHandler gameModeHandler){
        this.context=context;

        this.renderer = renderer;

        this.manager = manager;
        this.gameModeHandler = gameModeHandler;
        controllers = new TouchActivity[2];
        controllers[0] = new EditController(context, renderer, manager, gameModeHandler);
        controllers[1] = new PanoramicController(context, renderer, manager, gameModeHandler);
        current = 0;
        this.proxy = controllers[current];

    }

    public void toggleGameMode(int gamemode){
        switch (gamemode){
            case 0:case 1:
                this.current = gamemode;
                this.proxy = controllers[current];
                break;
            default:break;
        }



    }

    @Override
    public void onRightSwipe(float startX, float startY, float endX, float endY) {
        proxy.onRightSwipe(startX, startY, endX, endY);
    }

    @Override
    public void onLeftSwipe(float startX, float startY, float endX, float endY) {
        proxy.onLeftSwipe(startX, startY, endX, endY);
    }

    @Override
    public void onUpSwipe(float startX, float startY, float endX, float endY) {
        proxy.onUpSwipe(startX, startY, endX, endY);
    }

    @Override
    public void onDownSwipe(float startX, float startY, float endX, float endY) {
        proxy.onDownSwipe(startX, startY, endX, endY);
    }

    @Override
    public void onDoubleTap(float x, float y) {
        proxy.onDoubleTap(x, y);
    }

    @Override
    public void onLongPress(float x, float y) {
        proxy.onLongPress(x, y);
    }

    @Override
    public void onSingleTapUp(float x, float y) {
        proxy.onSingleTapUp(x, y);
    }

    @Override
    public void onDown(float x, float y) {
        proxy.onDown(x, y);
    }

    @Override
    public void onMove(float x, float y) {
        proxy.onMove(x, y);
    }

    @Override
    public void onUp(float x, float y) {
        proxy.onUp(x, y);
    }
}
