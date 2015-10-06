package activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;


import java.io.IOException;

import dagrada.marco.aquarium.ResourceManager;
import dagrada.marco.aquarium.filehandlers.MatrixFileHandler;
import dagrada.marco.aquarium.resources.BackgroundHolder;
import dagrada.marco.aquarium.resources.ItemsHolder;
import thesis.touch.GestureFilter;


public class MainActivity extends Activity {

    private GestureDetector detector;

    private long updateDelay = 80;
    private long generationDelay = 3000;

    private GraphicsView view;
    ResourceManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ProxyRenderer renderer = new ProxyRenderer(this);



        //GameController controller = new GameController(engine, itemsGenerator, guitarController);
/*
        MatrixFileHandler handler = new MatrixFileHandler(this, "background.txt");

        int[][] matrix = {
                {1, 1, 1, 1, 1},
                {1, 2, 2, 4, 1},
                {2, 1, 3, 0, 1},
                {1, 2, 1, 5, 1},
                {2, 1, 6, 1, 5}
        };

        try {
            handler.writeToFile(matrix);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            int[][] result = (int[][]) handler.readFromFile();

            String s = new String();

            for (int i=0; i<result.length; i++){
                for (int j=0; j<result[0].length; j++){
                    s+=result[i][j];
                    s+=" ";
                }
                s+="\n";
            }

            Log.e("MATRICE", s);

        } catch (IOException e) {
            e.printStackTrace();
        }

*/

        MatrixFileHandler handler1 = new MatrixFileHandler(this, "background.txt");
        MatrixFileHandler handler2 = new MatrixFileHandler(this, "items.txt");

        int[][] matrix = {
                {2, 1, 0, 0, 2},
                {0, 0, 0, 0, 1},
                {0, 0, 1, 0, 1},
                {2, 0, 0, 2, 0},
                {0, 0, 2, 1, 0}
        };

        int[][] matrix2 = {
                {2, 0, 0, 2, 1},
                {0, 0, 0, 0, 2},
                {0, 0, 3, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 2, 1, 0}
        };

        try {
            handler1.writeToFile(matrix);
            handler2.writeToFile(matrix2);
        } catch (IOException e) {
            e.printStackTrace();
        }





        BackgroundHolder holder = new BackgroundHolder(renderer, handler1);
        ItemsHolder holder2 = new ItemsHolder(renderer, handler2);

        manager = new ResourceManager();
        manager.setResource(ResourceManager.BACKGROUND, holder);
        manager.setResource(ResourceManager.ITEMS, holder2);

        view = new GraphicsView(this, renderer, manager);
        detector = new GestureDetector(this, new GestureFilter(view));
        detector.setIsLongpressEnabled(false);
        setContentView(view);



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event){

        if (detector.onTouchEvent(event)== true){
            //Fling or other gesture detected (not logpress because it is disabled)
        }
        else{
            //Manually handle the event.
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                view.onUp(event.getX(), event.getY());
                //Remember the time and press position
                Log.e("test", "Action down");
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE)
            {
                //Check if user is actually longpressing, not slow-moving
                // if current position differs much then press positon then discard whole thing
                // If position change is minimal then after 0.5s that is a longpress. You can now process your other gestures

                view.onMove(event.getX(), event.getY());

                Log.e("test","Action move");
            }
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                //Get the time and position and check what that was :)

                view.onUp(event.getX(), event.getY());
                Log.e("test","Action up");
            }

        }
        return super.onTouchEvent(event);
    }

}
