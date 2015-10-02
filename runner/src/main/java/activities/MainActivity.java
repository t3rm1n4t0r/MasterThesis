package activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;


import dagrada.marco.runner.controllers.GameController;
import dagrada.marco.runner.core.GameEngine;
import dagrada.marco.runner.controllers.GuitarController;
import thesis.utils.InteractablesCollector;
import dagrada.marco.runner.core.ItemsGenerator;
import dagrada.marco.runner.core.ScoreKeeper;
import thesis.utils.UpdatablesCollector;
import dagrada.marco.runner.interactables.Guitar;
import thesis.touch.GestureFilter;


public class MainActivity extends Activity {

    private GestureDetector detector;

    private long updateDelay = 80;
    private long generationDelay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GraphicsRenderer renderer = new GraphicsRenderer(this);

        Handler handler = new Handler(Looper.getMainLooper());

        InteractablesCollector interactablesCollector = new InteractablesCollector();
        UpdatablesCollector updatablesCollector = new UpdatablesCollector();
        Guitar guitar = new Guitar(-1.3f, 0.30f, 0, null);

        GuitarController guitarController = new GuitarController(guitar);

        GameEngine engine = new GameEngine(handler, updateDelay, updatablesCollector, interactablesCollector, guitar, renderer);
        ScoreKeeper keeper = new ScoreKeeper(engine);
        guitar.setScoreKeeper(keeper);
        ItemsGenerator itemsGenerator = new ItemsGenerator(handler, generationDelay, updatablesCollector, interactablesCollector);

        GameController controller = new GameController(engine, itemsGenerator, guitarController);




        GraphicsView view = new GraphicsView(this, controller, renderer);
        detector = new GestureDetector(this, new GestureFilter(view));
        setContentView(view);




    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
