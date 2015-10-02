package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import dagrada.marco.runner.core.GameEngine;
import dagrada.marco.runner.core.ScoreKeeper;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Marco on 02/10/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ScoreKeeperTest {

    ScoreKeeper keeper;

    @Mock
    GameEngine engine;

    @Before
    public void setUp(){

        engine = Mockito.mock(GameEngine.class);

        keeper = new ScoreKeeper(engine);

    }

    @Test
    public void testScoreCorrect(){

        assertTrue(keeper.getScore() == 0);
        keeper.updateScore(10);
        assertTrue(keeper.getScore() == 10);
        keeper.updateScore(-10);
        assertTrue(keeper.getScore() == 0);

    }

    @Test
    public void testAcceleration(){

        assertTrue(keeper.getScore() == 0);
        keeper.updateScore(500);
        assertTrue(keeper.getScore() == 500);
        verify(engine, times(1)).accelerate();

    }

}
