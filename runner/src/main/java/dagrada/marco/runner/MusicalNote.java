package dagrada.marco.runner;

/**
 * Created by Marco on 03/09/2015.
 */
public class MusicalNote implements ScorableItem, Updatable{

    private final float STEP_X = 0.01f;
    private final float LIMIT_X = -1.5f;
    private boolean updatable;

    private int note;
    private float x, y, z;
    private int score;

    public MusicalNote(int note, float x, float y, float z, int score){

        setNote(note);
        setX(x);
        setY(y);
        setZ(z);
        setScore(score);
        setUpdatable(true);

    }

    public MusicalNote(){

    }


    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setPosition(float x, float y, float z){
        setX(x);
        setY(y);
        setZ(z);

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void update() {
        if(canBeUpdated()){
            setX(getX() + STEP_X);
            if(getX() == LIMIT_X){
                setUpdatable(false);
            }
        }
    }

    @Override
    public boolean canBeUpdated() {
        return this.updatable;
    }

    @Override
    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }
}
