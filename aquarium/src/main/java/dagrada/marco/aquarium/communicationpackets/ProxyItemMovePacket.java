package dagrada.marco.aquarium.communicationpackets;

/**
 * Created by Marco on 07/10/2015.
 */
public class ProxyItemMovePacket {

    float dx, dy;

    public ProxyItemMovePacket(float dx, float dy){
        this.dx = dx;
        this.dy = dy;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }
}
