package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;


import dagrada.marco.runner.GameController;
import thesis.touch.GestureFilter;


public class MainActivity extends Activity {

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting up levels for the game
        GraphicsRenderer renderer = new GraphicsRenderer(this);

        ArrayList<String> list = new ArrayList<>();

        GameController controller = new GameController();


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
