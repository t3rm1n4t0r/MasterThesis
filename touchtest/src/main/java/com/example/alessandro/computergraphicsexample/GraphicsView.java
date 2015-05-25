package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sfogl.integration.Node;
import sfogl2.SFOGLSystemState;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import thesis.Graphics.NodesKeeper;
import thesis.Graphics.ShadersKeeper;
import thesis.touch.TouchActivity;

/**
 * Created by Alessandro on 13/03/15.
 */
public class GraphicsView extends GLSurfaceView implements TouchActivity{

    private Context context;
    private static final String STANDARD_SHADER = "stdShader";
    private static final int CUBE_ROWS = 5;
    private static final int CUBE_COLS = 5;
    private float[] projection={
            1,0,0,0,
            0,1,0,0,
            0,0,1,0,
            0,0,0,1,
    };

    private GraphicsRenderer renderer;

    public GraphicsView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        this.context=context;
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        renderer = new GraphicsRenderer();
        setRenderer(renderer);
    }



    @Override
    public void onRightSwipe(float distance) {
        Log.e("TOUCH", "RIGHT SWIPE");
        renderer.incrementTX(-0.01f);
    }

    @Override
    public void onLeftSwipe(float distance) {
        Log.e("TOUCH", "LEFT SWIPE");
        renderer.incrementTX(0.01f);
    }

    @Override
    public void onUpSwipe(float distance) {
        Log.e("TOUCH", "UP SWIPE");
        renderer.incrementTY(-0.01f);
    }

    @Override
    public void onDownSwipe(float distance) {
        Log.e("TOUCH", "DOWN SWIPE");
        renderer.incrementTY(0.01f);
    }

    @Override
    public void onDoubleTap() {
        Log.e("TOUCH", "DOUBLE TAP");
        renderer.stopMovement();
    }

    @Override
    public void onLongPress() {
        renderer.resetPosition();
    }

    public class GraphicsRenderer implements Renderer{

        private ArrayList<Node> nodes = new ArrayList<>();
        Node father;

        private float t=0;
        private float t2=0;
        private float TX=0;
        private float TY=0;

        public void incrementTX(float t){
            this.TX +=t;
        }
        public void incrementTY(float t){
            this.TY +=t;
        }

        public void stopMovement(){
            this.TX =0;
            this.TY =0;
        }

        public void resetPosition(){
            this.t=0;
            this.t2=0;
            this.TX =0;
            this.TY=0;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

            int intColor;

            father = NodesKeeper.generateNode(context, "stdShader", R.drawable.paddedroomtexture01, "cube.obj", "father");

            for (int i=0; i<CUBE_COLS; i++){
                for(int j=0; j<CUBE_ROWS; j++){
                    intColor = Color.argb(255, 50*i, 50*j, 0);
                    String color = String.format("#%06X", 0xFFFFFFFF & intColor);
                    String id = String.valueOf(i*CUBE_COLS + j);
                    Node node = NodesKeeper.generateNode(context, "stdShader", color, "cube.obj", "cube"+id);
                    node.getRelativeTransform().setPosition(10*j-20, 10*i-20, 0);
                    father.getSonNodes().add(node);
                }
            }

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            float r = (float)width/height;
            if(r<1) {
                projection[0] = 1;

                projection[5] = r;
            }
            else if(r>1){
                projection[0] = 1/r;

                projection[5] = 1;
            }


        }

        @Override
        public void onDrawFrame(GL10 gl) {

            SFOGLSystemState.cleanupColorAndDepth(0, 0, 0, 1);
            //setup the View Projection

            ShadersKeeper.getProgram(STANDARD_SHADER).setupProjection(projection);

            //Change the Node transform
            t+=TX;
            t2+=TY;
            float rotation=0f+t;
            float rotation2=0f+t2;
            //float rotation=0;
            float scaling=0.04f;

            SFMatrix3f matrix3f=SFMatrix3f.getScale(scaling, scaling, scaling);


            matrix3f=matrix3f.MultMatrix(SFMatrix3f.getRotationY(rotation));
            matrix3f=matrix3f.MultMatrix(SFMatrix3f.getRotationX(rotation2));

            //matrix3f=matrix3f.MultMatrix((SFMatrix3f.getRotationZ((float) Math.PI)));
            //matrix3f = matrix3f.MultMatrix((SFMatrix3f.getRotationX((float)Math.PI)));
            //matrix3f= matrix3f.MultMatrix((SFMatrix3f.getRotationY((float)Math.PI)));



            father.getRelativeTransform().setMatrix(matrix3f);
            father.updateTree(new SFTransform3f());
            //matrix3f=SFMatrix3f.getIdentity().MultMatrix(SFMatrix3f.getRotationY(rotation));


            Node node;

            for (int i=0; i<father.getSonNodes().size(); i++){
                node = father.getSonNodes().get(i);
                node.draw();
            }


            //Draw the node

            //int[] viewport=new int[4];
            //GLES20.glGetIntegerv(GLES20.GL_VIEWPORT,viewport,0);
            //Log.e("Graphics View Size", Arrays.toString(viewport));
        }
    }
}
