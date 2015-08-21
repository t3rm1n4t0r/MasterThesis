package dagrada.marco.shariki.Events;

import android.util.Log;

import dagrada.marco.shariki.GameEvent;
import dagrada.marco.shariki.GameModelHandler;
import dagrada.marco.shariki.GraphicsUpdatePacket;
import dagrada.marco.shariki.animations.WaitAnimation;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 21/08/2015.
 */
public class CheckandUpdateScoreLoopEvent extends GameEvent {

    private GameModelHandler handler;
    private GraphicsEngine engine;
    private final int WAIT_FRAMES = 30;
    private boolean updated;

    public CheckandUpdateScoreLoopEvent(GameModelHandler handler, GraphicsEngine engine){
        this.handler = handler;
        this.engine = engine;
        updated = false;
    }

    @Override
    public void happen() {

        handler.compactMarbles();
        handler.checkForEndGame();

        updateEngine();

        if(handler.checkForSegments()){
            updated = true;
            handler.updateScore();

            updateEngine();
            engine.addAnimation(new WaitAnimation(this, WAIT_FRAMES));

        }

        setEventData(updated);



    }

    private void updateEngine(){
        GraphicsUpdatePacket packet = new GraphicsUpdatePacket(handler.copyModel(), handler.getScorekeeper().getScore());
        engine.updateModel(packet);
        engine.update();
    }
}
