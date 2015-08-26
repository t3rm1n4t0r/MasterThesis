package test;


import android.content.Context;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import dagrada.marco.shariki.GameModelHandler;
import thesis.Graphics.GraphicsEngine;
import dagrada.marco.shariki.exceptions.GameEndException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Marco on 03/08/2015.
 *
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class GameModelHandlerTest {

    private GameModelHandler handler;
    private ArrayList<String> list = new ArrayList<>();

    @Mock
    Context context;

    @Before
    public void setUp(){
        context = Mockito.mock(Context.class);
        handler = new GameModelHandler(context);
    }

    @Test(expected = Exception.class)
    public void testLoadGame() throws Exception {
        list.add(0, "level0.txt");
        handler.loadGame(list);
    }


    @Test
    public void testCompactMarblesRow(){

        int[][] start={
                {0,0,0,2,2,1,2,1,1},
                {1,2,2,1,2,1,1,2,1},
                {2,1,1,2,1,2,2,1,2},
                {1,1,2,1,2,1,2,2,1},
                {1,2,1,1,2,2,1,2,2}
        };

        int[][] finalm={
                {2,2,1,2,1,1,0,0,0},
                {1,2,2,1,2,1,1,2,1},
                {2,1,1,2,1,2,2,1,2},
                {1,1,2,1,2,1,2,2,1},
                {1,2,1,1,2,2,1,2,2}
        };

        handler.setMarbles(start);
        handler.compactMarbles();

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));

    }

    @Test
    public void testCompactMarblesColumn(){

        int[][] start={
                {0,2,1,2,2,1,2,1,1},
                {0,2,2,1,2,1,1,2,1},
                {0,1,1,2,1,2,2,1,2},
                {0,1,2,1,2,1,2,2,1},
                {0,2,1,1,2,2,1,2,2}
        };

        int[][] finalm={
                {2,1,2,2,1,2,1,1,0},
                {2,2,1,2,1,1,2,1,0},
                {1,1,2,1,2,2,1,2,0},
                {1,2,1,2,1,2,2,1,0},
                {2,1,1,2,2,1,2,2,0}
        };
        handler.setMarbles(start);
        handler.compactMarbles();

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));

    }

    @Test
    public void testCheckForSegmentsCorrect(){

        int[][] start={
                {2,2,1,2,1,1},
                {1,2,1,1,2,1},
                {1,1,2,2,1,2},
                {1,2,1,2,2,1},
                {1,2,2,1,2,2}
        };

        int[][] finalm={
                {2,2,1,2,1,1},
                {0,2,1,1,2,1},
                {0,1,2,2,1,2},
                {0,2,1,2,2,1},
                {0,2,2,1,2,2}
        };

        handler.setMarbles(start);
        assertTrue(handler.checkForSegments());

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));

    }

    @Test
    public void testCheckForSegmentsWithNoSegments(){

        int[][] start={
                {2,2,1,2,1,1},
                {1,2,1,1,2,1},
                {2,1,2,2,1,2},
                {1,2,1,2,2,1},
                {1,2,2,1,2,2}
        };

        int[][] finalm={
                {2,2,1,2,1,1},
                {1,2,1,1,2,1},
                {2,1,2,2,1,2},
                {1,2,1,2,2,1},
                {1,2,2,1,2,2}
        };
        handler.setMarbles(start);
        assertFalse(handler.checkForSegments());

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));

    }


    @Test
    public void testTryToSwitch() throws GameEndException {

        int[][] start={
                {1,2,3,2,2,1,2,1,1,-1},
                {1,3,2,1,2,1,1,2,1,-1},
                {2,1,1,2,3,2,2,1,2,-1},
                {1,1,2,3,2,1,2,2,1,-1},
                {1,2,1,1,2,2,1,2,2,-1}
        };

        int[][] finalm={
                {1,2,3,2,2,1,2,1,1,-1},
                {1,3,2,1,2,1,1,2,1,-1},
                {2,1,2,1,3,2,2,1,2,-1},
                {1,1,2,3,2,1,2,2,1,-1},
                {1,2,1,1,2,2,1,2,2,-1}
        };
        handler.setMarbles(start);
        handler.switchMarbles(2, 2, 2, 3);

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));

    }


    @Test
    public void testTryToSwitchWhichShouldNotSwitchBecauseOfBadIndices() throws GameEndException {

        int[][] start={
                {1,2,1,2,2,1,2,1,1},
                {1,2,2,1,2,1,1,2,1},
                {2,1,1,2,1,2,2,1,2},
                {1,1,2,1,2,1,2,2,1},
                {1,2,1,1,2,2,1,2,2}
        };

        int[][] finalm={
                {1,2,1,2,2,1,2,1,1},
                {1,2,2,1,2,1,1,2,1},
                {2,1,1,2,1,2,2,1,2},
                {1,1,2,1,2,1,2,2,1},
                {1,2,1,1,2,2,1,2,2}
        };

        handler.setMarbles(start);
        handler.switchMarbles(0, 0, -1, 0);

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));

    }

    @Test
    public void testUpdateScore(){

        int[][] start={
                {0,2,1,2,2,1,2,1,1},
                {0,2,2,1,2,1,1,2,1},
                {0,1,1,2,1,2,2,1,2},
                {0,1,2,1,2,1,2,2,1},
                {0,2,1,1,2,2,1,2,2}
        };

        handler.setMarbles(start);
        handler.updateScore();

        assertTrue(handler.getScorekeeper().getScore() == 100);

    }

    @Test
    public void testUpdateScore2(){

        int[][] start={
                {0,2,1,2,2,1,2,1,1},
                {0,2,2,1,2,1,1,2,1},
                {0,0,0,0,1,2,2,1,2},
                {0,1,2,1,2,1,2,2,1},
                {0,2,1,1,2,2,1,2,2}
        };

        handler.setMarbles(start);
        handler.updateScore();

        assertTrue(handler.getScorekeeper().getScore() == 160);

    }

    @Test
    public void testCheckForEndGameTrue(){

        int[][] start={
                {0,2,1,2,-1,1,2,1,1},
                {0,2,2,1,-1,1,1,2,1},
                {0,0,0,0,-1,2,2,1,2},
                {0,1,2,1,-1,1,2,2,1},
                {0,2,1,1,-1,2,1,2,2}
        };

        handler.setMarbles(start);


        assertTrue(handler.checkForEndGame());

    }

    @Test
    public void testCheckForEndGameFalse(){

        int[][] start={
                {0,2,1,2,1,1,2,1,1},
                {0,2,2,1,1,1,1,2,1},
                {0,0,0,0,1,2,2,1,2},
                {0,1,2,1,1,1,2,2,1},
                {0,2,1,1,1,2,1,2,2}
        };

        handler.setMarbles(start);


        assertFalse(handler.checkForEndGame());

    }

    @Test
    public void testIsGameEnded(){

        int[][] start={
                {0,2,1,2,1,1,2,1,1},
                {0,2,2,1,1,1,1,2,1},
                {0,0,0,0,-1,2,2,1,2},
                {0,1,2,1,1,1,2,2,1},
                {0,2,1,1,1,2,1,2,2}
        };

        handler.setMarbles(start);
        handler.checkForEndGame();


        assertTrue(handler.isGameEnded());

    }













}
