package dagrada.marco.runner;

/**
 * Created by Marco on 03/09/2015.
 */
public interface Updatable {
    public void update();
    public boolean canBeUpdated();
    public void setUpdatable(boolean updatable);
}
