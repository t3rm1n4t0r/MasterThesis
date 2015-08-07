package dagrada.marco.shariki.animations;

import android.renderscript.Matrix3f;
import android.util.Log;

import sfogl.integration.Node;
import shadow.math.SFMatrix2f;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import shadow.math.SFVertex3f;
import thesis.Graphics.Exceptions.AnimationEndException;
import thesis.Graphics.GraphicsAnimation;

/**
 * Created by Marco on 07/08/2015.
 */
public class SwitchAnimation implements GraphicsAnimation {

    Node node1;
    Node node2;
    private SFVertex3f axis;
    private Node father;
    private static final double TOTAL_ROTATION = Math.PI;
    private static final int TOTAL_FRAMES = 30;
    private double current = 0;

    public SwitchAnimation(Node node1, Node node2){
        this.node1 = node1;
        this.node2 = node2;
        axis = new SFVertex3f((node1.getX()+node2.getX()/2), (node1.getY()+node2.getY()/2), (node1.getZ()+node2.getZ()/2) );
    }

    @Override
    public void goOn() throws AnimationEndException {

        if(current < TOTAL_ROTATION){

            father = new Node();

            Node child1 = new Node();
            child1.getRelativeTransform().setPosition(new SFVertex3f(node1.getX(), node1.getY(), node1.getZ()));
            Node child2 = new Node();
            child2.getRelativeTransform().setPosition(new SFVertex3f(node2.getX(), node2.getY(), node2.getZ()));

            father.getRelativeTransform().setPosition(axis);
            father.getSonNodes().add(child1);
            father.getSonNodes().add(child2);

            SFMatrix3f matrix3f=SFMatrix3f.getIdentity();
            matrix3f=matrix3f.MultMatrix(SFMatrix3f.getRotationY((float) (TOTAL_ROTATION/TOTAL_FRAMES)));
            current += TOTAL_ROTATION/TOTAL_FRAMES;
            father.getRelativeTransform().setMatrix(matrix3f);
            father.updateTree(new SFTransform3f());

            node1.getRelativeTransform().setPosition(child1.getX(), child1.getY(), child1.getZ());

            node2.getRelativeTransform().setPosition(child2.getX(), child2.getY(), child2.getZ());

            Log.d("AVANZATO DI ", String.valueOf(current));

        }
        else{
            throw new AnimationEndException();
        }
    }
}
