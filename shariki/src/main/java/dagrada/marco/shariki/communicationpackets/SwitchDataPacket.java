package dagrada.marco.shariki.communicationpackets;

/**
 * Created by Marco on 26/08/2015.
 */
public class SwitchDataPacket {

    private int row1, col1, row2, col2;

    public SwitchDataPacket(int row1, int col1, int row2, int col2){
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;
    }

    public int getRow1() {
        return row1;
    }

    public void setRow1(int row1) {
        this.row1 = row1;
    }

    public int getCol2() {
        return col2;
    }

    public void setCol2(int col2) {
        this.col2 = col2;
    }

    public int getCol1() {
        return col1;
    }

    public void setCol1(int col1) {
        this.col1 = col1;
    }

    public int getRow2() {
        return row2;
    }

    public void setRow2(int row2) {
        this.row2 = row2;
    }
}
