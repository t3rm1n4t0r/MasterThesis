package dagrada.marco.shariki;

import java.util.LinkedList;

/**
 * Created by Marco on 19/08/2015.
 */
public abstract class GameEvent{

    private LinkedList<EventManager> managers = new LinkedList();

    public void addManager(EventManager manager){
        managers.addLast(manager);
    }
    public void removeAllManagers(){
        managers = new LinkedList<>();
    }

    public void notifyManagers(){
        for (EventManager  manager : managers) {
            manager.notifyEventConclusion(this);
        }
    }

    public abstract void happen();
}
