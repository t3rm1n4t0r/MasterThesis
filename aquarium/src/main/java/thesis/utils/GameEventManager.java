package thesis.utils;

/**
 * Created by Marco on 19/08/2015.
 */
public interface GameEventManager {

    public void startEventChain(Object data);
    public void notifyEventConclusion(GameEvent event, Object data);
}
