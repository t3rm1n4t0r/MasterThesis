package com.example.alessandro.computergraphicsexample;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dagrada.marco.shariki.communicationpackets.GraphicsUpdatePacket;
import dagrada.marco.shariki.animations.AnimationPacket;
import dagrada.marco.shariki.animations.SwitchAnimation;
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
    private boolean isBlocked = false;

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

    Node father;

    Marble[][] marbless = new Marble[CUBE_ROWS][CUBE_COLS];

    private int[][] model;
    private int score;

    float t=0;

    private LinkedList<GraphicsAnimation> animations = new LinkedList<>();
    private LinkedList<GraphicsAnimation> blockingAnimations = new LinkedList<>();
    private LinkedList<GraphicsAnimation> toBeAdded = new LinkedList<>();
    private LinkedList<GraphicsAnimation> toBeAddedBlocking = new LinkedList<>();

    private long current=0;
    private long previous=0;
    private long frametime;


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

        for (int i=0; i<CUBE_ROWS; i++) {
            for (int j = 0; j < CUBE_COLS; j++) {


                current = marbless[i][j].getNode();
                position[0] = 0;
                position[1] = 0;
                position[2] = 0;
                position[3] = 1;


                current.getOpenGLMatrix(temp);

            /*
            Log.d("X", String.valueOf(temp[12]));

            Log.d("Y", String.valueOf(temp[13]));

            Log.d("Z", String.valueOf(temp[14]));

            /*

            Matrix.multiplyMM(matrix, 0,  mvp, 0, temp, 0);

            //Matrix.multiplyMM(matrix, 0, temp, 0, mvp, 0);

            Matrix.multiplyMV(result, 0, matrix, 0, position, 0);

*/
                Matrix.multiplyMV(result, 0, temp, 0, position, 0);
                Matrix.multiplyMV(result, 0, mvp, 0, result, 0);

                result[0] = result[0] / result[3];
                result[1] = result[1] / result[3];
                result[2] = result[2] / result[3];
                result[3] = result[3] / result[3];


                //printMatrix(matrix, "Node " + String.valueOf(i) + " Final Matrix", 4);
                //printVector(result, "Node " + String.valueOf(i)+ " Final Position");


                float cubecoordX = ((1 + result[0]) * this.width) / 2;

                float cubecoordY = ((1 - result[1]) * this.height) / 2;

                //Log.d("SCREEN POSITION", String.valueOf(cubecoordX) + ","+ String.valueOf(cubecoordY));


                float distance = (float) Math.sqrt((x - cubecoordX) * (x - cubecoordX) + (y - cubecoordY) * (y - cubecoordY));

                if (distance < RADIUS) {

                    Log.d("DISTANCE", String.valueOf(distance));
                    Log.d("NodeX", String.valueOf(cubecoordX));
                    Log.d("NodeY", String.valueOf(cubecoordY));
                    return marbless[i][j].getIndices();


                }
            }
        }
        throw new TouchedItemNotFoundException();

    }



    public GraphicsRenderer(Context context){
        this.context=context;
        model = new int[CUBE_ROWS][CUBE_COLS];

        colors.put(-1, "#FFFFFFFF");
        colors.put(0, "#FFFFFFFF");
        colors.put(1, "#FFFF0000");
        colors.put(2, "#FF00FF00");
        colors.put(3, "#FFFFFF00");
        colors.put(4, "#FF00FFFF");
        colors.put(5, "#FFFF00FF");

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

        float scale = 1.6f;


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
                marbless[i][j]= new Marble(node, pos);
            }
        }

        //Log.d("SIZE", String.valueOf(marbles.size()));
        Log.d("SCORE", String.valueOf(score));

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
        previous = current;
        current = SystemClock.elapsedRealtime();
        frametime = current-previous;

        //Log.d("TIME", String.valueOf(frametime));

        SFOGLSystemState.cleanupColorAndDepth(red, green, blue, 1);

        ShadersKeeper.getProgram(STANDARD_SHADER).setupProjection(mvp);

        updateAnimations();


        float scaling=0.032f;

        SFMatrix3f matrix3f=SFMatrix3f.getScale(scaling+t, scaling+t, scaling+t);

        father.getRelativeTransform().setMatrix(matrix3f);
        father.updateTree(new SFTransform3f());



        Node node;


        for (int i=0; i<CUBE_ROWS; i++){
            for (int j=0; j<CUBE_COLS; j++){
                node = marbless[i][j].getNode();
                node.draw();

            }
        }



    }


    public void updateAnimations(){

        if(toBeAddedBlocking.size() > 0){
            blockingAnimations.addAll(toBeAddedBlocking);
            toBeAddedBlocking = new LinkedList<>();

        }
        if(toBeAdded.size() > 0) {
            animations.addAll(toBeAdded);
            toBeAdded = new LinkedList<>();
        }


        List<GraphicsAnimation> toRemove = new LinkedList<>();

        for (Iterator<GraphicsAnimation> it = blockingAnimations.iterator(); it.hasNext();){
            GraphicsAnimation anim = it.next();

            anim.goOn();
            if(anim.isEnded()){

                toRemove.add(anim);
            }

        }
        try{
            blockingAnimations.removeAll(toRemove);

        }
        catch(ConcurrentModificationException e){

        }

        if(blockingAnimations.size() == 0){
            isBlocked = false;
        }

        toRemove = new LinkedList<>();

        for (Iterator<GraphicsAnimation> it = animations.iterator(); it.hasNext();){
            GraphicsAnimation anim = it.next();

            anim.goOn();
            if(anim.isEnded()){
                toRemove.add(anim);

            }

        }

        try{
            animations.removeAll(toRemove);

        }
        catch (ConcurrentModificationException e)
        {

        }
    }

/*

    public void printMatrix(float[] matrix, String name, int size){

        String matrice = new String();
        matrice+=name+":\n";


        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                matrice+=matrix[i*size+j];
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


    public void updateModel(Object obj){

        GraphicsUpdatePacket packet = (GraphicsUpdatePacket)obj;
        this.model = packet.getModel();
        this.score = packet.getScore();
    }

    @Override
    public void addAnimation(GraphicsAnimation animation) {
        if(animation.isblocking()){
            isBlocked = true;
            toBeAddedBlocking.add(animation);
        }
        else{
            toBeAdded.add(animation);
        }
    }

    @Override
    public void animate(Object object) {
        AnimationPacket packet = (AnimationPacket) object;
        addAnimation(new SwitchAnimation(marbless[packet.getX1()][packet.getY1()].getNode(), marbless[packet.getX2()][packet.getY2()].getNode(), packet.getEvent()));
        Log.d("PRIMA", String.valueOf(packet.getX1()) + " " + String.valueOf(packet.getY1()));
        Log.d("SECONDA", String.valueOf(packet.getX2()) + " " + String.valueOf(packet.getY2()));

    }

    public void testAnimation(){
        SwitchAnimation animation = new SwitchAnimation(marbless[2][2].getNode(), marbless[3][2].getNode(), null);
        addAnimation(animation);
    }

    public boolean isBlocked(){
        return isBlocked;
    }

    public Context getContext() {
        return context;
    }
}
