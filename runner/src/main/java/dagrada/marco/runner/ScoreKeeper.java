package dagrada.marco.runner;

/**
 * Created by Marco on 06/08/2015.
 */
public class ScoreKeeper {

    private int score;

    public ScoreKeeper(){
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int amount){
        score +=amount;
    }
}
