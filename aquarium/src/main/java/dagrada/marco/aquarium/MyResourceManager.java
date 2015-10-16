package dagrada.marco.aquarium;

import java.util.HashMap;

import thesis.utils.Resource;
import thesis.utils.ResourceManager;

/**
 * Created by Marco on 06/10/2015.
 */
public class MyResourceManager implements ResourceManager {

    public static final int BACKGROUND = 1;
    public static final int ITEMS = 2;
    public static final int FISHES = 3;

    private HashMap<Integer, Resource> resources;

    public MyResourceManager(){
        resources = new HashMap<>();
    }

    @Override
    public void setResource(int resourceID, Resource resource){
        this.resources.put(resourceID, resource);
    }

    @Override
    public Resource getResource(int resourceID){
        return this.resources.get(resourceID);
    }


    @Override
    public void loadAllResources(){
        for (Integer current : resources.keySet()) {
            loadResource(current);
        }
    }

    @Override
    public void storeAllResources(){
        for (Integer current : resources.keySet()) {
            storeResource(current);
        }
    }

    @Override
    public void loadResource(int resourceID){

        switch (resourceID){
            case 1:case 2:case 3:
                resources.get(resourceID).load();
                break;

            default:break;
        }

    }

    @Override
    public void storeResource(int resourceID){
        switch (resourceID){
            case 1:case 2:case 3:
                resources.get(resourceID).store();
                break;

            default:break;
        }
    }

}
