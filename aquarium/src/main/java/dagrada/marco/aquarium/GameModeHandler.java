package dagrada.marco.aquarium;

import java.util.Observable;

/**
 * Created by Marco on 11/10/2015.
 */
public class GameModeHandler extends Observable {

    public static final int GAMEMODE_EDIT = 0;
    public static final int GAMEMODE_PANORAMIC = 1;

    private int gamemode;

    public GameModeHandler(){

        gamemode = 0;

    }

    public void setGamemode(int gamemode){
        switch (gamemode){
            case 0:case 1:
                this.gamemode = gamemode;
                break;
            default:break;
        }
        setChanged();
        notifyObservers(this.gamemode);
    }

    public int getGamemode() {
        return gamemode;
    }
}
