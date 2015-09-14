package dagrada.marco.runner.interactables;

import android.util.Log;

import dagrada.marco.runner.Interactable;
import dagrada.marco.runner.ScoreKeeper;
import dagrada.marco.runner.Updatable;

/**
 * Created by Marco on 03/09/2015.
 */
public class Guitar implements Interactable, Updatable{

    private final float INTERACTION_RADIUS = 0.5f;

    private float x, y, z;
    private ScoreKeeper scoreKeeper;

    public Guitar(ScoreKeeper scoreKeeper){
        this.scoreKeeper = scoreKeeper;
    }

    public Guitar(float x, float y, float z, ScoreKeeper scoreKeeper){
        setX(x);
        setY(y);
        setZ(z);
        this.scoreKeeper = scoreKeeper;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setScoreKeeper(ScoreKeeper scoreKeeper) {
        this.scoreKeeper = scoreKeeper;
    }

    @Override
    public void interact(Interactable interactable) {
        if (canInteractWith(interactable)){
            if(interactable instanceof MusicalNote){
                MusicalNote note = (MusicalNote) interactable;
                scoreKeeper.updateScore(note.getScore());
                //Log.e("SCORE", String.valueOf(scoreKeeper.getScore()));
                note.setUpdatable(false);
                note.interact(this);
            }
        }
    }

    @Override
    public boolean canInteractWith(Interactable interactable) {
        if(interactable instanceof MusicalNote){
            MusicalNote note = (MusicalNote) interactable;
            if ((note.getX() - this.getX()) <= INTERACTION_RADIUS && note.getZ() == this.getZ()){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }

    }

    @Override
    public void update() {

    }

    @Override
    public void update(Object data) {
        setZ((float)data);

    }

    @Override
    public boolean canBeUpdated() {
        return false;
    }

    @Override
    public void setUpdatable(boolean updatable) {

    }

    public ScoreKeeper getScoreKeeper() {
        return scoreKeeper;
    }
}
