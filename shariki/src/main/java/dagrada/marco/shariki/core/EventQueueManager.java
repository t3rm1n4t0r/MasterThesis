package dagrada.marco.shariki.core;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import thesis.GameEvent;

/**
 * Created by Marco on 19/08/2015.
 */
public class EventQueueManager {

    private LinkedList<GameEvent> list;

    public EventQueueManager(){
        list = new LinkedList<>();
    }

    public void addToQueue(GameEvent gameEvent){
        list.addLast(gameEvent);
    }

    public GameEvent getNextEvent(){
        try{
            return list.removeFirst();

        }
        catch (NoSuchElementException e){
            return null;
        }
    }

    public int getQueueSize(){
        return list.size();
    }


}
