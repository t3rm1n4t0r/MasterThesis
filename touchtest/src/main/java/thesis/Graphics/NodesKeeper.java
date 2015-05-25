package thesis.Graphics;

import android.content.Context;

import java.util.HashMap;

import sfogl.integration.BitmapTexture;
import sfogl.integration.Material;
import sfogl.integration.Mesh;
import sfogl.integration.Model;
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

    private static HashMap<String, Node> nodes = new HashMap<String, Node>();

    public static HashMap<String, Node> getAllNodes(){
        return nodes;
    }

    public static Node generateNode(Context context, String shadername, int textureID, String objFilePath, String nodeID){

        if(nodes.containsKey(nodeID)){
            return nodes.get(nodeID);
        }
        else {
            ShadersKeeper.loadPipelineShaders(context, shadername);
            BitmapTexture texture = TexturesKeeper.generateTexture(context, textureID);
            Material mat = new Material(ShadersKeeper.getProgram(shadername));
            mat.getTextures().add(texture);
            Mesh mesh = MeshesKeeper.generateMesh(context, objFilePath);
            Model model = new Model();
            model.setRootGeometry(mesh);
            model.setMaterialComponent(mat);
            Node node = new Node();
            node.setModel(model);
            node.getRelativeTransform().setPosition(0, 0, 0);
            nodes.put(nodeID, node);
            return node;
        }

    }

    public static Node generateNode(Context context, String shadername, String textureColor, String objFilePath, String nodeID){

        if(nodes.containsKey(nodeID)){
            return nodes.get(nodeID);
        }
        else {
            ShadersKeeper.loadPipelineShaders(context, shadername);
            BitmapTexture texture = MonochromaticTexturesKeeper.generateTexture(context,textureColor);
            Material mat = new Material(ShadersKeeper.getProgram(shadername));
            mat.getTextures().add(texture);
            Mesh mesh = MeshesKeeper.generateMesh(context, objFilePath);
            Model model = new Model();
            model.setRootGeometry(mesh);
            model.setMaterialComponent(mat);
            Node node = new Node();
            node.setModel(model);
            node.getRelativeTransform().setPosition(0, 0, 0);
            nodes.put(nodeID, node);
            return node;
        }

    }

}
