package thesis.touch;

/**
 * Created by Marco on 25/05/2015.
 */
public interface TouchActivity {

    public void onRightSwipe(float startX, float startY, float endX, float endY);
    public void onLeftSwipe(float startX, float startY, float endX, float endY);
    public void onUpSwipe(float startX, float startY, float endX, float endY);
    public void onDownSwipe(float startX, float startY, float endX, float endY);
    public void onDoubleTap(float x, float y);
    public void onLongPress(float x, float y);
    public void onSingleTapUp(float x, float y);
    public void onDown(float x, float y);
    public void onMove(float x, float y);
    public void onUp(float x, float y);
}
