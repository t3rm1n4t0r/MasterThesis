package thesis.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Marco on 06/10/2015.
 */
public interface FileHandler {

    public Object readFromFile() throws IOException;
    public void writeToFile(Object obj) throws IOException;
}
