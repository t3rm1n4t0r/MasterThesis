package dagrada.marco.shariki.animations;

import thesis.GameEvent;
import thesis.Graphics.GraphicsAnimation;

/**
 * Created by Marco on 21/08/2015.
 */
public class WaitAnimation implements GraphicsAnimation {

    private GameEvent event;
    private int frames;
    private int current;
    private boolean isEnded;

    public WaitAnimation(GameEvent event, int frames){
        this.event = event;
        this.frames = frames;
        current = 0;
        isEnded = false;
    }

    @Override
    public void goOn()  {
        if(current < frames){
            current++;
        }
        else{
            this.isEnded = true;
            event.notifyManagers(event.getEventData());
        }
    }

    @Override
    public boolean isblocking() {
        return true;
    }

    @Override
    public boolean isEnded() {
        return this.isEnded;
    }
}
