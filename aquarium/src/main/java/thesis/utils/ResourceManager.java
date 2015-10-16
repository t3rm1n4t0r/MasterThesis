package thesis.utils;

/**
 * Created by Marco on 16/10/2015.
 */
public interface ResourceManager {
    void setResource(int resourceID, Resource resource);

    Resource getResource(int resourceID);

    void loadAllResources();

    void storeAllResources();

    void loadResource(int resourceID);

    void storeResource(int resourceID);
}
