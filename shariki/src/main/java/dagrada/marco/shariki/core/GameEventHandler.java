package dagrada.marco.shariki.core;

import android.content.Context;

import java.util.ArrayList;

import thesis.EventManager;
import dagrada.marco.shariki.Events.CheckandUpdateScoreLoopEvent;
import dagrada.marco.shariki.Events.SwitchMarbleGraphicsEvent;
import dagrada.marco.shariki.Events.SwitchOnModelAndCheckEvent;
import thesis.GameEvent;
import dagrada.marco.shariki.animations.WinningAnimation;
import dagrada.marco.shariki.communicationpackets.GraphicsUpdatePacket;
import dagrada.marco.shariki.communicationpackets.ModelUpdatePacket;
import dagrada.marco.shariki.communicationpackets.SwitchDataPacket;
import dagrada.marco.shariki.exceptions.GameEndException;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 19/08/2015.
 */
public class GameEventHandler implements EventManager {

    private GameModelHandler handler;
    private GraphicsEngine engine;
    private EventQueueManager queueManager = new EventQueueManager();
    private Context context;
    private boolean started;

    public GameEventHandler(Context context, GameModelHandler handler, GraphicsEngine engine){
        this.context = context;
        this.handler = handler;
        this.engine = engine;
        started = false;
    }

    public void startGame(ArrayList<String> levels){
        try {
            handler.loadGame(levels);
            GraphicsUpdatePacket packet = new GraphicsUpdatePacket(handler.getMarbles(), 0);
            engine.updateModel(packet);
            started = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void startEventChain(Object data) {

        if(started) {
            SwitchDataPacket packet = (SwitchDataPacket) data;
            int row1 = packet.getRow1();
            int row2 = packet.getRow2();
            int col1 = packet.getCol1();
            int col2 = packet.getCol2();

            if (!(row1 < 0 || col1 < 0 || row2 < 0 || col2 < 0 || row1 > handler.getMaxWidth() - 1 || row2 > handler.getMaxWidth() - 1 || col1 > handler.getMaxHeigth() - 1 || col2 > handler.getMaxHeigth() - 1)) {
                SwitchMarbleGraphicsEvent event1 = new SwitchMarbleGraphicsEvent(engine, row1, col1, row2, col2);
                event1.addManager(this);

                SwitchOnModelAndCheckEvent event2 = new SwitchOnModelAndCheckEvent(engine, handler, row1, col1, row2, col2);
                event2.addManager(this);

                queueManager.addToQueue(event1);
                queueManager.addToQueue(event2);

                queueManager.getNextEvent().happen();


            }
        }

    }

    @Override
    public void notifyEventConclusion(GameEvent event, Object data) {

        if(data != null){
            boolean updated = ((ModelUpdatePacket) data).isChanged();

            if(updated && !handler.isGameEnded()) {
                CheckandUpdateScoreLoopEvent event3 = new CheckandUpdateScoreLoopEvent(handler, engine);
                event3.addManager(this);
                queueManager.addToQueue(event3);
            }

            if(handler.isGameEnded()) {
                try {
                    handler.nextLevel();
                    engine.updateModel(new GraphicsUpdatePacket(handler.getMarbles(), 0));
                    engine.update();
                } catch (Exception e1) {

                    //e1.printStackTrace();
                    if(e1.getClass() == GameEndException.class)
                        engine.addAnimation(new WinningAnimation(context));

                }
            }



        }


        GameEvent current = queueManager.getNextEvent();
        if(current != null){

            current.happen();
        }
    }

    public GameModelHandler getHandler() {
        return handler;
    }

    public GraphicsEngine getEngine() {
        return engine;
    }

    public EventQueueManager getQueueManager() {
        return queueManager;
    }

    public Context getContext() {
        return context;
    }

    public boolean isStarted() {
        return started;
    }
}
