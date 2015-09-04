package dagrada.marco.runner;

import android.os.Handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marco on 01/09/2015.
 */
public class GameEngine implements Runnable {


    private Handler handler;
    private long time_delay;
    private UpdatablesCollector updatablesCollector;
    private InteractablesCollector interactablesCollector;
    private ScoreKeeper scoreKeeper;
    private Interactable character;

    private float base_multiplier;

    public GameEngine(Handler handler, long time_delay, UpdatablesCollector updatablesCollector, InteractablesCollector interactablesCollector, Interactable character){
        this.handler = handler;
        this.time_delay = time_delay;
        this.updatablesCollector = updatablesCollector;
        this.interactablesCollector = interactablesCollector;
        this.character = character;
        base_multiplier  = 1.0f;
    }

    @Override
    public void run() {


        updateUpdatables(updatablesCollector.getUpdatables());

        checkInteractions(this.character, interactablesCollector.getInteractables());


        handler.postDelayed(this, time_delay);

    }

    public void updateUpdatables(List<Updatable> list){
        for (Updatable  updatable: list) {

            if(updatable.canBeUpdated()){

                updatable.update(base_multiplier);
            }

            else{
                updatablesCollector.removeUpdatable(updatable);
            }

        }
    }

    public void checkInteractions(Interactable character, List<Interactable> list){

        for (Interactable  interactable: list) {
            character.interact(interactable);
        }
    }
}
