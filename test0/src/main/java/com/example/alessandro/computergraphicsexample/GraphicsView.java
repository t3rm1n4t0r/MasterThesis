package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import objLoader.ObjLoader;
import sfogl.integration.ArrayObject;
import sfogl.integration.BitmapTexture;
import sfogl.integration.Material;
import sfogl.integration.Mesh;
import sfogl.integration.Model;
import sfogl.integration.Node;
import sfogl.integration.ShadingProgram;
import sfogl2.SFOGLSystemState;
import sfogl2.SFOGLTextureModel;
import shadow.graphics.SFImageFormat;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;

/**
 * Created by Alessandro on 13/03/15.
 */
public class GraphicsView extends GLSurfaceView{

    private Context context;

    public GraphicsView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        this.context=context;
        super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        setRenderer(new GraphicsRenderer());
    }

    public class GraphicsRenderer implements Renderer{

        private Node node;

        private float t=0;

        private ShadingProgram program;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

            //Step 1 : load Shading effects
            ShadersKeeper.loadPipelineShaders(context);
            program= ShadersKeeper.getProgram(ShadersKeeper.STANDARD_TEXTURE_SHADER);

            //Step 2 : load Textures
            int textureModel=SFOGLTextureModel.generateTextureObjectModel(SFImageFormat.RGB,
                    GLES20.GL_REPEAT, GLES20.GL_REPEAT, GLES20.GL_LINEAR, GLES20.GL_LINEAR);
            BitmapTexture texture= BitmapTexture.loadBitmapTexture(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.paddedroomtexture01), textureModel);
            texture.init();

            //Step 3 : create a Material (materials combine shaders+textures+shading parameters)
            Material material=new Material(program);
            material.getTextures().add(texture);

            //Step 4: load a Geometry
            ArrayObject[] objects = ObjLoader.arrayObjectFromFile(context, "chitarra.obj");

            Mesh mesh=new Mesh(objects[0]);
            mesh.init();

            //Step 5: create a Model combining material+geometry
            Model model1=new Model();
            model1.setRootGeometry(mesh);
            model1.setMaterialComponent(material);

            //Step 6: create a Node, that is a reference system where you can place your Model
            node=new Node();
            node.setModel(model1);
            node.getRelativeTransform().setPosition(0, 0, 0);

            Node anotherNode=new Node();
            anotherNode.setModel(model1);
            anotherNode.getRelativeTransform().setPosition(1, 1, 0);
            anotherNode.getRelativeTransform().setMatrix(SFMatrix3f.getScale(0.3f,0.2f,0.1f));
            node.getSonNodes().add(anotherNode);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

            SFOGLSystemState.cleanupColorAndDepth(0, 0, 0, 1);
            //setup the View Projection
            float[] projection={
                    1,0,0,0,
                    0,1,0,0,
                    0,0,1,0,
                    0,0,0,1,
            };
            program.setupProjection(projection);


            //Change the Node transform
            t+=0.01f;
            float rotation=0.2f+t;
            float scaling=0.03f;
            SFMatrix3f matrix3f=SFMatrix3f.getScale(scaling, scaling, scaling);

            matrix3f=matrix3f.MultMatrix(SFMatrix3f.getRotationY(rotation));

            matrix3f=matrix3f.MultMatrix((SFMatrix3f.getRotationZ((float)Math.PI)));
            matrix3f=matrix3f.MultMatrix((SFMatrix3f.getRotationX(1.57079633f)));
            matrix3f=matrix3f.MultMatrix((SFMatrix3f.getRotationY(1.57079633f)));


            node.getRelativeTransform().setMatrix(matrix3f);
            node.updateTree(new SFTransform3f());

            //Draw the node

            node.draw();

            //int[] viewport=new int[4];
            //GLES20.glGetIntegerv(GLES20.GL_VIEWPORT,viewport,0);
            //Log.e("Graphics View Size", Arrays.toString(viewport));
        }
    }
}
