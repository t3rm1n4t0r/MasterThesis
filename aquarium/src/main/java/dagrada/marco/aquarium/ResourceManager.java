package dagrada.marco.aquarium;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import dagrada.marco.aquarium.resources.BackgroundHolder;
import objLoader.ObjArrays;

/**
 * Created by Marco on 06/10/2015.
 */
public class ResourceManager {

    public static final int BACKGROUND = 1;
    public static final int ITEMS = 2;
    public static final int FISHES = 3;

    private HashMap<Integer, Resource> resources;

    public ResourceManager(){
        resources = new HashMap<>();
    }

    public void setResource(int resourceID, Resource resource){
        this.resources.put(resourceID, resource);
    }

    public Resource getResource(int resourceID){
        return this.resources.get(resourceID);
    }


    public void loadAllResources(){
        for (Integer current : resources.keySet()) {
            loadResource(current);
        }
    }

    public void storeAllResources(){
        for (Integer current : resources.keySet()) {
            storeResource(current);
        }
    }

    public void loadResource(int resourceID){

        switch (resourceID){
            case 1:case 2:case 3:
                resources.get(resourceID).load();
                break;

            default:break;
        }

    }

    public void storeResource(int resourceID){
        switch (resourceID){
            case 1:case 2:case 3:
                resources.get(resourceID).store();
                break;

            default:break;
        }
    }

}
