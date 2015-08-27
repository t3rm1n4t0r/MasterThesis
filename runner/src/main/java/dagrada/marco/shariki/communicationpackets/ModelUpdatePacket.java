package dagrada.marco.shariki.communicationpackets;

/**
 * Created by Marco on 24/08/2015.
 */
public class ModelUpdatePacket {

    private boolean changed, gameEnd;
    public ModelUpdatePacket(boolean changed, boolean gameEnd){
        this.changed = changed;
        this.gameEnd = gameEnd;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }
}
