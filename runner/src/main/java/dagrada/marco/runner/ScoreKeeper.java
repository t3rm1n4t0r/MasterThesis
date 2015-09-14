package dagrada.marco.runner;

/**
 * Created by Marco on 06/08/2015.
 */
public class ScoreKeeper {



    private int score;
    private GameEngine engine;

    public ScoreKeeper(GameEngine engine){
        score = 0;
        this.engine = engine;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int amount){
        score +=amount;
        if(score > 0 && score%500 == 0){
            engine.accelerate();
        }
    }
}
