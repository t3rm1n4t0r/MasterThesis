package com.example.alessandro.computergraphicsexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;

import dagrada.marco.shariki.GameStatusHandler;
import dagrada.marco.shariki.controllers.MatrixController;
import thesis.touch.GestureFilter;


public class MainActivity extends Activity {

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting up levels for the game
        GameStatusHandler handler = new GameStatusHandler();
        ArrayList<String> list = new ArrayList<>();

        list.add(0, getFilesDir().getAbsolutePath() + "/level1.txt");
        try {
            handler.loadGame(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MatrixController controller = new MatrixController(handler);

        GraphicsView view = new GraphicsView(this, controller);
        detector = new GestureDetector(this, new GestureFilter(view));
        setContentView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
