package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import objLoader.ObjLoader;
import sfogl.integration.ArrayObject;
import sfogl.integration.BitmapTexture;
import sfogl.integration.Material;
import sfogl.integration.Mesh;
import sfogl.integration.Model;
import sfogl.integration.Node;
import sfogl.integration.Shaders;
import sfogl.integration.ShadingProgram;
import sfogl2.SFOGLSystemState;
import sfogl2.SFOGLTextureModel;
import shadow.graphics.SFImageFormat;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import thesis.NodesKeeper;
import thesis.ShadersKeeper;
import thesis.TexturesKeeper;

/**
 * Created by Alessandro on 13/03/15.
 */
public class GraphicsView extends GLSurfaceView{

    private Context context;
    private static final String STANDARD_SHADER = "stdShader";

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

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

           node = NodesKeeper.generateNode(context, "stdShader", R.drawable.paddedroomtexture01, "cubo.obj", "cubo1");
           Node anothernode = NodesKeeper.generateNode(context, "stdShader", R.drawable.paddedgreen, "cubo.obj", "cubo2");
           Node anotheranothernode = NodesKeeper.generateNode(context, "stdShader", R.drawable.paddedroomtexture01, "cubo.obj", "cubo3");


            node.getRelativeTransform().setPosition(0, 0, 0);

            node.getRelativeTransform().setMatrix(SFMatrix3f.getScale(0.3f, 0.2f, 0.1f));


            anothernode.getRelativeTransform().setPosition(1, -300, 0);
            //anothernode.getRelativeTransform().setMatrix(SFMatrix3f.getScale(0.3f, 0.2f, 0.1f));
            node.getSonNodes().add(anothernode);


            anotheranothernode.getRelativeTransform().setPosition(0, 300f, 0);

            node.getSonNodes().add(anotheranothernode);
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
            ShadersKeeper.getProgram(STANDARD_SHADER).setupProjection(projection);


            //Change the Node transform
            t+=0.01f;
            float rotation=0.2f+t;
            float scaling=0.01f;

            SFMatrix3f matrix3f=SFMatrix3f.getScale(scaling, scaling, scaling);
            SFMatrix3f spin = SFMatrix3f.getIdentity();





            matrix3f=matrix3f.MultMatrix(SFMatrix3f.getRotationY(rotation));

            matrix3f=matrix3f.MultMatrix((SFMatrix3f.getRotationZ((float) Math.PI)));
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
