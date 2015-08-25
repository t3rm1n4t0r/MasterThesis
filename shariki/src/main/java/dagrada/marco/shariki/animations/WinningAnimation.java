package dagrada.marco.shariki.animations;

import android.content.Context;

import sfogl.integration.Node;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import thesis.Graphics.GraphicsAnimation;
import thesis.Graphics.NodesKeeper;

/**
 * Created by Marco on 25/08/2015.
 */
public class WinningAnimation implements GraphicsAnimation {

    Node node;
    float t;
    float rotation = 0.01f;
    float scale = 0.2f;

    public WinningAnimation(Context context){
        node = NodesKeeper.generateNode(context, "stdShader" ,"#FFFFD700" , "trophy.obj");
        node.getRelativeTransform().setPosition(0, -0.2f, 0.5f);
        node.updateTree(new SFTransform3f());
        t=0;
    }

    @Override
    public void goOn() {

        SFMatrix3f matrix = SFMatrix3f.getScale(scale, scale, scale);
        matrix = matrix.MultMatrix(SFMatrix3f.getRotationY(t));
        node.getRelativeTransform().setMatrix(matrix);
        node.updateTree(new SFTransform3f());
        node.draw();
        t+=rotation;
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
