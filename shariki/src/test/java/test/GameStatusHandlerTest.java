package test;

import android.content.Context;
import android.test.ActivityTestCase;
import android.test.AndroidTestCase;
import android.test.ServiceTestCase;
import android.test.mock.MockContext;

import com.example.alessandro.computergraphicsexample.GraphicsRenderer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.And;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import dagrada.marco.shariki.GameStatusHandler;

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
    Context context;

    @Before
    public void setUp(){

        GraphicsRenderer renderer = new GraphicsRenderer(context);

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

    @Test
    public void testTryToSwitchWhichShouldNotSwitch(){

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
        handler.tryToSwitch(0,0,1,0);

        assertTrue(Arrays.equals(handler.getMarbles()[0], finalm[0]));
        assertTrue(Arrays.equals(handler.getMarbles()[1], finalm[1]));
        assertTrue(Arrays.equals(handler.getMarbles()[2], finalm[2]));
        assertTrue(Arrays.equals(handler.getMarbles()[3], finalm[3]));
        assertTrue(Arrays.equals(handler.getMarbles()[4], finalm[4]));

    }


}
