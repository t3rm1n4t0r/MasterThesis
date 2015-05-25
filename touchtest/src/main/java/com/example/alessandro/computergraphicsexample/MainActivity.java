package com.example.alessandro.computergraphicsexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import thesis.touch.GestureFilter;


public class MainActivity extends Activity {

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GraphicsView view = new GraphicsView(this);
        detector = new GestureDetector(this, new GestureFilter(view));
        setContentView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
