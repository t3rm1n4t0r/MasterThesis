package dagrada.marco.shariki.controllers;

import dagrada.marco.shariki.core.GameEventHandler;
import dagrada.marco.shariki.communicationpackets.SwitchDataPacket;

/**
 * Created by Marco on 28/07/2015.
 */
public class GameController {

    private GameEventHandler handler;

    public GameController(GameEventHandler handler){
        this.handler = handler;

    }


    public GameEventHandler getHandler() {
        return handler;
    }

    public void switchPosition(int row1, int col1, int row2, int col2){

        //Log.d("PRIMO", String.valueOf(row1)+ " "+ String.valueOf(col1));

        //Log.d("SECONDO", String.valueOf(row2)+ " "+ String.valueOf(col2));

        SwitchDataPacket packet = new SwitchDataPacket(row1, col1, row2, col2);
        handler.startEventChain(packet);

    }


    /*
    public int[] getMatrixIndeces(float x, float y, float screenWidth, float screenHeight){

        int indeces[] = new int[2];
        indeces[0] = (int) (x / (screenWidth/GameModelHandler.MAX_WIDTH));
        indeces[1] = (int) (y / (screenWidth/GameModelHandler.MAX_HEIGTH));
        return indeces;
    }
    */
}
