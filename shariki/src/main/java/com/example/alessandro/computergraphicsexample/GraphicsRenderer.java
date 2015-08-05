package com.example.alessandro.computergraphicsexample;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import thesis.Graphics.GraphicsEngine;
import dagrada.marco.shariki.Marble;
import dagrada.marco.shariki.exceptions.TouchedItemNotFoundException;
import sfogl.integration.Node;
import sfogl2.SFOGLSystemState;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import thesis.Graphics.NodesKeeper;
import thesis.Graphics.ShadersKeeper;
import thesis.Graphics.GraphicsAnimation;

/**
 * Created by Marco on 26/05/2015.
 */
public class GraphicsRenderer implements GLSurfaceView.Renderer, GraphicsEngine {


    private static final String STANDARD_SHADER = "stdShader";
    private static final int CUBE_ROWS = 5;
    private static final int CUBE_COLS = 5;
    private static final int RADIUS = 60;
    private static final int MAX_COLOR = 250;
    float width, height;

    HashMap<Integer, String> colors = new HashMap<>();

    float red=0;
    float blue = 0;
    float green = 0;

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
    private HashMap<Integer, Marble> marbles = new HashMap<>();
    private int[][] model;

    float t=0;


    public int[] detectTouchedItem(float x, float y) throws TouchedItemNotFoundException{

        //Log.e("TOUCHED POSITION", String.valueOf(x)+","+String.valueOf(y));

        float[] matrix = new float[16];
        float[] result = new float[4];
        float[] position = new float[4];
        float[] temp = new float[16];
        Node current;
        int[] indices = new int[2];

        y = y-getSoftbuttonsbarHeight() + getStatusBarHeight();
        //Log.d("WIDTH", String.valueOf(width));
       // Log.d("ACTUAL", String.valueOf(getActualWidth()));

        for (int i=0; i<marbles.size(); i++){

            current = marbles.get(i).getNode();
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




            float distance = (float) Math.sqrt((x-cubecoordX)*(x-cubecoordX) + (y-cubecoordY)*(y-cubecoordY));

            if(distance<RADIUS){

                Log.d("DISTANCE", String.valueOf(distance));
                Log.d("NodeX", String.valueOf(cubecoordX));
                Log.d("NodeY", String.valueOf(cubecoordY));
                return marbles.get(i).getIndices();


            }
        }
        throw new TouchedItemNotFoundException();

    }



    public GraphicsRenderer(Context context){
        this.context=context;
        model = new int[CUBE_ROWS][CUBE_COLS];

        colors.put(0, "#FFFFFFFF");
        colors.put(1, "#FFFF0000");
        colors.put(2, "#FF00FF00");
        colors.put(-1, "#FFFFFFFF");

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getActualWidth(){
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    private int getSoftbuttonsbarHeight() {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public void update(){

        float[] mat = new float[CUBE_COLS * CUBE_ROWS];
        for (int i=0; i<CUBE_ROWS; i++){
            for(int j=0; j<CUBE_COLS; j++) {
                mat[i*CUBE_COLS + j] = model[i][j];
            }
        }

        //printMatrix(mat, "marbles");

        father = NodesKeeper.generateNode(context, "stdShader", R.drawable.paddedroomtexture01, "sphere.obj");

        float scale = 1.5f;


        for (int i=0; i<CUBE_COLS; i++){
            for(int j=0; j<CUBE_ROWS; j++){
                //Log.d("MARBLE", String.valueOf(handler.getMarbles()[i][j]));
                String id = String.valueOf(i*CUBE_COLS + j);
                //Log.d("COLOR", colors.get(model[i][j]));
                Node node = NodesKeeper.generateNode(context, "stdShader", colors.get(model[i][j]), "sphere.obj");
                node.getRelativeTransform().setPosition(10 * i - 20, 10 * j - 20, 0);
                node.getRelativeTransform().setMatrix(SFMatrix3f.getScale(scale, scale, scale));
                node.updateTree(new SFTransform3f());
                father.getSonNodes().add(i * CUBE_COLS + j, node);
                int[] pos = new int[2];
                pos[0] = i;
                pos[1] = j;
                marbles.put(i*CUBE_COLS + j, new Marble(node, pos));
            }
        }

        //Log.d("SIZE", String.valueOf(marbles.size()));

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        update();



    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method

        //Log.d("SCREEN RATIO", "Width/Height =" + ratio);
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

        float scaling=0.04f;

        SFMatrix3f matrix3f=SFMatrix3f.getScale(scaling+t, scaling+t, scaling+t);

        father.getRelativeTransform().setMatrix(matrix3f);
        father.updateTree(new SFTransform3f());

        Node node;

        for (int i=0; i<marbles.size(); i++){
            node = marbles.get(i).getNode();
            node.draw();
        }

    }


    public void printMatrix(float[] matrix, String name){

        String matrice = new String();
        matrice+=name+":\n";


        for (int i=0; i<CUBE_ROWS; i++){
            for (int j=0; j<CUBE_COLS; j++){
                matrice+=matrix[i*CUBE_COLS+j];
                matrice+=" ";
            }
            matrice+="\n";
        }

        Log.d(name, matrice);
    }

    /*
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

    public void updateModel(Object obj){
        this.model = (int[][]) obj;
    }

    @Override
    public void animate(GraphicsAnimation animation) {

    }
}
