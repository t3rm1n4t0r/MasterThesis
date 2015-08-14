package test;

import android.content.Context;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


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
public class GameModelHandlerTest {

    private GameModelHandler handler;
    private ArrayList<String> list = new ArrayList<>();

    @Mock
    GraphicsEngine renderer;

    @Mock private Context context;

    @Before
    public void setUp(){
        renderer = Mockito.mock(GraphicsEngine.class);
        handler = new GameModelHandler(context, renderer);

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
                {1,3,1,2,1,1,2,1,-1,0},
                {2,1,1,3,2,2,1,2,-1,0},
                {1,1,3,2,1,2,2,1,-1,0},
                {1,2,1,1,2,2,1,2,2,-1}
        };
        handler.setMarbles(start);
        handler.tryToSwitch(2, 2, 2, 3);

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));
        assertTrue(handler.getScorekeeper().getScore()== 60);

    }


    @Test
    public void testTryToSwitchWhichShouldNotSwitch() throws GameEndException {

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
        handler.tryToSwitch(0, 0, 1, 0);

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
        handler.tryToSwitch(0,0,-1,0);

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));

    }


}
