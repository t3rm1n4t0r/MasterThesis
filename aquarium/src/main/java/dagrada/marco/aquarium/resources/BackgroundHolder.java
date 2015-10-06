package dagrada.marco.aquarium.resources;

import java.io.FileOutputStream;
import java.io.IOException;

import dagrada.marco.aquarium.FileHandler;
import dagrada.marco.aquarium.Resource;
import dagrada.marco.aquarium.communicationpackets.BackgroundPacket;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 06/10/2015.
 */
public class BackgroundHolder implements Resource {

    private int[][] grid;
    private GraphicsEngine engine;
    private FileHandler fileHandler;

    public BackgroundHolder(GraphicsEngine engine, FileHandler fileHandler){
        this.engine = engine;
        grid = new int[5][5];
        this.fileHandler = fileHandler;
    }

    @Override
    public void store() {
        try {
            fileHandler.writeToFile(this.grid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateGraphics();
    }

    @Override
    public void load() {
        try {
            int[][] result = (int[][])fileHandler.readFromFile();
            this.grid = result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateGraphics();
    }

    public void updateGraphics(){
        BackgroundPacket packet = new BackgroundPacket(this.grid);
        engine.updateModel(packet);
        engine.update();
    }
}
