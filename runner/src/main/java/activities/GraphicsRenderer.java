package activities;

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

import dagrada.marco.runner.NodeBuffer;
import dagrada.marco.runner.Updatable;
import dagrada.marco.runner.communicationpackets.ModelUpdatePacket;
import dagrada.marco.runner.exceptions.TouchedItemNotFoundException;
import dagrada.marco.runner.interactables.Guitar;
import dagrada.marco.runner.interactables.MusicalNote;
import thesis.Graphics.GraphicsEngine;
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

    private float guitarfloating=0;
    private float floatingincrement=0.002f;

    private NodeBuffer nodeBuffer;

    private HashMap<Character, String> numbersTextures = new HashMap<>();



    public int[] detectTouchedItem(float x, float y) throws TouchedItemNotFoundException {
/*
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
*/
        return null;
    }



    public GraphicsRenderer(Context context){
        this.context=context;
        toBeDrawn = new LinkedList<>();

        colors.put(-1, "#FF000000");
        colors.put(0, "#FFFFFFFF");
        colors.put(1, "#FFFF0000");
        colors.put(2, "#FFFFFF00");
        colors.put(3, "#FF00FFFF");
        colors.put(4, "#FFFF00FF");

        nodeBuffer = new NodeBuffer();

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

        nodeBuffer.clearList();

        drawItems();

        father.getSonNodes().addAll(nodeBuffer.getNodes());
        nodeBuffer.clearList();

        drawScore(score);

        scorefather.getSonNodes().addAll(nodeBuffer.getNodes());

        nodeBuffer.clearList();


    }

    public void drawItems(){


        Iterator<Updatable> iterator = toBeDrawn.iterator();

        //Log.d("To Be Drawn Size", String.valueOf(toBeDrawn.size()));

        while (iterator.hasNext()) {

            Updatable current = iterator.next();

            if(current instanceof MusicalNote){

                MusicalNote musicalNote = (MusicalNote) current;

                Node note = NodesKeeper.generateNode(context, "stdShader", colors.get(musicalNote.getNote()), "musicnote.obj");
                //note.getRelativeTransform().setPosition(0, 0.20f, 0);


                note.getRelativeTransform().setPosition(musicalNote.getX(), musicalNote.getY(), musicalNote.getZ());

                note.getRelativeTransform().setMatrix(SFMatrix3f.getScale(2.5f, 2.5f, 2.5f));

                nodeBuffer.addNode(note);

            }
            if(current instanceof Guitar){

                Guitar g = (Guitar) current;

                Node guitar = NodesKeeper.generateNode(context, "stdShader", "#FF00FF00", "guitar2.obj");
                //guitar.getRelativeTransform().setPosition(-1.5f, 0.20f, 0);
                guitar.getRelativeTransform().setPosition(g.getX(), g.getY(), g.getZ());
                SFMatrix3f matrix = SFMatrix3f.getScale(0.06f, 0.06f, 0.06f);
                guitar.getRelativeTransform().setMatrix(matrix.MultMatrix(SFMatrix3f.getRotationX(guitarfloating)));
                nodeBuffer.addNode(guitar);
            }

        }



    }

    public void drawBackground(){

        backgroundfather = new Node();



        Node sheet = NodesKeeper.generateNode(context, "stdShader", "#FFFFFDE8", "sheet.obj");
        sheet.getRelativeTransform().setPosition(0, 0, 0);
        sheet.getRelativeTransform().setMatrix(SFMatrix3f.getScale(1.8f, 1.0f, 1.0f));
        sheet.updateTree(new SFTransform3f());
        backgroundfather.getSonNodes().add(sheet);

        float LINE_LENGTH = 0.235f;

        Node line = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line.getRelativeTransform().setPosition(0, 0f, 0f);
        line.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line);

        Node line2 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line2.getRelativeTransform().setPosition(0, 0f, -0.035f);
        line2.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line2);

        Node line3 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line3.getRelativeTransform().setPosition(0, 0f, -0.07f);
        line3.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line3);

        Node line4 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line4.getRelativeTransform().setPosition(0, 0f, 0.035f);
        line4.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line4);

        Node line5 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line5.getRelativeTransform().setPosition(0, 0f, 0.07f);
        line5.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line5);

        Node line6 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line6.getRelativeTransform().setPosition(0, 0f, 0.60f);
        line6.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line6);


        Node line7 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line7.getRelativeTransform().setPosition(0, 0f, 0.565f);
        line7.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line7);

        Node line8 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line8.getRelativeTransform().setPosition(0, 0f, 0.53f);
        line8.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line8);

        Node line9 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line9.getRelativeTransform().setPosition(0, 0f, 0.635f);
        line9.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line9);

        Node line10 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line10.getRelativeTransform().setPosition(0, 0f, 0.67f);
        line10.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line10);


        Node line11 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line11.getRelativeTransform().setPosition(0, 0f, -0.60f);
        line11.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line11);

        Node line12 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line12.getRelativeTransform().setPosition(0, 0f, -0.635f);
        line12.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line12);

        Node line13 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line13.getRelativeTransform().setPosition(0, 0f, -0.67f);
        line13.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line13);

        Node line14 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line14.getRelativeTransform().setPosition(0, 0f, -0.565f);
        line14.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line14);

        Node line15 = NodesKeeper.generateNode(context, "stdShader", "#EF686868", "line.obj");
        line15.getRelativeTransform().setPosition(0, 0f, -0.53f);
        line15.getRelativeTransform().setMatrix(SFMatrix3f.getScale(LINE_LENGTH, 0.10f, 0.10f));
        backgroundfather.getSonNodes().add(line15);



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
        plane.getRelativeTransform().setPosition(0,0,0);
        plane.getRelativeTransform().setMatrix(SFMatrix3f.getScale(0.15f, 1f, 1f));
        plane.updateTree(new SFTransform3f());

        nodeBuffer.addNode(plane);

        for (int i=0; i<scoreString.length(); i++) {

            Node number = NodesKeeper.generateNode(context, "stdShader", "#FF000000", numbersTextures.get(scoreString.charAt(i)));
            number.getRelativeTransform().setPosition(0, 0, (i * digit_distance)-beginning);
            SFMatrix3f matrix = SFMatrix3f.getScale(number_scale, number_scale, number_scale);

            number.getRelativeTransform().setMatrix(matrix.MultMatrix(SFMatrix3f.getRotationX(3.14f)));
            number.updateTree(new SFTransform3f());
            nodeBuffer.addNode(number);

        }


    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {


        Guitar g = new Guitar(-1.5f, 0.30f, 0, null);


        MusicalNote musicalNote = new MusicalNote(1, 0,0.20f,0, 0);
        MusicalNote musicalNote2 = new MusicalNote(2, -0.53f,0.20f,0, 0);
        MusicalNote musicalNote3 = new MusicalNote(3, 0.53f,0.20f,0, 0);
        MusicalNote musicalNote4 = new MusicalNote(4, 1.06f,0.20f,0, 0);


        toBeDrawn = new LinkedList<>();

        toBeDrawn.add(g);
        toBeDrawn.add(musicalNote);
        toBeDrawn.add(musicalNote2);
        toBeDrawn.add(musicalNote3);
        toBeDrawn.add(musicalNote4);


        drawBackground();


        update();

        toBeDrawn = new LinkedList<>();

        update();

        drawScore(1234567890);
        drawScore(0);



    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method

        //Log.d("SCREEN RATIO", "Width/Height =" + ratio);
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 3, 9);


        Matrix.setLookAtM(camera, 0, -4f, 3.7f, 0, 0f, 0f, 0f, 1.0f, 0.0f, 0.0f);


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

        guitarfloating+=floatingincrement;
        if(guitarfloating >= ((float)Math.PI/8) || guitarfloating <= -((float)Math.PI/8))
            floatingincrement*=-1;

        float scaling=1f;

        SFMatrix3f matrix3f=SFMatrix3f.getScale(scaling, scaling, scaling);
        backgroundfather.getRelativeTransform().setMatrix(matrix3f);
        backgroundfather.updateTree(new SFTransform3f());
        father.getRelativeTransform().setMatrix(matrix3f);
        father.updateTree(new SFTransform3f());

        for (Node  node : backgroundfather.getSonNodes()) {
            node.draw();
        }



        for (Node  node : father.getSonNodes()) {
            node.draw();
        }

        scorefather.getRelativeTransform().setPosition(-2f, 0.3f, 0);
        scorefather.updateTree(new SFTransform3f());

        for (Node  node : scorefather.getSonNodes()) {
            node.draw();
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

        LinkedList<Updatable> items = ((ModelUpdatePacket) obj).getItems();
        this.toBeDrawn = new LinkedList<>();
        //this.toBeDrawn.addAll(items);
        this.toBeDrawn = items;
        this.score = ((ModelUpdatePacket) obj).getScore();


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
