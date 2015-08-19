package dagrada.marco.shariki.Events;

import dagrada.marco.shariki.GameEvent;
import dagrada.marco.shariki.GameStateHandler;
import dagrada.marco.shariki.animations.AnimationPacket;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 19/08/2015.
 */
public class SwitchMarbleGraphicsEvent extends GameEvent {

    private GraphicsEngine engine;
    private int row1, col1, row2, col2;

    public SwitchMarbleGraphicsEvent(GraphicsEngine engine, int row1, int col1, int row2, int col2){
        this.engine = engine;
        this.row1 = row1;
        this.row2 = row2;
        this.col1 = col1;
        this.col2 = col2;
    }

    @Override
    public void happen() {

        engine.animate(new AnimationPacket(row1, col1, row2, col2, this));

    }

    @Override
    public void notifyManagers() {
        super.notifyManagers();
    }
}
