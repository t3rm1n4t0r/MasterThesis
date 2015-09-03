package dagrada.marco.runner;

import android.os.Handler;

import java.util.LinkedList;

/**
 * Created by Marco on 01/09/2015.
 */
public class GameEngine implements Runnable {


    private Handler handler;
    private long time_delay;
    private UpdatablesCollector collector;
    private ScoreKeeper scoreKeeper;

    private float base_multiplier;

    public GameEngine(Handler handler, long time_delay, UpdatablesCollector collector){
        this.handler = handler;
        this.time_delay = time_delay;
        this.collector = collector;
        base_multiplier  = 1.0f;
    }

    @Override
    public void run() {


        LinkedList<Updatable> list = collector.getUpdatables();

        for (Updatable  updatable: list) {

            if(updatable.canBeUpdated()){

                updatable.update(base_multiplier);
            }

            else{
                collector.removeUpdatable(updatable);
            }

        }






        handler.postDelayed(this, time_delay);

    }
}
