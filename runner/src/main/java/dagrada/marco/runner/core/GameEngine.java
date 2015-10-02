package dagrada.marco.runner.core;

import android.os.Handler;

import java.util.LinkedList;
import java.util.List;

import dagrada.marco.runner.communicationpackets.ModelUpdatePacket;
import dagrada.marco.runner.interactables.Guitar;
import thesis.Graphics.GraphicsEngine;
import thesis.utils.Interactable;
import thesis.utils.InteractablesCollector;
import thesis.utils.InteractionChecker;
import thesis.utils.Updatable;
import thesis.utils.UpdatablesCollector;
import thesis.utils.Updater;

/**
 * Created by Marco on 01/09/2015.
 */
public class GameEngine implements Runnable, Updater, InteractionChecker {


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
        if(time_delay > 0)
            this.time_delay = time_delay;
        else {
            this.time_delay = 100;
        }
        this.updatablesCollector = updatablesCollector;
        this.interactablesCollector = interactablesCollector;
        this.character = character;
        this.engine = engine;
        base_multiplier  = 1.0f;
        stopped = true;
    }

    public void start(){

        if(isStopped()) {
            stopped = false;
            handler.postDelayed(this, time_delay);
        }
    }

    public void stop(){

        if(!isStopped()) {
            this.stopped = true;
            handler.removeCallbacks(this);
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    @Override
    public void run() {


        updateUpdatables();

        checkInteractions();


        updateGraphics();


        if(!stopped){

            handler.postDelayed(this, time_delay);
        }

    }

    public void updateUpdatables(){

        List<Updatable> list = updatablesCollector.getUpdatables();

        for (Updatable  updatable: list) {

            if(updatable.canBeUpdated()){

                updatable.update(base_multiplier);
            }

            else{
                updatablesCollector.removeUpdatable(updatable);
                interactablesCollector.removeInteractable((Interactable)updatable);
            }

        }
    }

    public void checkInteractions(){

        Interactable character=this.character;
        List<Interactable> list = interactablesCollector.getInteractables();

        //Log.d("INTERACTIONS", String.valueOf(list.size()));
        for (Interactable  interactable: list) {
            character.interact(interactable);
        }
    }

    public void updateGraphics(){
        LinkedList<Updatable> items = new LinkedList<>();
        items.addAll(updatablesCollector.getUpdatables());
        items.add(character);
        engine.updateModel(new ModelUpdatePacket(items, character.getScoreKeeper().getScore()));
        engine.update();
    }

    public void accelerate(){
        this.base_multiplier+=0.1f;
    }

    public float getBase_multiplier() {
        return base_multiplier;
    }
}
