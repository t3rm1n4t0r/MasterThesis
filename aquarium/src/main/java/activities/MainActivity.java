package activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;


import thesis.touch.GestureFilter;


public class MainActivity extends Activity {

    private GestureDetector detector;

    private long updateDelay = 80;
    private long generationDelay = 3000;

    private GraphicsView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ProxyRenderer renderer = new ProxyRenderer(this);



        //GameController controller = new GameController(engine, itemsGenerator, guitarController);




        view = new GraphicsView(this, renderer);
        detector = new GestureDetector(this, new GestureFilter(view));
        detector.setIsLongpressEnabled(false);
        setContentView(view);




    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if (detector.onTouchEvent(event)== true){
            //Fling or other gesture detected (not logpress because it is disabled)
        }
        else{
            //Manually handle the event.
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                view.onUp(event.getX(), event.getY());
                //Remember the time and press position
                Log.e("test", "Action down");
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE)
            {
                //Check if user is actually longpressing, not slow-moving
                // if current position differs much then press positon then discard whole thing
                // If position change is minimal then after 0.5s that is a longpress. You can now process your other gestures

                view.onMove(event.getX(), event.getY());

                Log.e("test","Action move");
            }
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                //Get the time and position and check what that was :)

                view.onUp(event.getX(), event.getY());
                Log.e("test","Action up");
            }

        }
        return super.onTouchEvent(event);
    }

}
