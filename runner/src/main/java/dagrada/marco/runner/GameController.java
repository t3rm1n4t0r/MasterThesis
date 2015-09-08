package dagrada.marco.runner;


/**
 * Created by Marco on 28/07/2015.
 */
public class GameController {

    private GameEngine engine;
    private ItemsGenerator generator;
    private boolean paused;

    public GameController(GameEngine engine, ItemsGenerator generator){

        this.engine = engine;
        this.generator = generator;
        paused = true;
    }

    public void toggleGame(){
        if(paused){
            engine.start();
            generator.start();
            paused = false;
        }
        else {
            engine.stop();
            generator.stop();
            paused = true;
        }
    }


}
