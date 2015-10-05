package activities;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import activities.renderers.EditRenderer;
import dagrada.marco.aquarium.exceptions.TouchedItemNotFoundException;
import thesis.Graphics.GraphicsAnimation;

/**
 * Created by Marco on 26/05/2015.
 */
public class ProxyRenderer extends GameRenderer {




    private GameRenderer proxyRenderer;

    private EditRenderer editRenderer;



    public int[] detectTouchedItem(float x, float y) throws TouchedItemNotFoundException {

        return proxyRenderer.detectTouchedItem(x, y);


    }



    public ProxyRenderer(Context context){
        editRenderer = new EditRenderer(context);
        proxyRenderer = editRenderer;

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





       proxyRenderer.onSurfaceCreated(gl, config);







    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        proxyRenderer.onSurfaceChanged(gl, width, height);






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
