package dagrada.marco.aquarium.communicationpackets;

/**
 * Created by Marco on 07/10/2015.
 */
public class ProxyItemPacket {

    int item;


    public static final int UNSELECTED = 0;
    public static final int STONE = 1;
    public static final int PLANT = 2;
    public static final int VASE = 3;


    public ProxyItemPacket(int item){
        this.item = item;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }
}
