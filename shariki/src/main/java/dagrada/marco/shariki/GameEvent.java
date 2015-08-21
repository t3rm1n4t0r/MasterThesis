package dagrada.marco.shariki;

import java.util.LinkedList;

/**
 * Created by Marco on 19/08/2015.
 */
public abstract class GameEvent{

    private LinkedList<EventManager> managers = new LinkedList<>();
    private Object EventData = null;


    public void addManager(EventManager manager){
        managers.addLast(manager);
    }
    public void removeAllManagers(){
        managers = new LinkedList<>();
    }

    public void notifyManagers(){
        for (EventManager  manager : managers) {
            manager.notifyEventConclusion(this, null);
        }
    }

    public void notifyManagers(Object data){
        for (EventManager  manager : managers) {
            manager.notifyEventConclusion(this, data);
        }
    }

    public Object getEventData() {
        return EventData;
    }

    public void setEventData(Object eventData) {
        EventData = eventData;
    }

    public LinkedList<EventManager> getManagers() {
        return managers;
    }

    public abstract void happen();
}
