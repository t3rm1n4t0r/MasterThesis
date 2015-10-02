package test;

import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import dagrada.marco.runner.core.GameEngine;
import dagrada.marco.runner.core.ScoreKeeper;
import dagrada.marco.runner.interactables.Guitar;
import thesis.Graphics.GraphicsEngine;
import thesis.utils.Interactable;
import thesis.utils.InteractablesCollector;
import thesis.utils.UpdatablesCollector;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Marco on 02/10/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class GameEngineTest {

    private GameEngine engine;

    @Mock
    GraphicsEngine graphicsEngine;

    @Mock
    Handler handler;

    @Mock
    Guitar guitar;


    private UpdatablesCollector updatablesCollector;
    private InteractablesCollector interactablesCollector;

    @Before
    public void setUp() {

        graphicsEngine = Mockito.mock(GraphicsEngine.class);
        handler = Mockito.mock(Handler.class);
        guitar = new Guitar(0,0,0,null);

        updatablesCollector = Mockito.mock(UpdatablesCollector.class);
        interactablesCollector = Mockito.mock(InteractablesCollector.class);

        engine = new GameEngine(handler, 50, updatablesCollector, interactablesCollector, guitar, graphicsEngine);
        ScoreKeeper keeper = new ScoreKeeper(engine);
        guitar.setScoreKeeper(keeper);
    }

    @Test
    public void testStartandStop(){
        assertTrue(engine.isStopped());
        engine.start();
        assertFalse(engine.isStopped());
        engine.stop();
        assertTrue(engine.isStopped());

    }

    @Test
    public void testRun(){
        engine.start();
        engine.run();
        verify(updatablesCollector, times(2)).getUpdatables();
        verify(interactablesCollector, times(1)).getInteractables();
    }

    @Test
    public void testAccelerate(){
        engine.start();
        engine.accelerate();
        assertTrue(engine.getBase_multiplier() == 1.1f);
    }

}
