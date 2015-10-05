package thesis.touch;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Marco on 25/05/2015.
 */
public class GestureFilter extends android.view.GestureDetector.SimpleOnGestureListener {
    // Swipe properties, you can change it to make the swipe
    // longer or shorter and speed
    private int swipe_Min_Distance = 100;
    private int swipe_Max_Distance = 9999;
    private int swipe_Min_Velocity = 10;
    private TouchActivity myactivity;

    public GestureFilter(TouchActivity activity){
        myactivity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        final float xDistance = Math.abs(e1.getX() - e2.getX());
        final float yDistance = Math.abs(e1.getY() - e2.getY());

        if(xDistance > this.swipe_Max_Distance || yDistance > this.swipe_Max_Distance)
            return false;

        velocityX = Math.abs(velocityX);
        velocityY = Math.abs(velocityY);
        boolean result = false;

        if(velocityX > this.swipe_Min_Velocity && xDistance > this.swipe_Min_Distance){
            if(e1.getX() > e2.getX()) // right to left
                myactivity.onLeftSwipe(e1.getX(), e1.getY(), e2.getX(), e2.getY());
            else
                myactivity.onRightSwipe(e1.getX(), e1.getY(), e2.getX(), e2.getY());

            result = true;
        }
        else if(velocityY > this.swipe_Min_Velocity && yDistance > this.swipe_Min_Distance){
            if(e1.getY() > e2.getY()) // bottom to up
                myactivity.onUpSwipe(e1.getX(), e1.getY(), e2.getX(), e2.getY());
            else
                myactivity.onDownSwipe(e1.getX(), e1.getY(), e2.getX(), e2.getY());

            result = true;
        }

        return result;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        myactivity.onDoubleTap(e.getX(), e.getY());
        return super.onDoubleTap(e);
    }

    @Override
    public void onLongPress(MotionEvent event) {
        myactivity.onLongPress(event.getX(), event.getY());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        myactivity.onSingleTapUp(event.getX(), event.getY());
        return true;
    }

}