package dagrada.marco.shariki.animations;

import thesis.GameEvent;
import dagrada.marco.shariki.animations.utility.BezierCurve;
import dagrada.marco.shariki.animations.utility.ParameterOutOfRangeException;
import dagrada.marco.shariki.animations.utility.SecondOrderBezierCurve;
import sfogl.integration.Node;
import shadow.math.SFVertex3f;
import thesis.Graphics.GraphicsAnimation;

/**
 * Created by Marco on 07/08/2015.
 */

public class SwitchAnimation implements GraphicsAnimation {

    private final float depth = 0.1f;
    Node node1;
    Node node2;
    private SFVertex3f startpoint, endpoint, midpointfront, midpointback;
    private static final float TOTAL_ROTATION = 1;
    private static final int TOTAL_FRAMES = 15;
    private float current = 0;
    BezierCurve curvefront, curveback;
    private GameEvent event;
    private boolean isEnded;

    public SwitchAnimation(Node node1, Node node2, GameEvent event){
        this.node1 = node1;
        this.node2 = node2;
        startpoint = new SFVertex3f();
        endpoint = new SFVertex3f();
        node1.getRelativeTransform().getPosition(startpoint);
        node2.getRelativeTransform().getPosition(endpoint);
        midpointfront = new SFVertex3f((node1.getX()+node2.getX()/2), (node1.getY()+node2.getY()/2), (node1.getZ()+node2.getZ()/2 - depth) );

        midpointback = new SFVertex3f((node1.getX()+node2.getX()/2), (node1.getY()+node2.getY()/2), (node1.getZ()+node2.getZ()/2 + depth) );

        curvefront = new SecondOrderBezierCurve(startpoint, midpointfront, endpoint);
        curveback = new SecondOrderBezierCurve(endpoint, midpointback, startpoint);
        this.event = event;
        this.isEnded = false;
    }

    @Override
    public void goOn() {

        if(!isEnded) {

            if (current < TOTAL_ROTATION) {

                current += TOTAL_ROTATION / TOTAL_FRAMES;
                if (current > TOTAL_ROTATION)
                    current = TOTAL_ROTATION;

                try {
                    node1.getRelativeTransform().setPosition(curvefront.getValue(current));
                    node2.getRelativeTransform().setPosition(curveback.getValue(current));
                } catch (ParameterOutOfRangeException e) {
                    e.printStackTrace();
                }



/*
            Log.d("PARAMETER VALUE ", String.valueOf(current));
            Log.d("NODE1", String.valueOf(node1.getX()) +" "+String.valueOf(node1.getY()) + " " + String.valueOf(node1.getZ()) );

            Log.d("NODE2", String.valueOf(node2.getX()) +" "+String.valueOf(node2.getY()) + " " + String.valueOf(node2.getZ()) );
*/
            } else {
                if (event != null) {
                    this.isEnded = true;
                    event.notifyManagers();

                }
            }
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