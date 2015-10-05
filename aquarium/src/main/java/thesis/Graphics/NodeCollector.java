package thesis.Graphics;

import java.util.LinkedList;
import java.util.List;

import sfogl.integration.Node;

/**
 * Created by Marco on 10/09/2015.
 */
public class NodeCollector {

    private LinkedList<Node> toBeAdded;
    private LinkedList<Node> nodes;

    public NodeCollector(){
        toBeAdded = new LinkedList<>();
        nodes = new LinkedList<>();
    }

    public void addNode(Node node){
        toBeAdded.add(node);
    }

    public void addNodes(List<Node> nodes){
        toBeAdded.addAll(nodes);
    }

    public List<Node> getNodes(){
        actualizeList();
        return this.nodes;
    }

    public void clearList(){
        this.nodes = new LinkedList<>();
        this.toBeAdded = new LinkedList<>();
    }

    private void actualizeList(){
        nodes.addAll(toBeAdded);
    }

}
