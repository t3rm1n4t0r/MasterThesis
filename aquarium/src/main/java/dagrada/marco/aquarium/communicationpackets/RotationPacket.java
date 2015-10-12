package dagrada.marco.aquarium.communicationpackets;

/**
 * Created by Marco on 12/10/2015.
 */
public class RotationPacket {
    private float rotationX, rotationY;

    public RotationPacket(float rotationX, float rotationY){
        this.rotationX = rotationX;
        this.rotationY = rotationY;
    }

    public float getRotationX() {
        return rotationX;
    }

    public void setRotationX(float rotationX) {
        this.rotationX = rotationX;
    }

    public float getRotationY() {
        return rotationY;
    }

    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }
}
