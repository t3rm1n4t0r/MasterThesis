package dagrada.marco.shariki;

import java.util.Observable;
import java.util.Observer;

import dagrada.marco.shariki.Events.SwitchMarbleGraphicsEvent;
import dagrada.marco.shariki.animations.AnimationPacket;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 19/08/2015.
 */
public class GameStateHandler implements EventManager{

    private int[][] lastModel;
    private GameModelHandler handler;
    private GraphicsEngine engine;
    private boolean changed;
    private EventQueueManager queueManager = new EventQueueManager();

    public GameStateHandler(GameModelHandler handler, GraphicsEngine engine){
        this.handler = handler;
        this.engine = engine;
    }

    public void switchMarbles(int row1, int col1, int row2, int col2){
        if(!(row1 <0 || col1 <0 || row2 <0 || col2 <0 || row1 > handler.getMaxWidth()-1 || row2 > handler.getMaxWidth()-1 || col1 > handler.getMaxHeigth()-1 || col2 > handler.getMaxHeigth()-1)){
            lastModel = handler.copyModel();
            SwitchMarbleGraphicsEvent event1 = new SwitchMarbleGraphicsEvent(engine, row1, col1, row2, col2);
            event1.addManager( this );
            queueManager.addToQueue(event1);



            changed = false;
        }
    }


    @Override
    public void notifyEventConclusion(GameEvent event) {

    }
}
