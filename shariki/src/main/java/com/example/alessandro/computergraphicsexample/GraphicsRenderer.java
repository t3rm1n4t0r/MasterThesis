package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.renderscript.Matrix4f;
import android.util.Log;
import android.view.WindowManager;

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
    private static final int RADIUS = 100;
    private static final int MAX_COLOR = 250;
    float width, height;

    float red=0;
    float blue = 0;
    float green = 0;

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

    private ArrayList<Integer> colormap = new ArrayList<>();

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
        //printMatrix(mvp, "Model View Projection");
    }

    public void resetPosition(){
        this.t=0;
        this.t2=0;
        this.TX =0;
        this.TY=0;
    }

    public void touch(float x, float y){

        float[] matrix = new float[16];
        float[] result = new float[4];
        float[] position = new float[4];
        float[] temp = new float[16];
        Node current;

        for (int i=0; i<father.getSonNodes().size(); i++){

            current = father.getSonNodes().get(i);
            position[0] = current.getX();
            position[1] = current.getY();
            position[2] = current.getZ();
            position[3] = 1;


            temp [0] = current.getRelativeTransform().getV()[0];
            temp [1] = current.getRelativeTransform().getV()[1];
            temp [2] = current.getRelativeTransform().getV()[2];
            temp [3] = 0;
            temp [4] = current.getRelativeTransform().getV()[3];;
            temp [5] = current.getRelativeTransform().getV()[4];
            temp [6] = current.getRelativeTransform().getV()[5];
            temp [7] = 0;
            temp [8] = current.getRelativeTransform().getV()[6];
            temp [9] = current.getRelativeTransform().getV()[7];
            temp [10] = current.getRelativeTransform().getV()[8];
            temp [11] = 0;
            temp [12] = 0;
            temp [13] = 0;
            temp [14] = 0;
            temp [15] = 1;


            Matrix.multiplyMM(matrix, 0,  mvp, 0, temp, 0);




            Matrix.multiplyMV(result, 0, matrix, 0, position, 0);


            result[0] = result[0] / result[3];
            result[1] = result[1] / result[3];
            result[2] = result[2] / result[3];
            result[3] = result[3] / result[3];





//            printMatrix(matrix, "Node " + String.valueOf(i) + " Final Matrix");
//            printVector(result, "Node " + String.valueOf(i)+ " Final Position");


            float cubecoordX = ((1 + result[0]) * this.width)/2;

            float cubecoordY = ((1 - result[1]) * this.height)/2;

//            Log.d("CubeX", String.valueOf(cubecoordX));
//            Log.d("CubeY", String.valueOf(cubecoordY));


            float distance = (float) Math.sqrt((x-cubecoordX)*(x-cubecoordX) + (y-cubecoordY)*(y-cubecoordY));
            //Log.d("DISTANCE", String.valueOf(distance));
            if(distance<RADIUS){
                red = (float)Color.red(colormap.get(i))/MAX_COLOR;
                green = (float)Color.green(colormap.get(i))/MAX_COLOR;
                blue = (float)Color.blue(colormap.get(i))/MAX_COLOR;

                Log.d("COLORE", String.valueOf(red)+" "+String.valueOf(green)+" "+String.valueOf(blue));
            }
        }

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
                intColor = Color.argb(MAX_COLOR, (MAX_COLOR/CUBE_ROWS) * i, (MAX_COLOR/CUBE_COLS) * j, 0);
                colormap.add(i*CUBE_COLS + j, intColor);
                String color = String.format("#%06X", 0xFFFFFFFF & intColor);
                String id = String.valueOf(i*CUBE_COLS + j);
                Node node = NodesKeeper.generateNode(context, "stdShader", color, "cube.obj", "cube"+id);
                node.getRelativeTransform().setPosition(10 * j - 20, 10 * i - 20, 0);
                father.getSonNodes().add(i*CUBE_COLS + j, node);
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

        Log.d("SCREEN RATIO", "Width/Height =" + ratio);
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(camera, 0, 0, 0, 4f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mvp, 0, projection, 0, camera, 0);

        this.width = width;
        this.height = height;






    }

    @Override
    public void onDrawFrame(GL10 gl) {

        SFOGLSystemState.cleanupColorAndDepth(red, green, blue, 1);

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

        //Draw the node

        //int[] viewport=new int[4];
        //GLES20.glGetIntegerv(GLES20.GL_VIEWPORT,viewport,0);
        //Log.e("Graphics View Size", Arrays.toString(viewport));
    }

    /*
    public void printMatrix(float[] matrix, String name){
        //Need to see MVP to correctly introduce ray picking
        String matrice = new String();
        matrice+=name+":\n";


        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                matrice+=matrix[i*4+j];
                matrice+=" ";
            }
            matrice+="\n";
        }

        Log.d(name, matrice);
    }
    public void printVector(float[] vector, String name){
        //Need to see MVP to correctly introduce ray picking
        String matrice = new String();
        matrice+=name+":\n";


        for (int i=0; i<4; i++){

                matrice+=vector[i];

                matrice+="\n";
            }


        Log.d(name, matrice);
    }
    */
}
