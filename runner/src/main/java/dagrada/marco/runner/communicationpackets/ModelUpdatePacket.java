package dagrada.marco.runner.communicationpackets;

import java.util.LinkedList;
import java.util.List;

import dagrada.marco.runner.Updatable;
import dagrada.marco.runner.interactables.MusicalNote;

/**
 * Created by Marco on 08/09/2015.
 */
public class ModelUpdatePacket {

    private List<Updatable> items;

    public ModelUpdatePacket(List<Updatable> items){
        this.items = items;
    }

    public List<Updatable> getItems() {
        return items;
    }

    public void setItems(List<Updatable> items) {
        this.items = items;
    }
}
