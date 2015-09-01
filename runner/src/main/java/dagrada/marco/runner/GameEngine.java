package dagrada.marco.runner;

import android.os.Handler;

/**
 * Created by Marco on 01/09/2015.
 */
public class GameEngine implements Runnable {


    private Handler handler;
    private long time_delay;

    public GameEngine(Handler handler, long time_delay){
        this.handler = handler;
        this.time_delay = time_delay;
    }

    @Override
    public void run() {




        handler.postDelayed(this, time_delay);

    }
}
