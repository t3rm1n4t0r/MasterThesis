package thesis;

import android.content.Context;

import java.util.HashMap;
import sfogl.integration.Node;

/**
 * Created by Marco on 18/05/2015.
 */
public class NodesKeeper {

    private ShadersKeeper shaderskeeper;
    private MaterialsKeeper materialskeeper;
    private ModelsKeeper modelskeeper;
    private MeshesKeeper mesheskeeper;
    private TexturesKeeper texturekeeper;

    private HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();



    public HashMap<Integer, Node> getAllNodes(){
        return this.nodes;
    }

    public Node generateNode(Context context, String shadername, int textureID, String objFilePath){


    return null;
    }

}
