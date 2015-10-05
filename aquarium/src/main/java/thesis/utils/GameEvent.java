package thesis.utils;

import java.util.LinkedList;

/**
 * Created by Marco on 19/08/2015.
 */
public abstract class GameEvent{

    private LinkedList<GameEventManager> managers = new LinkedList<>();
    private Object EventData = null;


    public void addManager(GameEventManager manager){
        managers.addLast(manager);
    }
    public void removeAllManagers(){
        managers = new LinkedList<>();
    }

    public void notifyManagers(){
        for (GameEventManager manager : managers) {
            manager.notifyEventConclusion(this, null);
        }
    }

    public void notifyManagers(Object data){
        for (GameEventManager manager : managers) {
            manager.notifyEventConclusion(this, data);
        }
    }

    public Object getEventData() {
        return EventData;
    }

    public void setEventData(Object eventData) {
        EventData = eventData;
    }

    public LinkedList<GameEventManager> getManagers() {
        return managers;
    }

    public abstract void happen();
}
