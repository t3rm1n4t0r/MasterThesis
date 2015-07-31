package dagrada.marco.shariki;

import sfogl.integration.Node;

/**
 * Created by Marco on 31/07/2015.
 */
public class Marble {

    private Node node;
    private int[] indices;

    public Marble(Node node, int[] indices){
        this.node = node;
        this.indices = indices;
    }

    public Node getNode() {
        return node;
    }

    public int[] getIndices() {
        return indices;
    }
}
