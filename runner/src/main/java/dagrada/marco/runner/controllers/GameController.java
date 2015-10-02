package dagrada.marco.runner.controllers;


import dagrada.marco.runner.core.GameEngine;
import dagrada.marco.runner.core.ItemsGenerator;

/**
 * Created by Marco on 28/07/2015.
 */
public class GameController {

    private GameEngine engine;
    private ItemsGenerator generator;
    private boolean paused;
    private GuitarController guitarController;

    public GameController(GameEngine engine, ItemsGenerator generator, GuitarController guitarController){

        this.engine = engine;
        this.generator = generator;
        this.guitarController = guitarController;
        paused = true;
    }

    public void toggleGame(){

        if(paused){
            engine.start();
            generator.start();
            paused = false;
        }
        else {
            engine.stop();
            generator.stop();
            paused = true;
        }

    }

    public void moveRight(){
        guitarController.moveRight();
    }
    public void moveLeft(){
        guitarController.moveLeft();
    }


}
