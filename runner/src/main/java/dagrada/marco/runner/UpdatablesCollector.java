package dagrada.marco.runner;

import java.util.LinkedList;

/**
 * Created by Marco on 03/09/2015.
 */
public class UpdatablesCollector {

    private LinkedList<Updatable> list;
    private LinkedList<Updatable> toBeAdded;
    private LinkedList<Updatable> toBeRemoved;

    public UpdatablesCollector(){
        list = new LinkedList<>();
        toBeAdded = new LinkedList<>();
        toBeRemoved = new LinkedList<>();
    }

    public void addUpdatable(Updatable updatable){
        toBeAdded.add(updatable);
    }

    public void removeUpdatable(Updatable updatable){
        toBeRemoved.add(updatable);
    }

    public LinkedList<Updatable> getUpdatables(){
        actualizeList();
        return list;
    }

    public void actualizeList(){
        list.addAll(toBeAdded);
        for (Updatable  current: toBeRemoved) {
            list.remove(current);
        }
    }

}
