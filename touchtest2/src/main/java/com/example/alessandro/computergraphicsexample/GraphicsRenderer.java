package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.renderscript.Matrix4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sfogl.integration.Node;
import sfogl2.SFOGLSystemState;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import thesis.Graphics.NodesKeeper;
import thesis.Graphics.ShadersKeeper;

/**
 * Created by Marco on 26/05/2015.
 */
public class GraphicsRenderer implements GLSurfaceView.Renderer {


    private static final String STANDARD_SHADER = "stdShader";
    private static final int CUBE_ROWS = 5;
    private static final int CUBE_COLS = 5;

    private boolean screenshot=false;

    Context context;

    private float[] projection={
            1,0,0,0,
            0,1,0,0,
            0,0,1,0,
            0,0,0,1,
    };

    private float[] camera={
            1,0,0,0,
            0,1,0,0,
            0,0,1,0,
            0,0,0,1,
    };

    private float[] mvp = new float[16];


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

    public void touch(float x, float y){

    }



    public GraphicsRenderer(Context context){
        this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        int intColor;

        father = NodesKeeper.generateNode(context, "stdShader", R.drawable.paddedroomtexture01, "cube.obj", "father");

        for (int i=0; i<CUBE_COLS; i++){
            for(int j=0; j<CUBE_ROWS; j++){
                intColor = Color.argb(255, 50 * i, 50 * j, 0);
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

        GLES20.glViewport(0, 0, width, height);

        /*float r = (float)width/height;
        if(r<1) {
            projection[0] = 1;

            projection[5] = r;
        }
        else if(r>1){
            projection[0] = 1/r;

            projection[5] = 1;
        }
*/
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 3, 7);




    }

    @Override
    public void onDrawFrame(GL10 gl) {

        SFOGLSystemState.cleanupColorAndDepth(0, 0, 0, 1);

        Matrix.setLookAtM(camera, 0, 0, 2, 4f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mvp, 0, projection, 0, camera, 0);

        ShadersKeeper.getProgram(STANDARD_SHADER).setupProjection(mvp);

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


        if (screenshot) {
            int screenshotSize = this.width * this.height;
            ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
            bb.order(ByteOrder.nativeOrder());
            gl.glReadPixels(0, 0, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);
            int pixelsBuffer[] = new int[screenshotSize];
            bb.asIntBuffer().get(pixelsBuffer);
            bb = null;

            for (int i = 0; i < screenshotSize; ++i) {
                // The alpha and green channels' positions are preserved while the red and blue are swapped
                pixelsBuffer[i] = ((pixelsBuffer[i] & 0xff00ff00)) | ((pixelsBuffer[i] & 0x000000ff) << 16) | ((pixelsBuffer[i] & 0x00ff0000) >> 16);
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixelsBuffer, screenshotSize-width, -width, 0, 0, width, height);
            this.screenshot = false;
        }
        //Draw the node

        //int[] viewport=new int[4];
        //GLES20.glGetIntegerv(GLES20.GL_VIEWPORT,viewport,0);
        //Log.e("Graphics View Size", Arrays.toString(viewport));
    }
}
