package dagrada.marco.aquarium.resources;

import java.io.IOException;

import dagrada.marco.aquarium.FileHandler;
import dagrada.marco.aquarium.Resource;
import dagrada.marco.aquarium.communicationpackets.BackgroundPacket;
import dagrada.marco.aquarium.communicationpackets.ItemsPacket;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 06/10/2015.
 */
public class ItemsHolder implements Resource {

    private int[][] grid;
    private GraphicsEngine engine;
    private FileHandler fileHandler;

    public ItemsHolder(GraphicsEngine engine, FileHandler fileHandler){
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
        ItemsPacket packet = new ItemsPacket(this.grid);
        engine.updateModel(packet);
        engine.update();
    }
}
