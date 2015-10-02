package dagrada.marco.runner.core;

import android.os.Handler;

import dagrada.marco.runner.interactables.MusicalNote;
import thesis.utils.InteractablesCollector;
import thesis.utils.UpdatablesCollector;

/**
 * Created by Marco on 08/09/2015.
 */
public class ItemsGenerator implements Runnable {

    private static long minDelay = 200;

    private static int notes_number = 4;
    private static int notes_starting_number = 1;

    private static float start_X = 3.2f;
    private static float start_Y = 0.20f;
    private static float start_Z = 0;
    private static float z_step = 0.60f;

    private Handler handler;
    private long timeDelay;
    private InteractablesCollector interactablesCollector;
    private UpdatablesCollector updatablesCollector;
    private boolean stopped = true;

    private int generated=0;


    public ItemsGenerator(Handler handler, long timeDelay, UpdatablesCollector updatablesCollector, InteractablesCollector interactablesCollector){

        this.handler = handler;
        this.timeDelay = timeDelay;
        this.interactablesCollector = interactablesCollector;
        this.updatablesCollector = updatablesCollector;
    }

    public ItemsGenerator(Handler handler, UpdatablesCollector updatablesCollector, InteractablesCollector interactablesCollector){

        this.handler = handler;
        this.timeDelay = -1;
        this.interactablesCollector = interactablesCollector;
        this.updatablesCollector = updatablesCollector;
    }

    public void start(){
        if(isStopped()) {
            stopped = false;
            setNextLaunch();
        }
    }

    public void stop(){
        if(!isStopped()) {
            stopped = true;
            handler.removeCallbacks(this);
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public long getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(long timeDelay) {

        this.timeDelay = timeDelay;
    }

    @Override
    public void run() {

        MusicalNote note = generate();

        updatablesCollector.addUpdatable(note);
        interactablesCollector.addInteractable(note);

        generated++;

        //Log.d("Generated", String.valueOf(generated));

        setNextLaunch();

    }

    public MusicalNote generate(){
        int notetype = notes_starting_number + ((int) (Math.random() * notes_number));

        //Log.d("NOTE TYPE", String.valueOf(notetype));

        int binary = (int) (Math.random() * 3);

        float z = start_Z;

        if(binary == 0){
            z=z-z_step;
        }
        if(binary == 1){

        }
        if (binary == 2){
            z+=z_step;
        }

        return new MusicalNote(notetype, start_X, start_Y, z, 50);
    }

    private void setNextLaunch(){
        if (timeDelay < 0 ){

            long delay = minDelay + ((long)Math.random() * 2000);

            handler.postDelayed(this, delay);
        }
        else {
            handler.postDelayed(this, timeDelay);
        }
    }

    public int getGenerated() {
        return generated;
    }
}
