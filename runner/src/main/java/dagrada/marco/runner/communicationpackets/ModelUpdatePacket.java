package dagrada.marco.runner.communicationpackets;

import java.util.LinkedList;
import java.util.List;

import dagrada.marco.runner.Updatable;
import dagrada.marco.runner.interactables.MusicalNote;

/**
 * Created by Marco on 08/09/2015.
 */
public class ModelUpdatePacket {

    private LinkedList<Updatable> items;

    public ModelUpdatePacket(LinkedList<Updatable> items){
        this.items = items;
    }

    public LinkedList<Updatable> getItems() {
        return items;
    }

    public void setItems(LinkedList<Updatable> items) {
        this.items = items;
    }
}
