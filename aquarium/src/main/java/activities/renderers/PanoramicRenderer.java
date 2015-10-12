package activities.renderers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Build;
import android.os.SystemClock;
import android.util.DisplayMetrics;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dagrada.marco.aquarium.animations.BubbleAnimation;
import dagrada.marco.aquarium.animations.BubbleFluxAnimation;
import dagrada.marco.aquarium.communicationpackets.BackgroundPacket;
import dagrada.marco.aquarium.communicationpackets.ItemsPacket;
import dagrada.marco.aquarium.communicationpackets.RotationPacket;
import dagrada.marco.aquarium.exceptions.TouchedItemNotFoundException;
import dagrada.marco.aquarium.resources.ItemsHolder;
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

public class PanoramicRenderer extends GameRenderer {

    private static final String STANDARD_SHADER = "stdShader";
    private static final int CUBE_ROWS = 5;
    private static final int CUBE_COLS = 5;
    private static final int RADIUS = 75;
    private static final int MAX_COLOR = 250;
    float width, height;
    private boolean isBlocked = false;

    HashMap<Integer, String> colors = new HashMap<>();

    float red = 0;
    float blue = 148/255;
    float green = 117/255;


    Context context;

    private float[] projection = {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1,
    };

    private float[] camera = {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1,
    };

    private float[] mvp = new float[16];

    Node father;
    Node backgroundfather;
    Node scorefather;
    Node menufather;
    Node itemsfather = new Node();

    Node proxyItem;

    private LinkedList<Updatable> toBeDrawn = new LinkedList<>();
    private int score;

    float tx = 0, ty = 0;
    float tx_step = 0, ty_step = 0;

    private LinkedList<GraphicsAnimation> animations = new LinkedList<>();
    private LinkedList<GraphicsAnimation> blockingAnimations = new LinkedList<>();
    private LinkedList<GraphicsAnimation> toBeAdded = new LinkedList<>();
    private LinkedList<GraphicsAnimation> toBeAddedBlocking = new LinkedList<>();

    private long current = 0;
    private long previous = 0;
    private long frametime;

    private NodeCollector nodeCollector;

    private Node[][] tiles = new Node[5][5];
    private Node[] menu = new Node[5];
    //private int[][] background = new int[5][5];
    //private int[][] items = new int[5][5];

    int[][] background = {
            {2, 1, 0, 0, 2},
            {0, 0, 0, 0, 1},
            {0, 0, 1, 0, 1},
            {2, 0, 0, 2, 0},
            {0, 0, 2, 1, 0}
    };

    int[][] items = {
            {2, 0, 0, 2, 1},
            {0, 0, 0, 0, 2},
            {0, 0, 3, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 2, 1, 0}
    };


    private HashMap<Character, String> numbersTextures = new HashMap<>();
    private HashMap<Integer, String> tilesTextures = new HashMap<>();
    private HashMap<Integer, String> itemsTextures = new HashMap<>();
    private HashMap<Integer, String> itemsColors = new HashMap<>();
    private HashMap<Integer, SFMatrix3f> itemsTransforms = new HashMap<>();

    public PanoramicRenderer(Context context) {
        tx =0;
        this.context = context;
        toBeDrawn = new LinkedList<>();

        colors.put(-1, "#FF000000");
        colors.put(0, "#FFAAAAAA");
        colors.put(1, "#FF00FF00");
        colors.put(2, "#FFFF0000");
        colors.put(3, "#FFFFFFFF");
        colors.put(4, "#FFFFFFFF");
        colors.put(5, "#FF009475");

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

        tilesTextures.put(0, "tile_0.obj");
        tilesTextures.put(1, "tile_1.obj");
        tilesTextures.put(2, "tile_2.obj");

        itemsTextures.put(1, "stone.obj");
        itemsTextures.put(2, "plant.obj");
        itemsTextures.put(3, "vase.obj");

        itemsColors.put(1, "#FF727B84");
        itemsColors.put(2, "#FF76A912");
        itemsColors.put(3, "#FFE29F5B");

        float STONE_SCALE = 0.65f;
        itemsTransforms.put(1, SFMatrix3f.getScale(STONE_SCALE, STONE_SCALE, STONE_SCALE));

        float PLANT_SCALE = 6f;
        itemsTransforms.put(2, SFMatrix3f.getScale(PLANT_SCALE, PLANT_SCALE, PLANT_SCALE));

        float AMPHORA_SCALE = 2.5f;
        SFMatrix3f matrix3f = SFMatrix3f.getScale(AMPHORA_SCALE, AMPHORA_SCALE, AMPHORA_SCALE);
        matrix3f.MultMatrix(SFMatrix3f.getRotationX(1.2f));
        itemsTransforms.put(3, matrix3f);




    }

    public int[] detectTouchedItem(float x, float y) throws TouchedItemNotFoundException {
        return null;
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getActualWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    private int getSoftbuttonsbarHeight() {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public void update() {

        drawBackground();

        father = new Node();

        nodeCollector.clearList();

        drawItems();

        father.getSonNodes().addAll(nodeCollector.getNodes());
        nodeCollector.clearList();

        /*
        drawScore(score);

        scorefather.getSonNodes().addAll(nodeCollector.getNodes());

        nodeCollector.clearList();

*/
    }

    public void drawItems() {

        float fish_scale = 0.05f;

        Node fish = NodesKeeper.generateNode(context, "stdShader", "#FFFA6900", "fish.obj");
        fish.getRelativeTransform().setPosition(0, 2f, 0);
        fish.getRelativeTransform().setMatrix(SFMatrix3f.getScale(fish_scale, fish_scale, fish_scale));
        fish.updateTree(new SFTransform3f());
        nodeCollector.addNode(fish);


    }

    public void drawBackground() {

        backgroundfather = new Node();
        this.animations = new LinkedList<>();

        float TILE_SCALE = 1f;
        float TILE_DISTANCE = 2f;

        for (int i = 0; i < background.length; i++) {
            for (int j = 0; j < background[0].length; j++) {
                Node tile = NodesKeeper.generateNode(context, "stdShader", "#FFC2B280", tilesTextures.get(background[i][j]));
                tile.getRelativeTransform().setPosition(i * TILE_DISTANCE - 2f * TILE_DISTANCE, 0, j * TILE_DISTANCE - 2f * TILE_DISTANCE);
                tile.getRelativeTransform().setMatrix(SFMatrix3f.getScale(TILE_SCALE, TILE_SCALE, TILE_SCALE));
                tile.updateTree(new SFTransform3f());
                tiles[i][j] = tile;
                backgroundfather.getSonNodes().add(tile);
            }
        }

        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[0].length; j++) {

                if (items[i][j] != 0) {

                    Node item = NodesKeeper.generateNode(context, "stdShader", itemsColors.get(items[i][j]), itemsTextures.get(items[i][j]));
                    SFVertex3f position = new SFVertex3f();
                    tiles[i][j].getRelativeTransform().getPosition(position);
                    item.getRelativeTransform().setPosition(position);
                    item.getRelativeTransform().setMatrix(itemsTransforms.get(items[i][j]));
                    item.updateTree(new SFTransform3f());
                    backgroundfather.getSonNodes().add(item);
                }

            }
        }

        if(items[2][2] == ItemsHolder.VASE){
            BubbleFluxAnimation a = new BubbleFluxAnimation(context, tiles[2][2].getX(), tiles[2][2].getY()+1.2f, tiles[2][2].getZ());
            addAnimation(a);
        }


    }

    public void drawMenu() {

        menufather = new Node();

        float TILE_SCALE = 1f;
        float TILE_DISTANCE = 2.1f;
        float RIGHT_MENU_CENTER = 7.5f;


        for (int j = 0; j < 5; j++) {
            Node tile = NodesKeeper.generateNode(context, "stdShader", colors.get(j), "tile_0.obj");
            tile.getRelativeTransform().setPosition(j * TILE_DISTANCE - 2f * TILE_DISTANCE, 0, RIGHT_MENU_CENTER);
            tile.getRelativeTransform().setMatrix(SFMatrix3f.getScale(TILE_SCALE, TILE_SCALE, TILE_SCALE));
            tile.updateTree(new SFTransform3f());
            menu[j] = tile;
            menufather.getSonNodes().add(tile);
        }

        for (int j = 5; j < 6; j++) {
            Node tile = NodesKeeper.generateNode(context, "stdShader", colors.get(j), "tile_0.obj");
            tile.getRelativeTransform().setPosition(j * TILE_DISTANCE - 2f * TILE_DISTANCE, 0, -RIGHT_MENU_CENTER);
            tile.getRelativeTransform().setMatrix(SFMatrix3f.getScale(TILE_SCALE, TILE_SCALE, TILE_SCALE));
            tile.updateTree(new SFTransform3f());
            menu[j] = tile;
            menufather.getSonNodes().add(tile);
        }

    }

    public void drawScore(int score) {

        float digit_distance = 0.12f;
        float number_scale = 0.045f;

        scorefather = new Node();

        String scoreString = String.valueOf(score);

        float beginning = ((scoreString.length() - 1) / 2) * digit_distance;
        if (scoreString.length() % 2 == 0)
            beginning += 0.5f * digit_distance;

        //Log.d("STRINGA", String.valueOf(scoreString.length()));

        Node plane = NodesKeeper.generateNode(context, "stdShader", "#FFFFFFFF", "1x1plane.obj");
        plane.getRelativeTransform().setPosition(0, 0, 0);
        plane.getRelativeTransform().setMatrix(SFMatrix3f.getScale(0.15f, 1f, 1f));
        plane.updateTree(new SFTransform3f());

        nodeCollector.addNode(plane);

        for (int i = 0; i < scoreString.length(); i++) {

            Node number = NodesKeeper.generateNode(context, "stdShader", "#FF000000", numbersTextures.get(scoreString.charAt(i)));
            number.getRelativeTransform().setPosition(0, 0, (i * digit_distance) - beginning);
            SFMatrix3f matrix = SFMatrix3f.getScale(number_scale, number_scale, number_scale);

            number.getRelativeTransform().setMatrix(matrix.MultMatrix(SFMatrix3f.getRotationX(3.14f)));
            number.updateTree(new SFTransform3f());
            nodeCollector.addNode(number);

        }


    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {


        toBeDrawn = new LinkedList<>();


        update();

        background = new int[5][5];
        items = new int[5][5];

        update();
        //drawBackground();
       //drawMenu();


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method

        //Log.d("SCREEN RATIO", "Width/Height =" + ratio);
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 3, 9);


        Matrix.setLookAtM(camera, 0, 0f, 3f, 5f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mvp, 0, projection, 0, camera, 0);

        this.width = width;
        this.height = height;


    }

    @Override
    public void onDrawFrame(GL10 gl) {
        previous = current;
        current = SystemClock.elapsedRealtime();
        frametime = current - previous;

        //Log.d("TIME", String.valueOf(frametime));

        SFOGLSystemState.cleanupColorAndDepth(red, green, blue, 1);

        //SFOGLSystemState.cleanupColorAndDepth(0, 148/256, 117/256, 1);

        ShadersKeeper.getProgram(STANDARD_SHADER).setupProjection(mvp);

        updateAnimations();


        float scaling = 0.35f;
        tx = tx + tx_step;

        float rotationx = 0f + tx;
        //Log.e("ROTATION", String.valueOf(rotation));

        SFMatrix3f matrix3f = SFMatrix3f.getRotationY(rotationx);


        matrix3f = matrix3f.MultMatrix(SFMatrix3f.getScale(scaling, scaling, scaling));

        //matrix3f.MultMatrix(SFMatrix3f.getRotationY(rotation));
        backgroundfather.getRelativeTransform().setMatrix(matrix3f);
        backgroundfather.updateTree(new SFTransform3f());


        for (Node node : backgroundfather.getSonNodes()) {
            node.draw();
        }

/*
        menufather.getRelativeTransform().setMatrix(matrix3f);
        menufather.updateTree(new SFTransform3f());


        for (Node node : menufather.getSonNodes()) {
            node.draw();
        }
        */

        itemsfather.getRelativeTransform().setMatrix(matrix3f);
        itemsfather.updateTree(new SFTransform3f());

        //Log.e("------------->", String.valueOf(itemsfather.getSonNodes().size()));

        for (Node node : itemsfather.getSonNodes()) {
            node.draw();
        }




        father.getRelativeTransform().setMatrix(matrix3f);
        father.updateTree(new SFTransform3f());

        for (Node  node : father.getSonNodes()) {
            node.draw();
        }

        /*

        scorefather.getRelativeTransform().setPosition(-2f, 0.3f, 0);
        scorefather.updateTree(new SFTransform3f());

        for (Node  node : scorefather.getSonNodes()) {
            node.draw();
        }


*/

    }


    public void updateAnimations() {

        if (toBeAddedBlocking.size() > 0) {
            blockingAnimations.addAll(toBeAddedBlocking);
            toBeAddedBlocking = new LinkedList<>();

        }
        if (toBeAdded.size() > 0) {
            animations.addAll(toBeAdded);
            toBeAdded = new LinkedList<>();
        }


        List<GraphicsAnimation> toRemove = new LinkedList<>();

        for (Iterator<GraphicsAnimation> it = blockingAnimations.iterator(); it.hasNext(); ) {
            GraphicsAnimation anim = it.next();

            anim.goOn();
            if (anim.isEnded()) {

                toRemove.add(anim);
            }

        }
        try {
            blockingAnimations.removeAll(toRemove);

        } catch (ConcurrentModificationException e) {

        }

        if (blockingAnimations.size() == 0) {
            isBlocked = false;
        }

        toRemove = new LinkedList<>();

        for (Iterator<GraphicsAnimation> it = animations.iterator(); it.hasNext(); ) {
            GraphicsAnimation anim = it.next();

            anim.goOn();
            if (anim.isEnded()) {
                toRemove.add(anim);

            }

        }

        try {
            animations.removeAll(toRemove);

        } catch (ConcurrentModificationException e) {

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


    public void updateModel(Object obj) {

        if (obj instanceof BackgroundPacket) {
            BackgroundPacket p = (BackgroundPacket) obj;
            this.background = p.getGrid();
        }
        if (obj instanceof ItemsPacket) {
            ItemsPacket p = (ItemsPacket) obj;
            this.items = p.getGrid();
        }
        if (obj instanceof RotationPacket){
            RotationPacket p = (RotationPacket)obj;
            this.tx_step = p.getRotationX();
            this.ty_step = p.getRotationY();
            ty = ty + ty_step;
            Matrix.setLookAtM(camera, 0, 0f, 3f+ty, 5f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            Matrix.multiplyMM(mvp, 0, projection, 0, camera, 0);
        }


    }

    @Override
    public void addAnimation(GraphicsAnimation animation) {
        if (animation.isblocking()) {
            isBlocked = true;
            toBeAddedBlocking.add(animation);
        } else {
            toBeAdded.add(animation);
        }
    }

    @Override
    public void animate(Object object) {


    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public Context getContext() {
        return context;
    }
}

