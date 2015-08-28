package dagrada.marco.runner;

/**
 * Created by Marco on 28/08/2015.
 */
public interface Interactable {

    public void interact(Interactable interactable);
    public boolean canInteract(Interactable interactable);
}
