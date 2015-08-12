package thesis.Graphics;

/**
 * Created by Marco on 05/08/2015.
 */
public interface GraphicsEngine {

    public void update();
    public void updateModel(Object obj);
    public void addAnimation(GraphicsAnimation animation);
    public void animate(Object object);
    public boolean isBlocked();

}
