package dagrada.marco.aquarium;

import android.view.GestureDetector;

import java.util.Observable;
import java.util.Observer;

import activities.renderers.ProxyRenderer;
import dagrada.marco.aquarium.controllers.ProxyController;

/**
 * Created by Marco on 11/10/2015.
 */
public class GameModeObserver implements Observer {

    private ProxyController controller;
    private ProxyRenderer renderer;
    private ResourceManager manager;
    private GestureDetector detector;

    public GameModeObserver(ProxyController controller, ProxyRenderer renderer, ResourceManager manager, GestureDetector detector){
        this.controller = controller;
        this.renderer = renderer;
        this.manager = manager;
        this.detector = detector;
    }

    @Override
    public void update(Observable observable, Object data) {
        int gamemode = (int) data;
        switch (gamemode){
            case 0:case 1:
                controller.toggleGameMode(gamemode);
                renderer.toggleRenderer(gamemode);
                manager.loadAllResources();
                detector.setIsLongpressEnabled(!detector.isLongpressEnabled());
                break;

            default:break;
        }
    }
}
