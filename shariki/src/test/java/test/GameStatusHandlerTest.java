package test;

import android.content.Context;


import com.example.alessandro.computergraphicsexample.GraphicsRenderer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagrada.marco.shariki.GameStatusHandler;
import dagrada.marco.shariki.exceptions.GameEndException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Marco on 03/08/2015.
 *
 *
 */
public class GameStatusHandlerTest {

    private GameStatusHandler handler;
    private ArrayList<String> list = new ArrayList<>();

    @Mock private GraphicsRenderer renderer;

    @Mock private Context context;

    @Before
    public void setUp(){

        handler = new GameStatusHandler(context, renderer);

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

    /*
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
                {1,2,1,2,1,1,2,1,-1,0},
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

    }
    */

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
