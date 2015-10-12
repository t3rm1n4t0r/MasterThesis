package dagrada.marco.aquarium.animations;

import android.content.Context;

import sfogl.integration.Node;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import shadow.math.SFVertex3f;
import thesis.Graphics.GraphicsAnimation;
import thesis.Graphics.NodesKeeper;

/**
 * Created by Marco on 12/10/2015.
 */
public class BubbleAnimation implements GraphicsAnimation{


    private final float max_bubble_scale = 0.1f;
    private final float bubble_max_heigth = 3f;
    private final float bubble_frame_up_value = 0.015f;


    private Context context;
    Node bubble;
    private boolean ended;
    private float radius;
    private float rotation_frames;

    public BubbleAnimation(Context context, float x, float y, float z, float radius){
        ended = false;
        bubble = NodesKeeper.generateNode(context, "stdShader", "#25FFFFFF", "sphere.obj");
        this.radius = radius;
        bubble.getRelativeTransform().setPosition(x, y, z + radius);
        float bubble_scale = ((float)Math.random())*max_bubble_scale;
        SFMatrix3f m = SFMatrix3f.getScale(bubble_scale, bubble_scale, bubble_scale);
        bubble.getRelativeTransform().setMatrix(m);
        bubble.updateTree(new SFTransform3f());
        rotation_frames = 20 + ((float)Math.random())* 30;

    }

    @Override
    public void goOn() {
        if(!isEnded()){
            SFMatrix3f m = new SFMatrix3f();
            bubble.getRelativeTransform().getMatrix(m);
            m = m.MultMatrix(SFMatrix3f.getRotationY((float)(2*Math.PI)/rotation_frames));
            bubble.getRelativeTransform().setMatrix(m);
            SFVertex3f v = new SFVertex3f();
            bubble.getRelativeTransform().getPosition(v);
            v.setY(v.getY() + bubble_frame_up_value);
            bubble.getRelativeTransform().setPosition(v);
            bubble.updateTree(new SFTransform3f());
            bubble.draw();
            if(bubble.getY() >= bubble_max_heigth)
                this.ended = true;
        }
    }

    @Override
    public boolean isblocking() {
        return false;
    }

    @Override
    public boolean isEnded() {
        return ended;
    }
}
