package dagrada.marco.runner.communicationpackets;

import java.util.LinkedList;

import thesis.utils.Updatable;

/**
 * Created by Marco on 08/09/2015.
 */
public class ModelUpdatePacket {

    private LinkedList<Updatable> items;
    private int score;

    public ModelUpdatePacket(LinkedList<Updatable> items, int score){
        this.items = items;
        this.score = score;
    }

    public LinkedList<Updatable> getItems() {
        return items;
    }

    public void setItems(LinkedList<Updatable> items) {
        this.items = items;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
