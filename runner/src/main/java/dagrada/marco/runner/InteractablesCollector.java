package dagrada.marco.runner;

import java.util.LinkedList;

/**
 * Created by Marco on 04/09/2015.
 */
public class InteractablesCollector {

    private LinkedList<Interactable> list;
    private LinkedList<Interactable> toBeAdded;
    private LinkedList<Interactable> toBeRemoved;

    public InteractablesCollector(){
        list = new LinkedList<>();
        toBeAdded = new LinkedList<>();
        toBeRemoved = new LinkedList<>();
    }

    public void addInteractable(Interactable interactable){
        toBeAdded.add(interactable);
    }

    public void removeInteractable(Interactable interactable){
        toBeRemoved.add(interactable);
    }

    public LinkedList<Interactable> getInteractables(){
        actualizeList();
        return list;
    }

    private void actualizeList(){
        list.addAll(toBeAdded);
        list.removeAll(toBeRemoved);
    }

}


