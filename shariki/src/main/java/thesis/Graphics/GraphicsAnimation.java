package thesis.Graphics;

import thesis.Graphics.Exceptions.AnimationEndException;

/**
 * Created by Marco on 05/08/2015.
 */
public interface GraphicsAnimation {
    public void goOn() throws AnimationEndException;
    public boolean isblocking();
}
