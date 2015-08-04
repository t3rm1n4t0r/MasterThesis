package thesis.touch;

/**
 * Created by Marco on 25/05/2015.
 */
public interface TouchActivity {

    public void onRightSwipe(float distance);
    public void onLeftSwipe(float distance);
    public void onUpSwipe(float distance);
    public void onDownSwipe(float distance);
    public void onDoubleTap();
    public void onLongPress();
    public void onSingleTapUp(float x, float y);
}
