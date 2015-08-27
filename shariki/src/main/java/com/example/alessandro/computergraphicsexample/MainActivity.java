package com.example.alessandro.computergraphicsexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;

import dagrada.marco.shariki.core.GameModelHandler;
import dagrada.marco.shariki.core.GameStateHandler;
import dagrada.marco.shariki.controllers.GameController;
import thesis.touch.GestureFilter;


public class MainActivity extends Activity {

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting up levels for the game
        GraphicsRenderer renderer = new GraphicsRenderer(this);
        GameModelHandler handler = new GameModelHandler(this);
        ArrayList<String> list = new ArrayList<>();

        GameStateHandler stateHandler = new GameStateHandler(this, handler, renderer);


        GameController controller = new GameController(stateHandler);


        GraphicsView view = new GraphicsView(this, controller, renderer);
        detector = new GestureDetector(this, new GestureFilter(view));
        setContentView(view);

        list.add(0, "level0.txt");
        list.add(1, "level2.txt");

        stateHandler.startGame(list);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
