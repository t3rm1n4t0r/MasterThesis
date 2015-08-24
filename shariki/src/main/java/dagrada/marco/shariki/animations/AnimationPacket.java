package dagrada.marco.shariki.animations;

import dagrada.marco.shariki.GameEvent;

/**
 * Created by Marco on 12/08/2015.
 */
public class AnimationPacket {
    private int x1, y1, x2, y2;
    private GameEvent event;
    public AnimationPacket(int x1, int y1, int x2, int y2, GameEvent event){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.event = event;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public GameEvent getEvent() {
        return event;
    }

    public void setEvent(GameEvent event) {
        this.event = event;
    }
}
