package dagrada.marco.aquarium.animations;

import android.content.Context;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import thesis.Graphics.GraphicsAnimation;

/**
 * Created by Marco on 12/10/2015.
 */
public class BubbleFluxAnimation implements GraphicsAnimation {

    private final double bubble_spawn_rate = 0.2f;
    private final float max_radius = 1.2f;

    private LinkedList<BubbleAnimation> bubbles = new LinkedList<>();
    private float x, y, z;
    private Context context;

    public BubbleFluxAnimation(Context context, float x, float y, float z){
        this.context = context;
        this.x = x;
        this.y = y;
        this.z = z;

    }

    @Override
    public void goOn() {
        if(Math.random() < bubble_spawn_rate){
            float radius = ((float)Math.random()) * max_radius;
            bubbles.add(new BubbleAnimation(context, x, y, z, radius));
        }
        for (BubbleAnimation a : bubbles) {
            a.goOn();
        }
        LinkedList<GraphicsAnimation> toBeRemoved = new LinkedList<>();
        for (BubbleAnimation a : bubbles) {
            if(a.isEnded()){
                toBeRemoved.add(a);
            }
        }

        bubbles.removeAll(toBeRemoved);


    }

    @Override
    public boolean isblocking() {
        return false;
    }

    @Override
    public boolean isEnded() {
        return false;
    }
}
