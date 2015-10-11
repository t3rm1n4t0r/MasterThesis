package activities.renderers;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import activities.renderers.EditRenderer;
import activities.renderers.PanoramicRenderer;
import dagrada.marco.aquarium.exceptions.TouchedItemNotFoundException;
import thesis.Graphics.GameRenderer;
import thesis.Graphics.GraphicsAnimation;

/**
 * Created by Marco on 26/05/2015.
 */
public class ProxyRenderer extends GameRenderer {




    private GameRenderer proxyRenderer;

    private EditRenderer editRenderer;
    private PanoramicRenderer panoramicRenderer;
    private GameRenderer[] renderers;
    private int current;


    public int[] detectTouchedItem(float x, float y) throws TouchedItemNotFoundException {

        return proxyRenderer.detectTouchedItem(x, y);


    }

    public void toggleRenderer(int gamemode){

        switch (gamemode){
            case 0:case 1:
                this.current = gamemode;
                this.proxyRenderer = renderers[current];
                proxyRenderer.drawBackground();
                proxyRenderer.drawItems();
                break;
            default:break;

        }



    }



    public ProxyRenderer(Context context){
        current = 0;
        renderers = new GameRenderer[2];
        renderers[0] = new EditRenderer(context);
        renderers[1] = new PanoramicRenderer(context);
        proxyRenderer = renderers[current];

    }



    public void update(){

        proxyRenderer.update();


    }

    public void drawItems(){


        proxyRenderer.drawItems();



    }

    public void drawBackground(){

        proxyRenderer.drawBackground();



    }

    public void drawScore(int score){

        proxyRenderer.drawScore(score);


    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {


        for (int i=0; i<renderers.length; i++) {
            renderers[i].onSurfaceCreated(gl, config);
        }

       //proxyRenderer.onSurfaceCreated(gl, config);







    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        for (int i=0; i<renderers.length; i++) {
            renderers[i].onSurfaceChanged(gl, width, height);
        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        proxyRenderer.onDrawFrame(gl);

    }


    public void updateAnimations(){

        proxyRenderer.updateAnimations();
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

        proxyRenderer.updateModel(obj);


    }

    @Override
    public void addAnimation(GraphicsAnimation animation) {
        proxyRenderer.addAnimation(animation);
    }

    @Override
    public void animate(Object object) {

        proxyRenderer.animate(object);
    }

    public boolean isBlocked(){
        return proxyRenderer.isBlocked();
    }

    public Context getContext() {
        return proxyRenderer.getContext();
    }
}
