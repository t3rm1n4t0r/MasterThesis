package activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;


import dagrada.marco.runner.GameController;
import dagrada.marco.runner.GameEngine;
import dagrada.marco.runner.GuitarController;
import dagrada.marco.runner.InteractablesCollector;
import dagrada.marco.runner.ItemsGenerator;
import dagrada.marco.runner.UpdatablesCollector;
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
        Guitar guitar = new Guitar(-1.5f, 0.30f, 0);

        GuitarController guitarController = new GuitarController(guitar);

        GameEngine engine = new GameEngine(handler, updateDelay, updatablesCollector, interactablesCollector, guitar, renderer);
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
