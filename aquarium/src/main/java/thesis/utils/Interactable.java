package thesis.utils;

/**
 * Created by Marco on 28/08/2015.
 */
public interface Interactable {

    public void interact(Interactable interactable);
    public boolean canInteractWith(Interactable interactable);
}
