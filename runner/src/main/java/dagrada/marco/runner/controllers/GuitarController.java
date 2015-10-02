package dagrada.marco.runner.controllers;

import dagrada.marco.runner.interactables.Guitar;

/**
 * Created by Marco on 14/09/2015.
 */
public class GuitarController {

    private Guitar guitar;
    private final float binary_1 = -0.60f;
    private final float binary_2 = 0f;
    private final float binary_3 = 0.60f;

    private float[] binaries = new float[3];
    private int current;


    public GuitarController(Guitar guitar){
        this.guitar = guitar;
        binaries[0] = binary_1;
        binaries[1] = binary_2;
        binaries[2] = binary_3;
        current = 1;
        guitar.update(binaries[current]);
    }

    public void moveRight(){
        if(current<2) {
            current++;
            guitar.update(binaries[current]);
        }
    }

    public void moveLeft(){
        if(current>0){
            current--;
            guitar.update(binaries[current]);
        }
    }
}
