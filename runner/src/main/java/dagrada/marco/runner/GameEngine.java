package dagrada.marco.runner;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import dagrada.marco.runner.communicationpackets.ModelUpdatePacket;
import dagrada.marco.runner.interactables.Guitar;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 01/09/2015.
 */
public class GameEngine implements Runnable {


    private Handler handler;
    private long time_delay;
    private UpdatablesCollector updatablesCollector;
    private InteractablesCollector interactablesCollector;
    private Guitar character;
    private boolean stopped;
    GraphicsEngine engine;


    private float base_multiplier;

    public GameEngine(Handler handler, long time_delay, UpdatablesCollector updatablesCollector, InteractablesCollector interactablesCollector, Guitar character, GraphicsEngine engine){

        this.handler = handler;
        this.time_delay = time_delay;
        this.updatablesCollector = updatablesCollector;
        this.interactablesCollector = interactablesCollector;
        this.character = character;
        this.engine = engine;
        base_multiplier  = 1.0f;
        stopped = true;
    }

    public void start(){
        stopped = false;
        handler.postDelayed(this, time_delay);
    }

    public void stop(){
        this.stopped = true;
        handler.removeCallbacks(this);
    }

    @Override
    public void run() {


        updateUpdatables(updatablesCollector.getUpdatables());

        checkInteractions(this.character, interactablesCollector.getInteractables());


        updateGraphics();


        if(!stopped){

            handler.postDelayed(this, time_delay);
        }

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

    public void updateGraphics(){
        LinkedList<Updatable> items = new LinkedList<>();
        items.addAll(updatablesCollector.getUpdatables());
        items.add(character);
        engine.updateModel(new ModelUpdatePacket(items));
        engine.update();
    }
}
