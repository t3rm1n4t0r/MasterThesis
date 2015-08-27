package dagrada.marco.shariki.Events;

import thesis.GameEvent;
import dagrada.marco.shariki.core.GameModelHandler;
import dagrada.marco.shariki.communicationpackets.GraphicsUpdatePacket;
import dagrada.marco.shariki.animations.AnimationPacket;
import dagrada.marco.shariki.animations.WaitAnimation;
import dagrada.marco.shariki.communicationpackets.ModelUpdatePacket;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 21/08/2015.
 */
public class SwitchOnModelAndCheckEvent extends GameEvent {

    private GameModelHandler handler;
    private GraphicsEngine engine;
    private int row1, col1, row2, col2;
    private final int WAIT_FRAMES = 30;
    private boolean switched;

    public SwitchOnModelAndCheckEvent(GraphicsEngine engine, GameModelHandler handler, int row1, int col1, int row2, int col2){
        this.row1 = row1;
        this.row2 = row2;
        this.col1 = col1;
        this.col2 = col2;
        this.engine = engine;
        this.handler = handler;
        switched = false;


    }

    @Override
    public void happen() {

        handler.switchMarbles(row1, col1, row2, col2);
        if(handler.checkForSegments()){
            switched = true;
            handler.updateScore();

            updateEngine();
            engine.addAnimation(new WaitAnimation(this, WAIT_FRAMES));

        }
        else{
            handler.switchMarbles(row2, col2, row1, col1);
            switched = false;
            engine.animate(new AnimationPacket(row2, col2, row1, col1, this));

        }

        setEventData(new ModelUpdatePacket(switched, false));

    }

    private void updateEngine(){
        GraphicsUpdatePacket packet = new GraphicsUpdatePacket(handler.copyModel(), handler.getScorekeeper().getScore());
        engine.updateModel(packet);
        engine.update();
    }


}
