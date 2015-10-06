package activities.renderers;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
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

import dagrada.marco.aquarium.exceptions.TouchedItemNotFoundException;
import sfogl.integration.Node;
import sfogl2.SFOGLSystemState;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import shadow.math.SFVertex3f;
import thesis.Graphics.GameRenderer;
import thesis.Graphics.GraphicsAnimation;
import thesis.Graphics.NodeCollector;
import thesis.Graphics.NodesKeeper;
import thesis.Graphics.ShadersKeeper;
import thesis.utils.Updatable;

/**
 * Created by Marco on 05/10/2015.
 */

public class PanoramicRenderer extends GameRenderer{
    private static final String STANDARD_SHADER = "stdShader";
    private static final int CUBE_ROWS = 5;
    private static final int CUBE_COLS = 5;
    private static final int RADIUS = 70;
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
    Node backgroundfather;
    Node scorefather;

    private LinkedList<Updatable> toBeDrawn = new LinkedList<>();
    private int score;

    float t=0;

    private LinkedList<GraphicsAnimation> animations = new LinkedList<>();
    private LinkedList<GraphicsAnimation> blockingAnimations = new LinkedList<>();
    private LinkedList<GraphicsAnimation> toBeAdded = new LinkedList<>();
    private LinkedList<GraphicsAnimation> toBeAddedBlocking = new LinkedList<>();

    private long current=0;
    private long previous=0;
    private long frametime;

    private NodeCollector nodeCollector;

    private Node[][] tiles = new Node[5][5];

    private HashMap<Character, String> numbersTextures = new HashMap<>();

    public PanoramicRenderer(Context context){
        this.context=context;
        toBeDrawn = new LinkedList<>();

        colors.put(-1, "#FF000000");
        colors.put(0, "#FFFFFFFF");
        colors.put(1, "#FFFF0000");
        colors.put(2, "#FFFFFF00");
        colors.put(3, "#FF00FFFF");
        colors.put(4, "#FFFF00FF");

        nodeCollector = new NodeCollector();

        numbersTextures.put('0', "numbers_0.obj");
        numbersTextures.put('1', "numbers_1.obj");
        numbersTextures.put('2', "numbers_2.obj");
        numbersTextures.put('3', "numbers_3.obj");
        numbersTextures.put('4', "numbers_4.obj");
        numbersTextures.put('5', "numbers_5.obj");
        numbersTextures.put('6', "numbers_6.obj");
        numbersTextures.put('7', "numbers_7.obj");
        numbersTextures.put('8', "numbers_8.obj");
        numbersTextures.put('9', "numbers_9.obj");


    }

    public int[] detectTouchedItem(float x, float y) throws TouchedItemNotFoundException {

        //Log.e("TOUCHED POSITION", String.valueOf(x)+","+String.valueOf(y));

        float[] matrix = new float[16];
        float[] result = new float[4];
        float[] position = new float[4];
        float[] temp = new float[16];
        Node current;
        int[] indices = new int[2];

        if(context.getResources().getConfiguration().orientation == context.getResources().getConfiguration().ORIENTATION_PORTRAIT)
            y = y-getSoftbuttonsbarHeight() + getStatusBarHeight();
        if(context.getResources().getConfiguration().orientation == context.getResources().getConfiguration().ORIENTATION_LANDSCAPE)
            y = y-getStatusBarHeight();

        //Log.d("WIDTH", String.valueOf(width));
        // Log.d("ACTUAL", String.valueOf(getActualWidth()));



        Iterator iterator = backgroundfather.getSonNodes().iterator();



        for (int i=0; i<CUBE_ROWS; i++) {
            for (int j = 0; j < CUBE_COLS; j++) {




                current = tiles[i][j];
                position[0] = 0;
                position[1] = 0;
                position[2] = 0;
                position[3] = 1;


                current.getOpenGLMatrix(temp);
/*

            Log.d("X", String.valueOf(temp[12]));

            Log.d("Y", String.valueOf(temp[13]));

            Log.d("Z", String.valueOf(temp[14]));


*/
                Matrix.multiplyMM(matrix, 0, mvp, 0, temp, 0);

                //Matrix.multiplyMM(matrix, 0, temp, 0, mvp, 0);

                Matrix.multiplyMV(result, 0, matrix, 0, position, 0);


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
/*
                    Log.d("DISTANCE", String.valueOf(distance));
                    Log.d("NodeX", String.valueOf(cubecoordX));
                    Log.d("NodeY", String.valueOf(cubecoordY));
                    */
                    int[] res = new int[2];
                    res[0] = i;
                    res[1] = j;
                    return res;


                }
            }
        }
        throw new TouchedItemNotFoundException();


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

        father = new Node();

        nodeCollector.clearList();

        drawItems();

        father.getSonNodes().addAll(nodeCollector.getNodes());
        nodeCollector.clearList();

        drawScore(score);

        scorefather.getSonNodes().addAll(nodeCollector.getNodes());

        nodeCollector.clearList();


    }

    public void drawItems(){


        Iterator<Updatable> iterator = toBeDrawn.iterator();

        //Log.d("To Be Drawn Size", String.valueOf(toBeDrawn.size()));

        while (iterator.hasNext()) {



        }



    }

    public void drawBackground(){

        backgroundfather = new Node();

        float TILE_SCALE = 1f;
        float TILE_DISTANCE = 2f;

        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                Node tile = NodesKeeper.generateNode(context, "stdShader", "#FFC2B280", "tile_0.obj");
                tile.getRelativeTransform().setPosition(i*TILE_DISTANCE - 2f*TILE_DISTANCE, 0, j*TILE_DISTANCE-2f*TILE_DISTANCE);
                tile.getRelativeTransform().setMatrix(SFMatrix3f.getScale(TILE_SCALE, TILE_SCALE, TILE_SCALE));
                tile.updateTree(new SFTransform3f());
                tiles[i][j] = tile;
                backgroundfather.getSonNodes().add(tile);
            }
        }

        float STONE_SCALE = 0.65f;

        Node stone = NodesKeeper.generateNode(context, "stdShader", "#FF727B84", "stone.obj");
        SFVertex3f position = new SFVertex3f();
        tiles[0][0].getRelativeTransform().getPosition(position);
        stone.getRelativeTransform().setPosition(position);
        stone.getRelativeTransform().setMatrix(SFMatrix3f.getScale(STONE_SCALE, STONE_SCALE, STONE_SCALE));
        stone.updateTree(new SFTransform3f());
        backgroundfather.getSonNodes().add(stone);


        float PLANT_SCALE = 6f;


        Node plant = NodesKeeper.generateNode(context, "stdShader", "#FF76A912", "plant.obj");
        SFVertex3f position2 = new SFVertex3f();
        tiles[2][2].getRelativeTransform().getPosition(position2);
        plant.getRelativeTransform().setPosition(position2);
        plant.getRelativeTransform().setMatrix(SFMatrix3f.getScale(PLANT_SCALE, PLANT_SCALE, PLANT_SCALE));
        plant.updateTree(new SFTransform3f());
        backgroundfather.getSonNodes().add(plant);

        float AMPHORA_SCALE = 3f;

        Node amphora = NodesKeeper.generateNode(context, "stdShader", "#FFE29F5B", "vase.obj");
        SFVertex3f position3 = new SFVertex3f();
        tiles[3][4].getRelativeTransform().getPosition(position3);
        amphora.getRelativeTransform().setPosition(position3);
        SFMatrix3f matrix3f = SFMatrix3f.getScale(AMPHORA_SCALE, AMPHORA_SCALE, AMPHORA_SCALE);
        matrix3f.MultMatrix(SFMatrix3f.getRotationX(1.2f));
        amphora.getRelativeTransform().setMatrix(matrix3f);
        amphora.updateTransform(new SFTransform3f());
        backgroundfather.getSonNodes().add(amphora);



    }

    public void drawScore(int score){

        float digit_distance = 0.12f;
        float number_scale = 0.045f;

        scorefather = new Node();

        String scoreString = String.valueOf(score);

        float beginning = ((scoreString.length()-1)/2)*digit_distance;
        if(scoreString.length()%2 == 0)
            beginning+=0.5f*digit_distance;

        //Log.d("STRINGA", String.valueOf(scoreString.length()));

        Node plane = NodesKeeper.generateNode(context, "stdShader", "#FFFFFFFF", "1x1plane.obj");
        plane.getRelativeTransform().setPosition(0, 0, 0);
        plane.getRelativeTransform().setMatrix(SFMatrix3f.getScale(0.15f, 1f, 1f));
        plane.updateTree(new SFTransform3f());

        nodeCollector.addNode(plane);

        for (int i=0; i<scoreString.length(); i++) {

            Node number = NodesKeeper.generateNode(context, "stdShader", "#FF000000", numbersTextures.get(scoreString.charAt(i)));
            number.getRelativeTransform().setPosition(0, 0, (i * digit_distance)-beginning);
            SFMatrix3f matrix = SFMatrix3f.getScale(number_scale, number_scale, number_scale);

            number.getRelativeTransform().setMatrix(matrix.MultMatrix(SFMatrix3f.getRotationX(3.14f)));
            number.updateTree(new SFTransform3f());
            nodeCollector.addNode(number);

        }


    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {





        toBeDrawn = new LinkedList<>();




        drawBackground();







    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method

        //Log.d("SCREEN RATIO", "Width/Height =" + ratio);
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 3, 7);


        Matrix.setLookAtM(camera, 0, -3f, 3f, 0f, 0f, 0f, 0f, 1f, 0.0f, 0.0f);

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



        float scaling=0.25f;

        SFMatrix3f matrix3f=SFMatrix3f.getScale(scaling, scaling, scaling);
        backgroundfather.getRelativeTransform().setMatrix(matrix3f);
        backgroundfather.updateTree(new SFTransform3f());


        for (Node  node : backgroundfather.getSonNodes()) {
            node.draw();
        }


        /*
        father.getRelativeTransform().setMatrix(matrix3f);
        father.updateTree(new SFTransform3f());

        for (Node  node : father.getSonNodes()) {
            node.draw();
        }

        scorefather.getRelativeTransform().setPosition(-2f, 0.3f, 0);
        scorefather.updateTree(new SFTransform3f());

        for (Node  node : scorefather.getSonNodes()) {
            node.draw();
        }


*/

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


    }

    public boolean isBlocked(){
        return isBlocked;
    }

    public Context getContext() {
        return context;
    }
}


