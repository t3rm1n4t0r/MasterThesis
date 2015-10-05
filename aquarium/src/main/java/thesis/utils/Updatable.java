package thesis.utils;


/**
 * Created by Marco on 03/09/2015.
 */
public interface Updatable {
    public void update();
    public void update(Object data);
    public boolean canBeUpdated();
    public void setUpdatable(boolean updatable);
}
