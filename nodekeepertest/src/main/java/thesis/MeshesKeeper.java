package thesis;

import android.content.Context;

import java.util.HashMap;

import objLoader.ObjLoader;
import sfogl.integration.ArrayObject;
import sfogl.integration.Mesh;

/**
 * Created by Marco on 18/05/2015.
 */
public class MeshesKeeper {

    private static HashMap<String, Mesh> meshes = new HashMap<String, Mesh>();

    public static Mesh generateMesh(Context context, String meshFilePath){
        if(meshes.containsKey(meshFilePath)){
                return meshes.get(meshFilePath);
        }
        else{
            ArrayObject[] objects = ObjLoader.arrayObjectFromFile(context, meshFilePath);
            Mesh mesh=new Mesh(objects[0]);
            mesh.init();
            meshes.put(meshFilePath, mesh);
            return mesh;
        }
    }

    public static HashMap<String, Mesh> getMeshes() {
        return meshes;
    }
}
