package dagrada.marco.shariki.Events;

import dagrada.marco.shariki.GameEvent;
import dagrada.marco.shariki.GameModelHandler;
import dagrada.marco.shariki.communicationpackets.GraphicsUpdatePacket;
import dagrada.marco.shariki.animations.WaitAnimation;
import dagrada.marco.shariki.communicationpackets.ModelUpdatePacket;
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


        updateEngine();

        if(handler.checkForSegments()){
            updated = true;
            handler.updateScore();

            updateEngine();
            engine.addAnimation(new WaitAnimation(this, WAIT_FRAMES));

        }

        ModelUpdatePacket packet = new ModelUpdatePacket( updated,
                handler.checkForEndGame());

        setEventData(packet);



    }

    private void updateEngine(){
        GraphicsUpdatePacket packet = new GraphicsUpdatePacket(handler.copyModel(), handler.getScorekeeper().getScore());
        engine.updateModel(packet);
        engine.update();
    }
}
